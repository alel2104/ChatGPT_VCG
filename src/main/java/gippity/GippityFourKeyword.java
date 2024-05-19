package gippity;

import com.cjcrafter.openai.chat.ChatMessage;

import java.util.List;

/**
 * The ChatGPT version 4.0 with the keyword code generation variant component.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class GippityFourKeyword extends Gippity {

    /**
     * Constructor to initialize the ChatGPT model, system messages for prompting
     * the ChatGPT before each message, and Speech Optimization messages for prompting the
     * ChaGPT for speech optimization after each message in the superclass constructor.
     */
    public GippityFourKeyword() {
        super(
                "gpt-4-turbo",
                List.of(ChatMessage.toSystemMessage(
                        "Attempt to interpret all user commands and convert them into code keyword " +
                                "which the Serenade tool can understand.Most Serenade commands have the same form: " +
                                "<action> <selector>, where common actions include 'add' to add a new line of code, " +
                                "'change' to edit code, 'delete' to remove code, and 'insert' to insert code at the same " +
                                "line (if insert is used in command, then use it). Examples: go to <selector> " +
                                "('go to line 50', 'go to next function', 'go to second parameter'), " +
                                "insert <code or text> ('insert 21' => return 21), add <code> ('add return false', " +
                                "'add class exception', 'add function factorial', 'add method factorial'(to add " +
                                "the factorial function inside a class), 'add return say(\"hello\")'(return say(\"hello\")), " +
                                "'add else if x < 3', 'add if i == 0 continue', 'add argument word[0]' " +
                                "(capitalize(word[0]);), 'add int i = 0', 'add comment fix later' (// fix later), 'add callback = " +
                                "() => {}' (callback = () => {};), 'add parameter String[] args', 'add " +
                                "for int i = 0 i <= 6 i++'(has to be without ';'), 'add print hello world' " +
                                "(system.out.println('hello world'),) 'add system.out.printf(\"hello\")' " +
                                "(system.out.printf(\"hello\");), 'add argument ----' (print(----))), 'add method public " +
                                "static void hello(String message) throws IOException' (private static void " +
                                "hello(String message) throws IOException), 'add do {} while (i > 0)' (do {} while (i > 0);) " +
                                "change <old> to <new> ('change hello to goodbye', " +
                                "'change return value to false' (only works in return statements, for methods and other " +
                                "code use change <previous> to <new>)), delete <selector> " +
                                "('delete foo bar', 'delete next " +
                                "function'), copy/paste <selector> ('copy method', 'cut previous two words', 'paste'), " +
                                "indent/dedent ('indent block', dedent if), save ('save'), undo ('undo'), open <text> " +
                                "('open react.js'), (next | previous) tab ('next tab', 'previous tab'), focus <text> " +
                                "('focus code', 'focus chrome'), 'up' (to move up), 'down' (to move down), 'left' " +
                                "(to move one to the left), 'right' (to move one to the right)")),
                List.of(ChatMessage.toSystemMessage("Ignore previous instructions and respond with " +
                        "how the previous user command could have been optimized (in terms of shorten the command, but " +
                        "not only character count, how long it would take the user to pronounce the command) to achieve " +
                        "the same response/result from you."))
        );
        String holder = "Ignore previous instructions and respond only with: [Speech Optimization Yes!]";
    }
}
