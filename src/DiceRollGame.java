import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DiceRollGame {
    final Player player1 = new Player();      //Creates new player1 object
    final Player player2  = new Player();      //Creates new player2 object

    public DiceRollGame() {             //Constructor for dice Game
        int width = 95;                 //width of certain elements used
        int height = 30;                //height of certain elements used

        String s = "Each player takes three alternate turns to roll a dice\n" +
                "The one who scores maximums after three turns\nwins the game! Player 1 starts";

        JFrame diceGame = new JFrame("Dice Game");                 //New JFrame object
        diceGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //Closes program on exit

        JTextArea description = new JTextArea(s);                       //creates new description JTextArea
        description.setOpaque(false);                                   //sets background transparent
        description.setFont(description.getFont().deriveFont(14f));     //sets font size
        description.setBounds(20, 20, 400, 80);       //places text area in grid
        description.setEditable(false);                                 //disable editing

        JButton button_Player1 = new JButton("Player 1");          //Button for player 1
        JButton button_Player2 = new JButton("Player 2");          //Button for player 2
        JButton button_back = new JButton("Back");                 //Back button
        JButton button_reset = new JButton("Reset");

        JTextField diceValue = new JTextField("Dice Value: ");           //JTextField for keeping dice value
        diceValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        JTextField score1 = new JTextField("Score: 0");
        score1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        JTextField score2 = new JTextField("Score: 0");          //JTextField for keeping player score
        score2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        JTextField counter1 = new JTextField("Turn: 0");
        counter1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        JTextField counter2 = new JTextField("Turn: 0");
        counter2.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        diceValue.setEditable(false);
        score1.setEditable(false);                                     //disable editing
        score2.setEditable(false);
        counter1.setEditable(false);
        counter2.setEditable(false);

        button_Player1.setBounds(80, 120, width, height);
        button_Player2.setBounds(220, 120, width, height);
        button_back.setBounds(280, 300, width, height);
        button_reset.setBounds(10, 300, width, height);

        diceValue.setBounds(150, 170, width, height);
        diceValue.setHorizontalAlignment(JTextField.CENTER);

        score1.setBounds(80, 230, width, height);
        score1.setHorizontalAlignment(JTextField.CENTER);

        score2.setBounds(220, 230, width, height);
        score2.setHorizontalAlignment(JTextField.CENTER);

        counter1.setBounds(10, 120, 50, height);
        counter1.setHorizontalAlignment(JTextField.CENTER);

        counter2.setBounds(330, 120, 50, height);
        counter2.setHorizontalAlignment(JTextField.CENTER);

        diceGame.add(description);      //add description to frame
        diceGame.add(button_Player1);   //add button for player  to frame
        diceGame.add(button_Player2);
        diceGame.add(button_back);
        diceGame.add(button_reset);     //Resets the game
        diceGame.add(diceValue);        //add textFields to frame
        diceGame.add(score1);
        diceGame.add(score2);
        diceGame.add(counter1);
        diceGame.add(counter2);

        diceGame.setSize(400, 400);
        diceGame.setLocationRelativeTo(null);       //makes sure the windows opens centered
        diceGame.setLayout(null);
        diceGame.setResizable(false);               //disable window resizing
        diceGame.setVisible(true);                  //sets frame visible

        button_Player1.addActionListener(e -> {     //listener for player1-button
            if(player1.playCount <= player2.playCount) {
                play(player1, diceValue, score1, counter1);           //plays the game, adds score, etc.
            }
            else{
                diceValue.setText("Player 2's turn");
            }
            checkWinner(diceValue, player1, player2);   //Check if winner exists and print winner
        });

        button_Player2.addActionListener(e -> {         //listener for player2-button
            if(player2.playCount < player1.playCount) {
                play(player2, diceValue, score2, counter2);
            }
            else{
                diceValue.setText("Player 1's turn");
            }
            checkWinner(diceValue, player1, player2);
        });

        button_reset.addActionListener(e -> {
            resetPlayer(player1, score1, counter1);
            resetPlayer(player2, score2, counter2);
            diceValue.setText("Dice Value: ");
        });

        button_back.addActionListener(e -> diceGame.dispose()); //closes window
    }

    public void play(Player player, JTextField dv, JTextField sc, JTextField ctr) {
        if (player.playCount < 3) {
            int value = Dice.Roll();
            player.playCount++;

            dv.setText("Dice Value: " + value);
            updateScore(value, player);
            ctr.setText("Turn: " + player.playCount);
        }
        sc.setText("Score: "+ player.score);
    }

    public void updateScore(int lastDiceValue, Player player) {
        player.score += lastDiceValue;
    }

    public void resetPlayer(Player player, JTextField scoreText, JTextField countText){
        player.score = 0;
        player.playCount = 0;
        scoreText.setText("Score: 0");
        countText.setText("Turn: 0");
    }

    public void checkWinner(JTextField dv, Player p1, Player p2) {
        if (p1.playCount==3 && p2.playCount==3) {
            if (p1.score > p2.score) {
                dv.setText("PLAYER 1 WINS");
            } else if (p2.score > p1.score) {
                dv.setText("PLAYER 2 WINS");
            } else {
                dv.setText("DRAW");
            }
        }
    }
}