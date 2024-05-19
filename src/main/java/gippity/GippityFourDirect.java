package gippity;

import com.cjcrafter.openai.chat.ChatMessage;

import java.util.List;

/**
 * The ChatGPT version 4.0 with the direct code generation variant component.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class GippityFourDirect extends Gippity {

    /**
     * Constructor to initialize the ChatGPT model, system messages for prompting
     * the ChatGPT before each message, and Speech Optimization messages for prompting the
     * ChaGPT for speech optimization after each message in the superclass constructor.
     */
    public GippityFourDirect() {
        super(
                "gpt-4-turbo",
                List.of(ChatMessage.toSystemMessage("Attempt to interpret all user commands and convert them into Java code. Do not include any formatting and return only the code.")),
                List.of(ChatMessage.toSystemMessage("Ignore previous instructions and respond with " +
                        "how the previous user command could have been optimized (in terms of shorten the command, but " +
                        "not only character count, how long it would take the user to pronounce the command) to achieve " +
                        "the same response/result from you."))
        );
    }
}
