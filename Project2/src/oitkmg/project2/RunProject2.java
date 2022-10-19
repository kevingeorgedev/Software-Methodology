package oitkmg.project2;

/**
 * RunProject1 calls on the Gym Manager class to start running
 * the Gym Manager. This class allows for the user to start
 * inputting commands.
 * @author Kevin George, Omar Talaat
 */
public class RunProject2 {

    /**
     * Initiates the Gym Manager
     * @param args an array of command-line arguments for the application.
     */
    public static void main(String[] args) {
        new GymManager().run();
    }
}
