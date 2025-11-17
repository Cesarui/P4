//Andre Larrazabal
//package P4.src;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI {
    //ATTRIBUTES
    Player player_1;
    JFrame frame;
    JTextArea text_area;
    JButton button_start;
    JButton button_skip;
    JPanel cardPanel;
    JPanel scene_menu;
    JPanel scene_hallway;
    JPanel scene_room;

    public GUI() {
        frame = new JFrame();
        text_area = new JTextArea();
        button_start = new JButton("Start");
        button_skip = new JButton("Skip");
        cardPanel = new JPanel(new CardLayout());
        scene_menu = new JPanel();
        scene_hallway = new JPanel();
        scene_room = new JPanel();
    }

    /**
     * Opens the GUI for the player to play the game
     */
    public void openGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setButtons();
        cardPanel.add(menu(), "menu");
        cardPanel.add(game(), "game");
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private JPanel menu() {
        scene_menu.setLayout(null);
        scene_menu.setBounds(0, 0, 600, 600);


        return scene_menu;
    }
    
    private JPanel game() {
        return scene_room;
    }
    /**
     * Private helper function to set up the buttons for the game
     */
    private void setButtons() {
        button_start.setBounds(200, 400, 100, 25);
        frame.add(button_start);
    }

}
