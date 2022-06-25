import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
public class HumanPlayer {

    /* Reads and returns user input */
    protected String getInputFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //reader created to take user input
        try {
            return reader.readLine();
            //returns user input
        }catch(IOException e){
            System.out.println(e);
            return null;
        }
    }

    /* Takes user input and returns corresponding command */
    protected String getNextAction(String action) {
        if(action.equalsIgnoreCase("hello")){
            return "hello";
        }else if(action.equalsIgnoreCase("gold")){
            return "gold";
        }else if(action.toLowerCase().contains("move")){
            return action.toLowerCase();
        }else if(action.equalsIgnoreCase("pickup")){
            return "pickup";
        }else if(action.equalsIgnoreCase("look")){
            return "look";
        }else if(action.equalsIgnoreCase("quit")){
            return "quit";
        }else{
            return "Invalid";
        }
    }
}