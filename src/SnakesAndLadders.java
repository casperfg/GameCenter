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
    Icon[][] boardImage = new Icon[6][6];
    JButton[][] board = new JButton[6][6];

    JButton btn_start, btn_restart, btn_player1, btn_player2;
    String nr;
    int boardPath;
    int clickCounter;
    int player1_position, player2_position;
    boolean gameOver;

    int[][] game = {{36, 35, 34, 33, 32, 31},
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

        gamePanel = new JPanel();gameNorth = new JPanel();
        gameWest = new JPanel(); gameCenter = new JPanel();
        gameEast = new JPanel(); gameSouth = new JPanel();

        gameCenter.setLayout(new GridLayout(6, 6));
        gamePanel.setLayout(new BorderLayout());

        gameWest.setLayout(new FlowLayout());
        gameNorth.setLayout(new FlowLayout());
        gameSouth.setLayout(new FlowLayout());

        player1 = new ImageIcon("Images/player1.gif");
        player2 = new ImageIcon("Images/player2.gif");

        label1.setIcon(player1);
        label2.setIcon(player2);

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 6; column++) {
                board[row][column] = new JButton();
                boardPath = game[row][column];
                nr = Integer.toString(boardPath);
                boardImage[row][column] = new ImageIcon("Images/" + nr + ".jpg");
                board[row][column].setIcon(boardImage[row][column]);
                gameCenter.add(board[row][column]);
            }
        }

        diceIcon1 = new ImageIcon("Images/Dice/dice.gif");
        diceIcon2 = new ImageIcon("Images/Dice/dice.gif");
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

        gamePanel.add(gameCenter, BorderLayout.CENTER);
        gamePanel.add(gameWest, BorderLayout.WEST);
        gamePanel.add(gameNorth, BorderLayout.NORTH);
        gamePanel.add(gameSouth, BorderLayout.SOUTH);
        gamePanel.add(gameEast, BorderLayout.EAST);

        tab.addTab("Snakes & Ladders", gamePanel);
    }

    void aboutPage() {
        aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        tab.addTab("About", aboutPanel);
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
        nr = Integer.toString(dice_throw);
        diceIcon1 = new ImageIcon("Images/Dice/dice" + nr + ".png");
        die.setIcon(diceIcon1);
        return dice_throw;
    }

    void startingPlayer() {
        Random randomNo = new Random();
        int n = randomNo.nextInt(3);

        if (n==0) {
            startingPlayer();
        }

        if (n==1) {
            btn_player1.setEnabled(true);
            btn_player2.setEnabled(false);

            board[5][0].setIcon(player1);
            player1_position = 1;
            player2_position = 0;
            clickCounter = 0;
        } else if (n==2) {
            btn_player2.setEnabled(true);
            btn_player1.setEnabled(false);

            board[5][0].setIcon(player2);
            player1_position = 0;
            player2_position = 1;
            clickCounter = 0;
        }

    }


    void travel(Icon player, int n) {
        int x, y;
        boolean foundPlace = false;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(n == game[i][j]) {
                    board[i][j].setIcon(player);
                }
            }
        }

        if(n == 12) {
            btn_player2.setEnabled(false);
            btn_player1.setEnabled(false);
            board[0][4].addActionListener(e -> travel(player, 20));

        }
        if(n == 5){
            btn_player2.setEnabled(false);
            btn_player1.setEnabled(false);
            board[5][4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    travel(player,23);
                }
            });
        }

    }

    void drawBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j].setIcon(boardImage[i][j]);
            }
        }
    }

    void redraw(int last_position){
        int x, y;
        boolean foundPlace = false;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(last_position == game[i][j]) {
                    board[i][j].setIcon(boardImage[i][j]);
                }
            }
        }

    }

    void resetDice() {
        diceIcon1 = new ImageIcon("Images/Dice/dice.gif");
        diceIcon2 = new ImageIcon("Images/Dice/dice.gif");
        dice1.setIcon(diceIcon1);
        dice2.setIcon(diceIcon2);
    }

    void move(Icon player, int position, int lastPosition){
            travel(player,position);
            redraw(lastPosition);
    }


    int getDiceValue() {
        int dice1_value, dice2_value, total_diceValue;
        dice1_value = throwDice(this.dice1);
        dice2_value = throwDice(this.dice2);
        total_diceValue = dice1_value + dice2_value;

        return total_diceValue;
    }

    public void actionPerformed(ActionEvent event) {
        int diceValue, player1_lastPosition, player2_lastPosition;
        try {
            if (event.getSource()==btn_player1) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue = getDiceValue();
                player1_position += diceValue;
                player1_lastPosition = player1_position - diceValue;
                move(player1, player1_position, player1_lastPosition);
                if (clickCounter==1) {
                    board[5][0].setIcon(player2);
                } else {
                    board[5][0].setIcon(boardImage[5][0]);
                }
                btn_player1.setEnabled(false);
                btn_player2.setEnabled(true);
            } else if (event.getSource()==btn_player2) {
                clickCounter++;
                btn_restart.setEnabled(true);


                diceValue = getDiceValue();
                player2_position = player2_position + diceValue;
                player2_lastPosition = player2_position - diceValue;
                move(player2, player2_position, player2_lastPosition);

                if (clickCounter==1) {
                    board[5][0].setIcon(player1);
                } else {
                    board[5][0].setIcon(boardImage[5][0]);
                }

                btn_player1.setEnabled(true);
                btn_player2.setEnabled(false);
            } else if (event.getSource()==btn_start) {
                btn_start.setEnabled(false);
                btn_restart.setEnabled(true);
                startingPlayer();
            } else if (event.getSource()==btn_restart) {
                btn_restart.setEnabled(false);
                gameOver = false;
                drawBoard();
                resetDice();
                startingPlayer();
            }
        } catch (Exception button) {
            System.out.println("Error in BUTTON:" + button);
        }
    }
}
