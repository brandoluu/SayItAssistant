package SayItAssistant.middleware;

import java.io.IOException;
import java.lang.ref.Cleaner.Cleanable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PromptFactory {
    
    private final String QUESTION_PROMPT     = "question";
    private final String DELETE_PROMPT        = "delete prompt";
    private final String CLEAR_ALL_PROMPTS   = "clear all";
    private final String SETUP_EMAIL_PROMPT  = "set up email";
    private final String SETUP_EMAIL_PROMPT_ALT = "setup email";
    private final String CREATE_EMAIL_PROMPT = "create email";
    private final String SEND_EMAIL_PROMPT   = "send email";
    private final String PUNCTUATION = "[^\\p{L}\\p{Z}]";   //RegEx to cover all punctuation in a String

    public IPrompt createPrompt(String input) {

        String raw = input;
        IPrompt prompt = null;
        String cleanInput = input.replaceAll(PUNCTUATION, "").toLowerCase();
        System.out.println("CLEAN INPUT: " + cleanInput);
        
        if (cleanInput.startsWith(QUESTION_PROMPT, 0) && !(cleanInput.equals(QUESTION_PROMPT))) {
            prompt = makeQuestion(input);
        }

        else if (cleanInput.equals(DELETE_PROMPT)) {
            System.out.println("Creating delete prompt");
            prompt = new DeletePrompt();
        }

        else if (cleanInput.equals(CLEAR_ALL_PROMPTS)) {
           System.out.println("Creating clear-all prompt");
           prompt = new ClearAllPrompt();
        }

        else if (cleanInput.equals(SETUP_EMAIL_PROMPT) || cleanInput.equals(SETUP_EMAIL_PROMPT_ALT)) {
            System.out.println("Creating setup-email prompt");
            prompt = new SetUpEmailPrompt();
        }

        else if (cleanInput.startsWith(CREATE_EMAIL_PROMPT)) {
            try {
                List<String> lines = Files.readAllLines(Paths.get("name.txt"));
                String name = lines.get(0);
                Question q = new Question(String.format("%s Add %s as the sender's name after the closing phrase", raw, name));
                q.setMESSAGE("Create Email");
                prompt = q;
            } catch (IOException e) {
                System.out.println("lol");
            }
            
        }

        else if (cleanInput.startsWith(SEND_EMAIL_PROMPT, 0)) {
            Question q = new Question("Sending email...");
            q.setMESSAGE(raw.replace(" at ", "@"));
            return q;
        }

        return prompt;
    }

    private Question makeQuestion(String input) {
        Question prompt;
        System.out.println("Creating question prompt");
        input = input.replaceFirst("(?i)"+QUESTION_PROMPT, ""); // strips input of the command, regardless of case
        input = input.replaceFirst(PUNCTUATION, "");            // removes punctuation after the command
        input = input.replaceFirst(" ", "");                    // removes space after the command
        prompt = new Question(input);                           // creates a Question prompt using the new input
        System.out.println("Input: " + input);
        return prompt;
    }
}
