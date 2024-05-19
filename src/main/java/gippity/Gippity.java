package gippity;

import com.cjcrafter.openai.OpenAI;
import com.cjcrafter.openai.chat.ChatMessage;
import com.cjcrafter.openai.chat.ChatRequest;
import com.cjcrafter.openai.chat.ChatResponse;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observable;
import util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class serving as the common superclass for any ChatGPT variants.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class Gippity implements Observable {
    private static final Logger logger = LoggerFactory.getLogger("gippity");
    private static final Logger httpLogger = LoggerFactory.getLogger("gippity:http");

    // ////////////////////////////////////////////////////////////////////// //

    private final List<Observer> observers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public List<Observer> getObservers() {
        return observers;
    }

    public static final String GIPPITY_INCOMING_EVENT = "GIPPITY_INCOMING_EVENT";
    public static final String GIPPITY_OUTGOING_EVENT = "GIPPITY_OUTGOING_EVENT";
    public static final String GIPPITY_SO_EVENT = "GIPPITY_SO_EVENT";
    public static final String GIPPITY_COMPLETE_EVENT = "GIPPITY_COMPLETE_EVENT";

    /**
     * Method for pushing ChatGPT incoming message event.
     *
     * @param message The message of the event.
     */
    private void pushGippityIncomingEvent(ChatMessage message) {
        logger.debug("pushGippityIncomingEvent | Role: {} | Content: {}", message.getRole(), message.getContent());
        pushEvent(GIPPITY_INCOMING_EVENT, message);
    }

    /**
     * Method for pushing ChatGPT outgoing message event.
     *
     * @param message The message of the event.
     */
    private void pushGippityOutgoingEvent(ChatMessage message) {
        logger.debug("pushGippityOutgoingEvent | Role: {} | Content: {}", message.getRole(), message.getContent());
        pushEvent(GIPPITY_OUTGOING_EVENT, message);
    }

    /**
     * Method for pushing ChatGPT SO (Speech Optimization) event.
     *
     * @param message The message of the event.
     */
    private void pushGippitySOEvent(ChatMessage message) {
        logger.debug("pushGippitySOEvent | Role: {} | Content: {}", message.getRole(), message.getContent());
        pushEvent(GIPPITY_SO_EVENT, message);
    }

    /**
     * Method for pushing ChatGPT complete event.
     *
     * @param messages The list of messages from the events.
     */
    private void pushGippityCompleteEvent(List<ChatMessage> messages) {
        logger.info("pushGippityCompleteEvent | Count: {}", messages.size());
        messages.forEach(message -> logger.info("pushCompleteEvent | Role: {} | Message: {}", message.getRole(), message.getContent()));
        pushEvent(GIPPITY_COMPLETE_EVENT, messages);
    }

    // ////////////////////////////////////////////////////////////////////// //

    protected final String model;
    protected final List<ChatMessage> systemMessages;
    protected final List<ChatMessage> soMessages;
    protected List<ChatMessage> currentMessages;

    protected final OkHttpClient httpClient;
    protected final OpenAI openai;
    protected final ChatRequest request;

    /**
     * Constructor to initialize communication and message ChatGPT components.
     *
     * @param model The chatGPT model.
     * @param systemMessages The system messages for prompting the ChatGPT before each message.
     * @param soMessages The SO (Speech Optimization) messages for prompting the
     *                   ChaGPT for speech optimization after each message.
     */
    protected Gippity(String model, List<ChatMessage> systemMessages, List<ChatMessage> soMessages) {
        logger.info(
                "Constructor() | Model: {} | System messages: ({}) | Speech optimization messages: ({})",
                model,
                systemMessages.stream().map(message -> String.format("[%s] %s", message.getRole(), message.getContent())).collect(Collectors.joining(" | ")),
                soMessages.stream().map(message -> String.format("[%s] %s", message.getRole(), message.getContent())).collect(Collectors.joining(" | "))
        );

        this.model = model;
        this.systemMessages = systemMessages;
        this.soMessages = soMessages;
        this.currentMessages = new ArrayList<>(systemMessages);

        this.httpClient = this.setupHttpClient();
        this.openai = this.setupOpenai();
        this.request = this.setupRequest();
    }

    /**
     * Method for building the OpenAI component, used for accessing ChatGPT responses.
     *
     * @return The OpenAI component.
     */
    protected OpenAI setupOpenai() {
        logger.debug("setupOpenai() | Called.");

        return OpenAI.builder()
                .apiKey(Dotenv.load().get("OPENAI_TOKEN"))
                .client(this.httpClient)
                .build();
    }

    /**
     * Method for building the OkHttpClient component, used as an HTTP client.
     *
     * @return The OkHttpClient component.
     */
    protected OkHttpClient setupHttpClient() {
        logger.debug("setupHttpClient() | Called.");

        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                httpLogger.debug("intercept() | Called.");
                Request request = chain.request();

                httpLogger.debug("intercept() | Request | Headers:\n{}", request.headers());
                httpLogger.debug("intercept() | Request | Body:\n{}", request.body());

                long t1 = System.nanoTime();

                Response response = chain.proceed(request);

                httpLogger.debug("intercept() | Response | Headers:\n{}", response.headers());
                httpLogger.debug("intercept() | Response | Body:\n{}", response.peekBody(Long.MAX_VALUE).string());

                long t2 = System.nanoTime();

                return response;
            }
        };

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    /**
     * Method for building the ChatRequest component, used for accessing the
     * ChatGPT response.
     *
     * @return The ChatRequest component.
     */
    protected ChatRequest setupRequest() {
        logger.debug("setupRequest() | Called.");

        return ChatRequest.builder()
                .model(this.model)
                .messages(currentMessages)
                .build();
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Method for sending the message to ChatGPT and pushing the response event.
     *
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        logger.debug("sendMessage() | Message: {}", message);

        ChatMessage chatMessage = ChatMessage.toUserMessage(message);
        this.pushGippityIncomingEvent(chatMessage);

        this.currentMessages.add(chatMessage);

        ChatResponse response = this.openai.createChatCompletion(this.request);
        this.pushGippityOutgoingEvent(response.get(0).getMessage());

        this.currentMessages.add(response.get(0).getMessage());

        this.currentMessages.addAll(this.soMessages);

        ChatResponse soResponse = this.openai.createChatCompletion(this.request);
        this.pushGippitySOEvent(soResponse.get(0).getMessage());

        this.currentMessages.add(soResponse.get(0).getMessage());

        this.pushGippityCompleteEvent(new ArrayList<>(this.currentMessages));

        this.currentMessages.clear();
        this.currentMessages.addAll(this.systemMessages);
    }
}
