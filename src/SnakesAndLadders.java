import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class SnakesAndLadders implements ActionListener {
    JFrame frame;
    JTabbedPane tab;
    JPanel gamePanel, aboutPanel, helpPanel;
    JPanel gameC, gameE, gameW, gameN, gameS;

    JLabel dice1, dice2;
    Icon diceIcon1, diceIcon2;
    Icon[][] boardTileImage = new Icon[6][6];
    JButton[][] boardTiles = new JButton[6][6];

    JButton start, restart, player1, player2;
    String string;
    int path;

    int[][] game = { {36, 35, 34, 33, 32, 31},
                     {25, 26, 27, 28, 29, 30},
                     {24, 23, 22, 21, 20, 19},
                     {13, 14, 15, 16, 17, 18},
                     {12, 11, 10,  9,  8,  7},
                     { 1,  2,  3,  4,  5,  6}};

    public SnakesAndLadders() throws IOException {
        frame = new JFrame("Snakes and Ladders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tab = new JTabbedPane(JTabbedPane.TOP);

        frame.setLayout(new BorderLayout());

        player1 = new JButton("Player 1");
        player1.addActionListener(this);
        player1.setEnabled(false);

        player2 = new JButton("Player 2");
        player2.addActionListener(this);
        player2.setEnabled(false);

        dice1 = new JLabel();
        dice2 = new JLabel();

        gamePage();
        aboutPage();
        helpPage();

        frame.add(tab, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setSize(675, 760);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    void gamePage() {
        restart = new JButton("Restart");
        restart.setEnabled(false);
        restart.addActionListener(this);

        start = new JButton("Start");
        start.addActionListener(this);

        gamePanel = new JPanel();

        gameC = new JPanel();
        gameN = new JPanel();
        gameW = new JPanel();
        gameS = new JPanel();
        gameE = new JPanel();

        gameC.setLayout(new GridLayout(6, 6));
        gamePanel.setLayout(new BorderLayout());

        gameW.setLayout(new FlowLayout());
        gameN = new JPanel(new FlowLayout());
        gameS = new JPanel(new FlowLayout());

        for (int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                boardTiles[i][j] = new JButton();
                path = game[i][j];
                string = Integer.toString(path);
                boardTileImage[i][j] = new ImageIcon("Images/" + string + ".jpg");
                boardTiles[i][j].setIcon(boardTileImage[i][j]);
                gameC.add(boardTiles[i][j]);
            }
        }

        string = Integer.toString(1);
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + string + ".jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice" + string + ".jpg");
        dice1.setIcon(diceIcon1);
        dice2.setIcon(diceIcon2);

        gameS.add(player1);
        gameS.add(dice1);
        gameS.add(dice2);
        gameS.add(player2);

        gameN.add(start);
        gameN.add(restart);

        gamePanel.add(gameC, BorderLayout.CENTER);
        gamePanel.add(gameW, BorderLayout.WEST);
        gamePanel.add(gameN, BorderLayout.NORTH);
        gamePanel.add(gameS, BorderLayout.SOUTH);
        gamePanel.add(gameE, BorderLayout.EAST);

        tab.addTab("Snakes & Ladders", gamePanel);
    }

    void aboutPage() throws IOException {
        aboutPanel = new JPanel();


        aboutPanel.setLayout(new BorderLayout());;

        tab.addTab("About",aboutPanel);
    }

    void helpPage() throws IOException {
        helpPanel = new JPanel();

        String text = new String(Files.readAllBytes(Paths.get("help.txt")));
        JTextArea tutorial = new JTextArea(text);
        tutorial.setFont(tutorial.getFont().deriveFont(17f));

        helpPanel.setLayout(new BorderLayout());
        helpPanel.add(tutorial, BorderLayout.CENTER);
        tab.addTab("Help", helpPanel);
    }

    int throwDice(JLabel die) {
        int dice_throw = Dice.Roll();
        string = Integer.toString(dice_throw);
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + string + ".jpg");
        die.setIcon(diceIcon1);
        return dice_throw;
    }

    public void actionPerformed(ActionEvent event) {
        int d1, d2;
        try {
            if(event.getSource()==player1){
                d1 = throwDice(dice1);
                d2 = throwDice(dice2);
            }
            else if (event.getSource()==start){
                start.setEnabled(false);
                restart.setEnabled(true);
                player1.setEnabled(true);
            }
        } catch (Exception button){
            System.out.println("Error in BUTTON:" + button);
        }
    }
}
