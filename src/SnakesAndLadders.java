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

    JPanel gamePanel, aboutPanel, helpPanel, gameCENTER, gameNORTH, gameSOUTH;

    JLabel diceLabel1, diceLabel2;

    JButton btn_start, btn_restart,
            btn_player1, btn_player2;

    Icon diceIcon1, diceIcon2, player1Right, player2Right, player1Left, player2Left;


    Icon[][] boardImage = new Icon[6][6];
    Icon[][] winScreen = new Icon[6][6];
    JButton[][] board = new JButton[6][6];

    String blockNr;
    int clickCounter, player1_position, player2_position,
            player1_lastPosition, player2_lastPosition;
    boolean gameOver;

    final int[][] game = {{36, 35, 34, 33, 32, 31},             //left
                          {25, 26, 27, 28, 29, 30},             //right
                          {24, 23, 22, 21, 20, 19},             //left
                          {13, 14, 15, 16, 17, 18},             //right
                          {12, 11, 10,  9,  8,  7},             //left
                          { 1,  2,  3,  4,  5,  6}};            //right

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

        diceLabel1 = new JLabel();
        diceLabel2 = new JLabel();


        gamePage1();
        aboutPage();
        helpPage();

        frame.add(tab, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setSize(675, 810);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


    void gamePage1() {
        btn_restart = new JButton("Restart");
        btn_restart.setEnabled(false);
        btn_restart.addActionListener(this);
        btn_start = new JButton("Start");
        btn_start.addActionListener(this);

        gamePanel = new JPanel();
        gameNORTH = new JPanel();
        gameCENTER = new JPanel();
        gameSOUTH = new JPanel();

        gameCENTER.setLayout(new GridLayout(6, 6));
        gamePanel.setLayout(new BorderLayout());
        gameNORTH.setLayout(new GridLayout(1, 2));
        gameSOUTH.setLayout(new FlowLayout());

        player1Right = new ImageIcon("Images/player1.gif");
        player2Right = new ImageIcon("Images/player2.gif");
        player1Left = new ImageIcon("Images/player1Left.gif");
        player2Left = new ImageIcon("Images/player2Left.gif");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = new JButton();

                blockNr = Integer.toString(game[i][j]);
                boardImage[i][j] = new ImageIcon("Images/" + blockNr + ".jpg");

                board[i][j].setIcon(boardImage[i][j]);

                gameCENTER.add(board[i][j]);
            }
        }

        diceIcon1 = new ImageIcon("Images/Dice/Dice1.jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice3.jpg");

        diceLabel1.setIcon(diceIcon1);
        diceLabel2.setIcon(diceIcon2);


        gameSOUTH.add(btn_start);
        gameSOUTH.add(btn_player1);
        gameSOUTH.add(diceLabel1);
        gameSOUTH.add(diceLabel2);
        gameSOUTH.add(btn_player2);
        gameSOUTH.add(btn_restart);

        gamePanel.add(gameCENTER, BorderLayout.CENTER);
        gamePanel.add(gameNORTH, BorderLayout.NORTH);
        gamePanel.add(gameSOUTH, BorderLayout.SOUTH);

        tab.addTab("Game", gamePanel);
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
        blockNr = Integer.toString(dice_throw);
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + blockNr + ".jpg");
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

            board[5][0].setIcon(player1Right);
            player1_position = 1;
            player2_position = 0;
            clickCounter = 0;
        } else if (n==2) {
            btn_player2.setEnabled(true);
            btn_player1.setEnabled(false);

            board[5][0].setIcon(player2Right);
            player1_position = 0;
            player2_position = 1;
            clickCounter = 0;
        }

    }

    void travel(Icon player, int n) throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (n==game[i][j]) {
                    Thread.sleep(200);
                    if(i % 2 == 0){
                        if(player == player1Right)
                            board[i][j].setIcon(player1Left);
                        else if (player == player2Right)
                            board[i][j].setIcon(player2Left);
                    }
                    else
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
                    board[2][1].setIcon(player1Right);
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
                    board[2][1].setIcon(player2Right);
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
                    board[2][4].setIcon(player1Right);
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
                    board[2][4].setIcon(player2Right);
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
                    board[5][1].setIcon(player1Right);
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
                    board[5][1].setIcon(player2Right);
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
                    board[2][5].setIcon(player1Right);
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
                    board[2][5].setIcon(player2Right);
                    player2_lastPosition = 33;
                    board[0][3].removeActionListener(this);
                    board[0][3].setIcon(boardImage[0][3]);
                    btn_player1.setEnabled(true);
                }
            });
        }

        if(player1_position > 36)
        {
           player1_position = backTrack(player1Right, player1_position);

            travel(player1Right, player1_position);
            redraw(player1_lastPosition);
            System.out.println("Player 1 pos: " + player1_position);
            System.out.println("Player 1 last pos: " + player1_lastPosition);
        }

        if (player2_position > 36){

            travel(player2Right, player2_position);
            player2_position = backTrack(player2Right, player2_position);
            System.out.println("Player 2 pos: " + player2_position);
            System.out.println("Player 2 last pos : " + player2_lastPosition);
        }

        if(player1_position == 36 || player2_position == 36){
            gameOver = true;
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
                    if(player1_lastPosition == player2_position){
                        board[i][j].setIcon(player2Right);
                    }
                    else if(player2_lastPosition == player1_position) {
                        board[i][j].setIcon(player1Right);
                    }
                    else
                        board[i][j].setIcon(boardImage[i][j]);
                }
            }
        }

    }

    void resetDice() {
        int dice_thrw = Dice.Roll();
        diceIcon1 = new ImageIcon("Images/Dice/Dice" + dice_thrw + ".jpg");
        diceIcon2 = new ImageIcon("Images/Dice/Dice" + dice_thrw + ".jpg");
        diceLabel1.setIcon(diceIcon1);
        diceLabel2.setIcon(diceIcon2);
    }

    int backTrack(Icon player, int position){
        int difference = position - 36;
        position = 36 - difference;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(position == game[i][j]){
                    board[i][j].setIcon(player);
                }
            }
        }
        return position;
    }

    void move(Icon player, int position, int lastPosition) throws InterruptedException {
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
        }
        else {
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

    void playerWin(String player){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                blockNr = Integer.toString(game[i][j]);
                winScreen[i][j] = new ImageIcon("Images/Win"+ player +"/Win" + blockNr + ".jpg");
                board[i][j].setIcon(winScreen[i][j]);
            }
        }
        btn_player1.setEnabled(false);
        btn_player2.setEnabled(false);
    }

    public void actionPerformed(ActionEvent event) {
        int totalDiceValue, diceValue1, diceValue2;
        try {
            if (event.getSource() == btn_player1) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.diceLabel1);
                diceValue2 = throwDice(this.diceLabel2);

                totalDiceValue = diceValue1 + diceValue2;
                System.out.println("Dice:" + totalDiceValue);
                player1_position += totalDiceValue;
                player1_lastPosition = player1_position - totalDiceValue;
                move(player1Right, player1_position, player1_lastPosition);

                placePlayerAtStart(player2Right);
                checkEqualDice(diceValue1, diceValue2, btn_player1, btn_player2);
                checkIfLadderOrSnake(player1_position);
                if(gameOver)
                    playerWin("Player1");
            } else if (event.getSource() == btn_player2) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.diceLabel1);
                diceValue2 = throwDice(this.diceLabel2);

                totalDiceValue = diceValue1 + diceValue2;
                System.out.println("Dice:" + totalDiceValue);
                player2_position = player2_position + totalDiceValue;
                player2_lastPosition = player2_position - totalDiceValue;
                move(player2Right, player2_position, player2_lastPosition);

                placePlayerAtStart(player1Right);
                checkEqualDice(diceValue1, diceValue2, btn_player2, btn_player1);
                checkIfLadderOrSnake(player2_position);

                if(gameOver){
                    playerWin("Player2");
                }
            } else if (event.getSource() == btn_start) {
                gameOver = false;
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
