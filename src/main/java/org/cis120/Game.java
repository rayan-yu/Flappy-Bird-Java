package org.cis120;

import org.cis120.FlappyBird.FlappyBird;
import javax.swing.*;

public class Game {
    /**
     * Main method run to start and run the game. Initializes the runnable game
     * class of your choosing and runs it. IMPORTANT: Do NOT delete! You MUST
     * include a main method in your final submission.
     */
    public static void main(String[] args) {
        // message dialog at beginning of game
        JOptionPane.showMessageDialog(
                null, "Welcome to Flappy Bird.\n" +
                        "You are the bird, and your objective is to " +
                        "fly past as many pipes as possible by "
                        +
                        "navigating through the space " +
                        "between the pipes accordingly\n" +
                        "Your score for how many pipes you " +
                        "have cleared each game will be displayed in the "
                        +
                        "top right corner, and a high score " +
                        "will be kept for the time you keep playing.\n"
                        +
                        "You can choose your bird, background, and difficulty " +
                        "style in the following dialogs.\n" +
                        "You can save and exit in the middle of a game, " +
                        "or save your high score after losing.\n"
                        +
                        "Here are the instructions:\n" +
                        " - Press the space bar to jump\n" +
                        " - Press the P key to pause the game\n" +
                        " - Press the R key to continue the game when paused\n" +
                        " - Press the Esc. key to save/exit when paused"
        );
        // create the frame for flappybird
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(800, 800);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create flappybird
        FlappyBird p = new FlappyBird();
        frame.setContentPane(p);
        p.requestFocus();
        frame.setVisible(true);
    }
}
