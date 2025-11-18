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
    private Player player1;
    private JTextArea text_area;
    private JTextField field_answer;
    private JLabel label_room;
    private JLabel label_riddle;
    private JLabel label_hint;
    private JLabel label_player1;
    private JLabel label_bot1;
    private JLabel label_bot2;
    private JButton button_submit;
    private JButton button_hint;
    private JButton button_skip;


    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setButtons();
        field_answer = new JTextField();
        text_area = new JTextArea();
        label_room = new JLabel();
        label_riddle = new JLabel();
        sim = new Simulation();
        setLayout(null);
        setVisible(true);
    }

    /**
     * Opens the GUI for the player to play the game
     */
    public void openGui() {
        // Sets up bot players
        sim.startGame("Player 1"); // Testing with default name
        sim.addBot("1");
        sim.addBot("2");
        text_area.setBounds(0, 0, 750, 375);
        field_answer.setBounds(100, 400, 200, 25);
        label_room.setBounds(0, 0, 600, 25);
        label_riddle.setBounds(200, 200, 600, 25);

        startGame();
    }
    
    private void startGame() {
        updateRoom();
        // Action listeners for buttons (If it's a new room set visible for skip button to false)
        button_submit.addActionListener(e -> {
            String guess = field_answer.getText();
            boolean correct = sim.checkGuess(guess);

            //Checks to see if the guess was correct
            if (correct) {
                sim.moveToNextRoom();
                updateRoom();
                if (sim.hasPlayerWon()) {
                    endGame();
                }
            } else {

                if (sim.getPlayer().getNumOfIncorrectGuesses() > 2) { // if number of incorrect guesses > 2, allow skip
                    button_skip.setVisible(true);
                }
            }
        });

        button_hint.addActionListener(e -> { // if player requests hint, show first letter of answer and add penalty
            sim.getPlayer().getHint();
        });

        button_skip.addActionListener(e -> {
            sim.moveToNextRoom();
            updateRoom();
        });
        
        add(field_answer);
        //add(text_area);
        add(label_room);
        add(label_riddle);
    }

    private void endGame() {
        label_riddle.setText("Congratulations! You've made it out!");
        label_room.setText("Outside the Riddle Rooms");
        button_submit.setEnabled(false);
        button_hint.setEnabled(false);
        button_skip.setEnabled(false);
    }

    /**
     * Updates the room information in the GUI
     */
    private void updateRoom() {
        button_skip.setVisible(false);
        field_answer.setText("");
        label_room.setText("Room " + String.valueOf(sim.getCurrentRoomNumber()));
        label_riddle.setText(sim.getCurrentRiddle());
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
