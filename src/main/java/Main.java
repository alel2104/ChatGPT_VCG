import connectcopy.SocketServer;
import mvc.AppController;
import mvc.AppModel;
import mvc.AppView;

/**
 * Class containing the main entry point of the ChatGPT voice code generator application.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class Main {

    /**
     * Main entry point of the ChatGPT voice code generator application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String... args) {
        System.out.println("ChatGPT-Voice-code-generator");

        new AppController(new AppModel(), new AppView(), new SocketServer());
    }
}
