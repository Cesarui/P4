//Andre Larrazabal
//package P4.src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
    //ATTRIBUTES
    private final Simulation sim;
    private Player player_1;
    private JTextArea text_area;
    private JTextField field_answer;
    private JLabel room_label;
    private JButton button_submit;
    private JButton button_hint;
    private JButton button_skip;


    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setButtons();
        field_answer = new JTextField();
        text_area = new JTextArea();
        room_label = new JLabel();
        sim = new Simulation();
        setLayout(null);
        setVisible(true);
    }

    /**
     * Opens the GUI for the player to play the game
     */
    public void openGui() {
        // Sets up bot players
        sim.addBot("1");
        sim.addBot("2");
        text_area.setBounds(0, 0, 750, 375);
        field_answer.setBounds(100, 400, 200, 25);
        
        //Game begins
        sim.startGame("Player 1"); // Testing with default name
        room_label.setText("Room " + String.valueOf(sim.getCurrentRoomNumber()) + ": " + sim.getCurrentRiddle());


            // Action listeners for buttons (If it's a new room set visible for skip button to false)
            // if (player_1.getNumOfIncorrectGuesses() > 2) {
            //     button_skip.setVisible(true);
            // } else {
            //     button_skip.setVisible(false);
            // }

            button_submit.addActionListener(e -> {

            String guess = field_answer.getText();
            boolean correct = sim.checkGuess(guess);

            //Checks to see if the guess was correct
            if (correct) {
                text_area.setText("Correct! Moving to next room.");
                sim.moveToNextRoom();
            } else {
                player_1.numOfIncorrectGuesses++;
                if (player_1.numOfIncorrectGuesses > 2) { // if number of incorrect guesses > 2, allow skip
                    button_skip.setVisible(true);
                }
            }
            });

            button_hint.addActionListener(e -> { // if player requests hint, show first letter of answer and add penalty
                player_1.getHint();
            
            });

            button_skip.addActionListener(e -> {
                sim.moveToNextRoom();
                text_area.setText("Room " + String.valueOf(sim.getCurrentRoomNumber()) + ": " + sim.getCurrentRiddle());
            });
        


        add(field_answer);
        add(text_area);
    }
    
    /**
     * Private helper function to set up the buttons for the game
     */
    private void setButtons() {
        button_submit = new JButton("Submit");
        button_hint = new JButton("Hint");
        button_skip = new JButton("Skip");
        button_submit.setBounds(325, 400, 100, 25);
        button_skip.setBounds(550, 400, 75, 25);
        button_hint.setBounds(460, 400, 75, 25);
        add(button_hint);
        add(button_skip);
        add(button_submit);
    }

}
