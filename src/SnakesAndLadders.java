import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class SnakesAndLadders implements ActionListener {
    JFrame frame;
    JTabbedPane tab;
    JPanel gamePanel, aboutPanel, helpPanel;
    JPanel gameCenter, gameEast, gameWest, gameNorth, gameSouth;

    JLabel dice1, dice2;
    Icon diceIcon1, diceIcon2, player1, player2;
    Icon[][] boardTileImage = new Icon[6][6];
    JButton[][] boardTiles = new JButton[6][6];

    JButton btn_start, btn_restart, btn_player1, btn_player2;
    String string;
    int path, previ, prevj;
    int player1_position, player2_position;
    boolean gameOver;

    int[][] game = { {36, 35, 34, 33, 32, 31},
                     {25, 26, 27, 28, 29, 30},
                     {24, 23, 22, 21, 20, 19},
                     {13, 14, 15, 16, 17, 18},
                     {12, 11, 10,  9,  8,  7},
                     { 1,  2,  3,  4,  5,  6}};

    public SnakesAndLadders() throws IOException {
        frame = new JFrame("Snakes and Ladders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        tab = new JTabbedPane(JTabbedPane.TOP);

        btn_player1 = new JButton("Player 1");
        btn_player1.addActionListener(this);
        btn_player1.setEnabled(false);

        btn_player2 = new JButton("Player 2");
        btn_player2.addActionListener(this);
        btn_player2.setEnabled(false);

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
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();

        btn_restart = new JButton("Restart");
        btn_restart.setEnabled(false);
        btn_restart.addActionListener(this);

        btn_start = new JButton("Start");
        btn_start.addActionListener(this);

        gamePanel =  new JPanel();  gameNorth  =  new JPanel();
        gameWest  =  new JPanel();  gameCenter =  new JPanel();
        gameEast  =  new JPanel();  gameSouth  =  new JPanel();

        gameCenter.setLayout(new GridLayout(6, 6));
        gamePanel.setLayout(new BorderLayout());

        gameWest.setLayout(new FlowLayout());
        gameNorth.setLayout(new FlowLayout());
        gameSouth.setLayout(new FlowLayout());

        player1 = new ImageIcon("Images/player1.gif");
        player2 = new ImageIcon("Images/player2.gif");

        label1.setIcon(player1);
        label2.setIcon(player2);

        for (int row = 0; row < 6; row++){
            for(int column = 0; column < 6; column++){
                boardTiles[row][column] = new JButton();
                path = game[row][column];
                string = Integer.toString(path);
                boardTileImage[row][column] = new ImageIcon("Images/" + string + ".jpg");
                boardTiles[row][column].setIcon(boardTileImage[row][column]);
                gameCenter.add(boardTiles[row][column]);
            }
        }

        string = Integer.toString(1);
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + string + ".jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice" + string + ".jpg");
        dice1.setIcon(diceIcon1);
        dice2.setIcon(diceIcon2);

        gameSouth.add(label1);
        gameSouth.add(btn_player1);
        gameSouth.add(dice1);
        gameSouth.add(dice2);
        gameSouth.add(btn_player2);
        gameSouth.add(label2);


        gameNorth.add(btn_start);
        gameNorth.add(btn_restart);

        gamePanel.add(gameCenter, BorderLayout.CENTER); gamePanel.add(gameWest,  BorderLayout.WEST);
        gamePanel.add(gameNorth,  BorderLayout.NORTH);  gamePanel.add(gameSouth, BorderLayout.SOUTH);
        gamePanel.add(gameEast,   BorderLayout.EAST);

        tab.addTab("Snakes & Ladders", gamePanel);
    }

    void aboutPage(){
        aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
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

    void startingPlayer() {
       Random randomNo = new Random();
       int n = randomNo.nextInt(3);

       if(n == 0){
            startingPlayer();
       }

       if(n == 1){
        btn_player1.setEnabled(true);
        btn_player2.setEnabled(false);
       }
       else if(n == 2){
           btn_player2.setEnabled(true);
           btn_player1.setEnabled(false);
       }

    }

    void travel(Icon player, int n) throws InterruptedException {
        int i;
        int j = 0;
        boolean stop = false;

        if (!gameOver) {
            reDrawBoard();
            for (i = 0; i < 6; i++) {
                for (j = 0; j < 6; j++) {
                    if (n == game[i][j]){
                        stop = true;
                        break;
                    }
                }
                if(stop){
                    break;
                }
            }
            if (stop){
            boardTiles[i][j].setIcon(player);
            }
        }
    }

    void reDrawBoard(){
        for (int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++ ){
                boardTiles[i][j].setIcon(boardTileImage[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent event) {
        int dice1_value, dice2_value, total_diceValue;
        try {
            if  (event.getSource()==btn_player1){
                dice1_value = throwDice(this.dice1);
                dice2_value = throwDice(this.dice2);
                total_diceValue = dice1_value + dice2_value;
                player1_position = player1_position + total_diceValue;

                travel(player1, player1_position);
                btn_player1.setEnabled(false);
                btn_player2.setEnabled(true);
            }
            else if (event.getSource() == btn_player2){
                dice1_value = throwDice(this.dice1);
                dice2_value = throwDice(this.dice2);

                total_diceValue = dice1_value + dice2_value;
                player2_position = player2_position + total_diceValue;

                travel(player2, player2_position);
                btn_player1.setEnabled(true);
                btn_player2.setEnabled(false);
            }
            else if (event.getSource()==btn_start){
                btn_start.setEnabled(false);
                btn_restart.setEnabled(true);
                startingPlayer();
            }
            else if (event.getSource() == btn_restart){
                player1_position = 0;
                player2_position = 0;
                reDrawBoard();
            }
        } catch (Exception button){
            System.out.println("Error in BUTTON:" + button);
        }
    }
}
