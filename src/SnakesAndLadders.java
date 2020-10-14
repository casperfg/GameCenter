import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SnakesAndLadders implements ActionListener {
    final JFrame frame;
    final JTabbedPane tab;
    JPanel gamePanel, helpPanel, gameCENTER, gameNORTH, gameSOUTH;
    JLabel diceLabel1, diceLabel2;
    JButton btn_start, btn_restart, btn_player1, btn_player2, btn_exit,
            btn_resign_player1, btn_resign_player2;
    Icon diceIcon1, diceIcon2, player1Right, player2Right, player1Left, player2Left;

    Icon[][] boardImage = new Icon[6][6];       //The icons on the board in array
    Icon[][] winScreen = new Icon[6][6];        //The icons used in win screens
    JButton[][] board = new JButton[6][6];      //The board itself with buttons

    String blockNr;                             //Used in finding right image to right place

    int clickCounter, player1_position, player2_position,
            player1_lastPosition, player2_lastPosition;     //Various variables used in the game

    int totalDiceValue, diceValue1, diceValue2;             //Dice
    boolean gameOver;                                       //check if someone has won

    final int[][] game = {{36, 35, 34, 33, 32, 31},
                          {25, 26, 27, 28, 29, 30},
                          {24, 23, 22, 21, 20, 19},
                          {13, 14, 15, 16, 17, 18},        //Game board with correct numbers
                          {12, 11, 10,  9,  8,  7},
                          { 1,  2,  3,  4,  5,  6}};

    public SnakesAndLadders() {
        frame = new JFrame("Snakes and Ladders");           //main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //exit program on close
        frame.setLayout(new BorderLayout());
        tab = new JTabbedPane(JTabbedPane.TOP);                 //tabs for game and rules

        btn_player1 = new JButton("Player 1");
        btn_player1.addActionListener(this);                  //player 1 buttons
        btn_player1.setEnabled(false);

        btn_player2 = new JButton("Player 2");
        btn_player2.addActionListener(this);                  //player 2 buttons
        btn_player2.setEnabled(false);

        diceLabel1 = new JLabel();
        diceLabel2 = new JLabel();            //Dice


        gamePage();                //Game itself
        rulePage();                //Rule page

        frame.add(tab, BorderLayout.CENTER);    //add them to frame
        frame.setResizable(false);
        frame.setSize(675, 790);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);      //makes the game open center desktop
    }


    void gamePage() {
        btn_restart = new JButton("Restart");
        btn_restart.setEnabled(false);
        btn_restart.addActionListener(this);

        btn_start = new JButton("Start");
        btn_start.addActionListener(this);

        btn_exit = new JButton("Exit");
        btn_exit.addActionListener(this);

        btn_resign_player1 = new JButton("Resign player 1");
        btn_resign_player1.setEnabled(false);
        btn_resign_player1.addActionListener(this);
        btn_resign_player2 = new JButton("Resign player 2");
        btn_resign_player2.setEnabled(false);
        btn_resign_player2.addActionListener(this);

        gamePanel = new JPanel();
        gameNORTH = new JPanel();
        gameCENTER = new JPanel();          //Defines N-W-S-E areas on game-frame
        gameSOUTH = new JPanel();

        gameCENTER.setLayout(new GridLayout(6, 6));
        gamePanel.setLayout(new BorderLayout());
        gameNORTH.setLayout(new GridLayout(1, 2));
        gameSOUTH.setLayout(new FlowLayout());

        player1Right = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                                getResource("Images/player1.gif")));
        player2Right = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                                getResource("Images/player2.gif")));
        player1Left = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                                getResource("Images/player1Left.gif")));        //define the icons
        player2Left = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                                getResource("Images/player2Left.gif")));        //facing right or left

        for (int i = 0; i < 6; i++) {               //places the images on correct buttonNr
            for (int j = 0; j < 6; j++) {
                board[i][j] = new JButton();
                blockNr = Integer.toString(game[i][j]);
                boardImage[i][j] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                                getResource("Images/" + blockNr + ".jpg")));

                board[i][j].setIcon(boardImage[i][j]);
                gameCENTER.add(board[i][j]);
            }
        }

        //diceIcon1 = new ImageIcon("./Images/Dice/Dice1.jpg");       //define the dice
        //diceIcon2 = new ImageIcon("./Images/Dice/Dice3.jpg");
        diceIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                        getResource("Images/Dice/Dice3.jpg")));

        diceIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                                        getResource("Images/Dice/Dice3.jpg")));

        diceLabel1.setIcon(diceIcon1);
        diceLabel2.setIcon(diceIcon2);


        gameNORTH.add(btn_start);
        gameSOUTH.add(btn_resign_player1);
        gameSOUTH.add(btn_player1);
        gameSOUTH.add(diceLabel1);
        gameSOUTH.add(diceLabel2);
        gameSOUTH.add(btn_player2);
        gameSOUTH.add(btn_resign_player2);
        gameNORTH.add(btn_restart);
        gameNORTH.add(btn_exit);

        gamePanel.add(gameCENTER, BorderLayout.CENTER);
        gamePanel.add(gameNORTH, BorderLayout.NORTH);
        gamePanel.add(gameSOUTH, BorderLayout.SOUTH);

        tab.addTab("Game", gamePanel);
    }

    // creates the rule page from txt doc
    void rulePage() {
        helpPanel = new JPanel();
        Icon rules = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                        getResource("Images/rules.jpg")));
        JLabel tutorial = new JLabel();

        tutorial.setIcon(rules);
        tutorial.setFont(tutorial.getFont().deriveFont(17f));

        helpPanel.setLayout(new BorderLayout());
        helpPanel.add(tutorial, BorderLayout.CENTER);
        tab.addTab("Rules", helpPanel);
    }

    //Throws the dice by using Dice object
    int throwDice(JLabel die) {
        int dice_throw = Dice.Roll();
        blockNr = Integer.toString(dice_throw);
        diceIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                getResource("Images/Dice/Dice" + blockNr + ".jpg")));

        die.setIcon(diceIcon1);
        return dice_throw;      //returns nr between 1 and 6
    }

    //backtracks the player if gets > 36
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


    //Randomly chooses which player starts the game
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
            player2_position = 1;
            clickCounter = 0;
        } else if (n==2) {
            btn_player2.setEnabled(true);
            btn_player1.setEnabled(false);

            board[5][0].setIcon(player2Right);
            player1_position = 1;
            player2_position = 1;
            clickCounter = 0;
        }
    }

    //Draw the board
    void drawBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j].setIcon(boardImage[i][j]);
            }
        }
    }

    //Replaces the icon so there are no duplicate icons
    //When player has left his block
    void redraw(int last_position){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(last_position == game[i][j]) {
                    if(player1_lastPosition == player2_position){
                       if(i % 2 == 0 && i != 0) {
                            board[i][j].setIcon(player2Right);
                        }
                        else
                            board[i][j].setIcon(player2Left);
                    }

                    if(player2_lastPosition == player1_position) {
                        if(i % 2 == 0 && i != 0) {
                            board[i][j].setIcon(player1Right);
                        }
                        else
                            board[i][j].setIcon(player1Left);
                    }

                    if(player1_position != game[i][j] && player2_position != game[i][j]) {
                        board[i][j].setIcon(boardImage[i][j]);
                    }
                }
            }
        }

    }

    //Quick move method to save coding space
    void move(Icon player, int position, int lastPosition) throws InterruptedException {
            travel(player,position);
            if(position != lastPosition){
            redraw(lastPosition);
            }
    }

    //Checks if player has gotten equal dice on throw
    void checkEqualDice(int die1, int die2, JButton btn1, JButton btn2){
        if (die1 == die2 ){
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
        else {
            btn1.setEnabled(false);
            btn2.setEnabled(true);
        }
    }

    //Makes sure the second player is placed after first has left
    void placePlayerAtStart(Icon player){
        if (clickCounter == 1) {
            board[5][0].setIcon(player);
        }
        else if (player1_position != 1 && player2_position != 0){
            board[5][0].setIcon(boardImage[5][0]);
        }

    }

    //Checks if lands on ladder and disable the buttons
    void checkIfLadderOrSnake(int player_position){
        if (player_position == 12 || player_position == 17
                || player_position == 16 || player_position == 33)
        {
            btn_player2.setEnabled(false);
            btn_player1.setEnabled(false);
        }
    }

    //Reveal win screen after a warning message
    void player1Win(){
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    blockNr = Integer.toString(game[i][j]);
                    winScreen[i][j] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                            getResource("Images/WinScreen/1Win" + blockNr + ".jpg")));
                    board[i][j].setIcon(winScreen[i][j]);
                }
            }
            btn_player1.setEnabled(false);
            btn_player2.setEnabled(false);
            btn_resign_player1.setEnabled(false);
            btn_resign_player2.setEnabled(false);
            frame.setSize(675, 730);
    }

    void player2Win(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                blockNr = Integer.toString(game[i][j]);
                winScreen[i][j] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                        getResource("Images/WinScreen/2Win" + blockNr + ".jpg")));
                board[i][j].setIcon(winScreen[i][j]);
            }
        }
        btn_player1.setEnabled(false);
        btn_player2.setEnabled(false);
        btn_resign_player1.setEnabled(false);
        btn_resign_player2.setEnabled(false);
        frame.setSize(675, 730);
    }

    //Starts the game :D
    void startGame(){
        gameOver = false;
        btn_start.setEnabled(false);
        btn_restart.setEnabled(true);
        btn_resign_player1.setEnabled(true);
        btn_resign_player2.setEnabled(true);
        startingPlayer();
    }

    //Restarts the game after a warning screen
    void restartGame(){
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to restart?", "RESTART GAME",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            gameOver = false;
            frame.setSize(675, 790);
            drawBoard();
            startingPlayer();
            btn_resign_player1.setEnabled(true);
            btn_resign_player2.setEnabled(true);
        }
    }

    void firstThrow(){
        if(clickCounter == 1){
            while(diceValue1 == diceValue2){
                diceValue1 = throwDice(this.diceLabel1);
                diceValue2 = throwDice(this.diceLabel2);
            }
        }
    }

    //Exits the game after a warning screen
    void exitGame(){
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    //Travel method for moving the player tokens and also
    //ladder and snake traversal.
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
                    else {
                        board[i][j].setIcon(player);
                    }

                }
            }
        }
        //Check for ladder
        if (player1_position == 12){
            board[4][0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1_position = 23;
                    board[2][1].setIcon(player1Left);
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
                    board[2][1].setIcon(player2Left);
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
                    board[2][4].setIcon(player1Left);
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
                    board[2][4].setIcon(player2Left);
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
                    board[2][5].setIcon(player1Left);
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
                    board[2][5].setIcon(player2Left);
                    player2_lastPosition = 33;
                    board[0][3].removeActionListener(this);
                    board[0][3].setIcon(boardImage[0][3]);
                    btn_player1.setEnabled(true);
                }
            });
        }

        //Backtracking if player overshoots the board
        if(player1_position > 36)
        {
            player1_position = backTrack(player1Right, player1_position);
            move(player1Right, player1_position, player1_lastPosition);
        }

        if (player2_position > 36){
            player2_position = backTrack(player2Right, player2_position);
            move(player2Right, player2_position, player2_lastPosition);
        }

        if(player1_position == 36 || player2_position == 36){
            gameOver = true;
        }
    }

    //Listener for all the buttons in the game.
    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource() == btn_player1) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.diceLabel1);
                diceValue2 = throwDice(this.diceLabel2);

                firstThrow();

                totalDiceValue = diceValue1 + diceValue2;

                checkEqualDice(diceValue1, diceValue2, btn_player1, btn_player2);
                player1_position += totalDiceValue;
                player1_lastPosition = player1_position - totalDiceValue;
                move(player1Right, player1_position, player1_lastPosition);
                
                placePlayerAtStart(player2Right);

                checkIfLadderOrSnake(player1_position);
                if(gameOver)
                    player1Win();

            } else if (event.getSource() == btn_player2) {
                clickCounter++;
                btn_restart.setEnabled(true);

                diceValue1 = throwDice(this.diceLabel1);
                diceValue2 = throwDice(this.diceLabel2);
                firstThrow();

                totalDiceValue = diceValue1 + diceValue2;
                checkEqualDice(diceValue1, diceValue2, btn_player2, btn_player1);

                player2_position = player2_position + totalDiceValue;
                player2_lastPosition = player2_position - totalDiceValue;
                move(player2Right, player2_position, player2_lastPosition);

                placePlayerAtStart(player1Right);
                checkIfLadderOrSnake(player2_position);

                if(gameOver){
                    player2Win();
                }

            }
            else if (event.getSource() == btn_start) startGame();
            else if (event.getSource() == btn_resign_player1){
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to resign?", "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    player2Win();
                }
            }
            else if (event.getSource() == btn_resign_player2){
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to resign?", "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    player1Win();
                }
            }
            else if (event.getSource()==btn_restart) restartGame();
            else if(event.getSource()==btn_exit) exitGame();

        } catch (Exception button) {
            System.out.println("Error in BUTTON:" + button);
        }
    }


}