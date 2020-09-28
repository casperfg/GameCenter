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
    final JFrame frame;
    final JTabbedPane tab;
    JPanel gamePanel, aboutPanel, helpPanel;
    JPanel gameCenter, gameEast, gameWest, gameNorth, gameSouth;

    final JLabel dice1;
    final JLabel dice2;
    Icon diceIcon1, diceIcon2, player1, player2;
    final Icon[][] boardImage = new Icon[6][6];
    final JButton[][] board = new JButton[6][6];

    JButton btn_start;
    JButton btn_restart;
    final JButton btn_player1;
    final JButton btn_player2;
    String nr;
    int boardPath;
    int clickCounter;
    int player1_position, player2_position, player1_lastPosition, player2_lastPosition;
    boolean gameOver;

    final int[][] game = {{36, 35, 34, 33, 32, 31},
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
        frame.setSize(675, 810);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    void gamePage() {
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
        gameNorth.setLayout(new GridLayout(1, 2));
        gameSouth.setLayout(new FlowLayout());

        player1 = new ImageIcon("Images/player1.gif");
        player2 = new ImageIcon("Images/player2.gif");

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

        diceIcon1 = new ImageIcon("Images/Dice/Dice1.jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice3.jpg");

        dice1.setIcon(diceIcon1);
        dice2.setIcon(diceIcon2);

        gameSouth.add(btn_start);
        gameSouth.add(btn_player1);
        gameSouth.add(dice1);
        gameSouth.add(dice2);
        gameSouth.add(btn_player2);
        gameSouth.add(btn_restart);

        gamePanel.add(gameCenter, BorderLayout.CENTER);
        gamePanel.add(gameNorth, BorderLayout.NORTH);
        gamePanel.add(gameSouth, BorderLayout.SOUTH);

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
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + nr + ".jpg");
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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (n==game[i][j]) {
                    board[i][j].setIcon(player);
                }
            }
        }

        //Check for ladder
        if (player1_position == 12){
            board[4][0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1_position = 23;
                    board[2][1].setIcon(player1);
                    player1_lastPosition = 12;
                    board[4][0].removeActionListener(this);
                    board[4][0].setIcon(boardImage[4][0]);
                    btn_player2.setEnabled(true);
                }
            });
        }
        if (player2_position == 12){
            board[4][0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player2_position = 23;
                    board[4][0].setIcon(player2);
                    player2_lastPosition = 12;
                    board[4][0].removeActionListener(this);
                    board[4][0].setIcon(boardImage[4][0]);
                    btn_player1.setEnabled(true);
                }
            });
        }
        if (player1_position == 17){
            board[3][4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1_position = 20;
                    board[2][4].setIcon(player1);
                    player1_lastPosition = 17;
                    board[3][4].removeActionListener(this);
                    board[3][4].setIcon(boardImage[3][4]);
                    btn_player2.setEnabled(true);
                }
            });
        }
        if (player2_position == 17){
            board[3][4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player2_position = 20;
                    board[2][4].setIcon(player2);
                    player2_lastPosition = 17;
                    board[3][4].removeActionListener(this);
                    board[3][4].setIcon(boardImage[3][4]);
                    btn_player1.setEnabled(true);
                }
            });
        }

        //Check for snake
        if(player1_position == 16){
            board[3][3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1_position = 2;
                    board[5][1].setIcon(player1);
                    player1_lastPosition = 16;
                    board[3][3].removeActionListener(this);
                    board[3][3].setIcon(boardImage[3][3]);
                    btn_player2.setEnabled(true);
                }
            });
        }
        if(player2_position == 16){
            board[3][3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player2_position = 2;
                    board[5][1].setIcon(player2);
                    player2_lastPosition = 16;
                    board[3][3].removeActionListener(this);
                    board[3][3].setIcon(boardImage[3][3]);
                    btn_player1.setEnabled(true);
                }
            });
        }
        if(player1_position == 33){
            board[0][3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1_position = 19;
                    board[2][5].setIcon(player1);
                    player1_lastPosition = 16;
                    board[0][3].removeActionListener(this);
                    board[0][3].setIcon(boardImage[0][3]);
                    btn_player2.setEnabled(true);
                }
            });
        }
        if(player2_position == 33){
            board[0][3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player2_position = 19;
                    board[2][5].setIcon(player2);
                    player2_lastPosition = 33;
                    board[0][3].removeActionListener(this);
                    board[0][3].setIcon(boardImage[0][3]);
                    btn_player1.setEnabled(true);
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
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(last_position == game[i][j]) {
                    board[i][j].setIcon(boardImage[i][j]);
                }
            }
        }

    }

    void resetDice() {
        int dice_thrw = Dice.Roll();
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + dice_thrw + ".jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice" + dice_thrw + ".jpg");
        dice1.setIcon(diceIcon1);
        dice2.setIcon(diceIcon2);
    }

    void move(Icon player, int position, int lastPosition) {
            travel(player,position);
            redraw(lastPosition);
    }

    void checkEqualDice(int die1, int die2, JButton btn1, JButton btn2){
        if (die1 == die2){
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
        else
        {
            btn1.setEnabled(false);
            btn2.setEnabled(true);
        }
    }
    void placePlayerAtStart(Icon player){
        if (clickCounter == 1) {
            board[5][0].setIcon(player);
        } else {
            board[5][0].setIcon(boardImage[5][0]);
        }
    }
    void checkIfLadderOrSnake(int player_position){
        if (player_position == 12 || player_position == 17
                || player_position == 16 || player_position == 33)
        {
            btn_player2.setEnabled(false);
            btn_player1.setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent event) {
        int totalDiceValue, diceValue1, diceValue2;
        try {
            if (event.getSource() == btn_player1) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.dice1);
                diceValue2 = throwDice(this.dice2);

                totalDiceValue = diceValue1 + diceValue2;
                player1_position += totalDiceValue;
                player1_lastPosition = player1_position - totalDiceValue;
                move(player1, player1_position, player1_lastPosition);

                placePlayerAtStart(player2);
                checkEqualDice(diceValue1, diceValue2, btn_player1, btn_player2);

                checkIfLadderOrSnake(player1_position);
            } else if (event.getSource() == btn_player2) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.dice1);
                diceValue2 = throwDice(this.dice2);

                totalDiceValue = diceValue1 + diceValue2;
                player2_position = player2_position + totalDiceValue;
                player2_lastPosition = player2_position - totalDiceValue;
                move(player2, player2_position, player2_lastPosition);

                placePlayerAtStart(player1);
                checkEqualDice(diceValue1, diceValue2, btn_player2, btn_player1);
                checkIfLadderOrSnake(player2_position);

            } else if (event.getSource() == btn_start) {
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
