//Andrew Larrazabal
//Gabriel Luciano
//package P4.src;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
    //ATTRIBUTES
    private final Simulation sim;
    private JTextField field_answer;
    private JLabel label_room;
    private JLabel label_riddle;
    private JLabel label_hint;
    private JLabel label_incorrect;
    private JLabel label_bonus;
    private JLabel label_player1;
    private JLabel label_bot1;
    private JLabel label_bot2;
    private JButton button_submit;
    private JButton button_hint;
    private JButton button_skip;
    private boolean guess_wrong;

    //CONSTRUCTOR
    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setButtons();
        field_answer = new JTextField();
        label_room = new JLabel();
        label_riddle = new JLabel();
        label_hint = new JLabel();
        label_incorrect = new JLabel();
        label_bonus = new JLabel();
        sim = new Simulation();
        setLayout(null);
        setVisible(true);
        guess_wrong = false;
    }

    //METHODS

    /**
     * Opens the GUI for the player to play the game
     */
    public void openGui() {
        // Sets up bot players
        sim.startGame("Player 1"); // Testing with default name
        sim.addBot("Bot 1");
        sim.addBot("Bot 2");

        sim.simulateBots();

        field_answer.setBounds(100, 400, 200, 25);
        label_room.setBounds(0, 0, 600, 25);
        label_bonus.setBounds(70, 0, 150, 25);
        label_riddle.setBounds(200, 200, 600, 25);
        label_hint.setBounds(200, 300, 600, 25);
        label_incorrect.setBounds(100, 380, 200, 25);

        label_hint.setVisible(false);

        startGame();
    }
    
    /**
     * Starts the game and sets up action listeners for buttons
     */
    private void startGame() {
        add(field_answer);
        add(label_room);
        add(label_riddle);
        add(label_hint);
        add(label_incorrect);
        add(label_bonus);

        updateRoom();
        //Action listeners for buttons (If it's a new room set visible for skip button to false)
        //SOLUTION TO THE RIDDLES ARE FOUND IN THE SIMULATION CLASS
        button_submit.addActionListener(e -> {
            String guess = field_answer.getText();
            boolean correct = sim.checkGuess(guess);

            //Checks to see if the guess was correct
            
            if (sim.hasPlayerWon()) {
                if (!guess_wrong) {
                    sim.moveToNextRoom();
                    updateRoom();
                    //endGame();
                } else {
                    endGame();
                }
            } else {
                if (correct) {
                    sim.moveToNextRoom();
                    updateRoom();
                } else {
                    //REHASH OCCURS WHEN BONUS ROOMS ARE UNLOCKED
                    guess_wrong = true;

                    if (sim.getCurrentRoomNumber() > 10 && guess_wrong) { // if in bonus room and guess is wrong, end game
                        endGame();
                    } else if (sim.getCurrentRoomNumber() == 10 && sim.getCurrentRoom().isSolved() && guess_wrong) { // if you guess wrong from rooms 1-10 and solved room 10, end game
                        endGame();
                    }

                    label_incorrect.setText("Incorrect Guesses: " + sim.getPlayer().getNumOfIncorrectGuesses());
                    if (sim.getPlayer().getNumOfIncorrectGuesses() > 2) { // if number of incorrect guesses > 2, allow skip
                        button_skip.setVisible(true);
                    }
                }      
            }

            if (sim.getCurrentRoomNumber() == 20 && sim.getCurrentRoom().isSolved()) {
                endGame();
            }

        });

        button_hint.addActionListener(e -> { // if player requests hint, show first letter of answer and add penalty
            String hint = sim.showHint(sim.getCurrentRoom());
            label_hint.setVisible(true);
            label_hint.setText(hint);

            if (!hint.contains("_")) {
                button_hint.setEnabled(false);
            }
        });

        button_skip.addActionListener(e -> {
            sim.getPlayer().addTimePenalty(120);
            sim.moveToNextRoom();
            updateRoom();
        });
    }

    /**
     * Ends the game and shows the results screen
     */
    private void endGame() {
        sim.finishGame();
        showResultScreen();
    }

    /**
     * Displays the result screen with final scores and options to play again or exit
     */
    private void showResultScreen() {
        remove(field_answer);
        remove(button_submit);
        remove(button_hint);
        remove(button_skip);
        remove(label_riddle);
        remove(label_hint);
        remove(label_incorrect);
        remove(label_bonus);

        label_room.setText("Game Complete!");
        label_room.setBounds(250, 30, 300, 40);
        label_room.setFont(new Font("Arial", Font.BOLD, 28));

        JTextArea resultsArea = new JTextArea();
        resultsArea.setBounds(150, 100, 450, 250);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        String results = "";
        results += "\n   FINAL RESULTS\n";
        results = results + "   ===================================\n\n";

        int playerTime = sim.getPlayer().getTotalTime();
        results = results + "   " + sim.getPlayer().getName() + ": " + playerTime + " seconds\n";

        int botNum = 1;
        for (Bot bot : sim.getBots()) {
            results = results + "   Bot " + botNum + ": " + bot.getTotalTime() + " seconds\n";
            botNum++;
        }

        results = results + "\n   ===================================\n";

        int minTime = playerTime;
        String winner = sim.getPlayer().getName();

        for(Bot bot : sim.getBots()) {
            if (bot.getTotalTime() < minTime) {
                minTime = bot.getTotalTime();
                winner = bot.getName();
            }
        }

        results = results + "\n   Winner: " + winner + "!\n";
        results = results + "   Completetion Time: " + minTime + " seconds\n\n";

        results = results + "   Your Stats:\n";
        results = results + "   - Hints Used: " + sim.getPlayer().getNumOfIncorrectGuesses() + "\n";
        results = results + "   - Time Penalties: " + sim.getPlayer().getSecondsElapsed() + " seconds\n";

        resultsArea.setText(results);
        add(resultsArea);

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBounds(150, 100, 450, 250);
        add(scrollPane);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(250, 380, 120, 40);
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 14));
        playAgainButton.addActionListener(e -> {
           dispose();
           new GUI().openGui();
        });
        add(playAgainButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(390, 380, 120, 40);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        revalidate();
        repaint();
    }

    /**
     * Updates the room information in the GUI
     */
    private void updateRoom() {
        button_skip.setVisible(false);
        button_hint.setEnabled(true);
        field_answer.setText("");
        label_room.setText("Room " + String.valueOf(sim.getCurrentRoomNumber()));
        label_riddle.setText(sim.getCurrentRiddle());
        label_hint.setText("");
        label_incorrect.setText("Incorrect Guesses: 0");

        if (sim.getCurrentRoomNumber() > 10) {
            label_bonus.setText("Well Done!!! Bonus Rooms Unlocked!! If You Guess Wrong, the Game Ends!");
        }
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
