//Andre Larrazabal
//package P4.src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
    //ATTRIBUTES
    private final Simulation sim;
    private Player player_1;
    private JTextArea text_area;
    private JTextField field_answer;
    private JButton button_hint;
    private JButton button_skip;


    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setButtons();
        field_answer = new JTextField();
        text_area = new JTextArea();
        sim = new Simulation();
    }

    /**
     * Opens the GUI for the player to play the game
     */
    public void openGui() {
        text_area.setBounds(0, 0, 750, 375);
        setLayout(null);
        add(text_area);

        field_answer.setBounds(100, 400, 100, 25);
        add(field_answer);
        setVisible(true);
    }
    
    /**
     * Private helper function to set up the buttons for the game
     */
    private void setButtons() {
        button_hint = new JButton("Hint");
        button_skip = new JButton("Skip");
        button_skip.setBounds(525, 400, 100, 25);
        button_hint.setBounds(400, 400, 100, 25);
        add(button_hint);
        add(button_skip);
    }

}
