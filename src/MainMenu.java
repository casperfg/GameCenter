import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MainMenu {
    public MainMenu() {
        int size = 400;
        int buttonHeight = 30;
        int buttonWidth = 160;

        JFrame menu = new JFrame("Game Center");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea title = new JTextArea("Game Center");
        title.setOpaque(false);
        title.setFont(title.getFont().deriveFont(20f));
        title.setBounds(140, 20, 400, 80);
        title.setEditable(false);

        JButton rollDice = new JButton("Roll a Dice");
        JButton maxClicks = new JButton("Max Clicks");
        JButton SnL = new JButton("Snakes & Ladders");
        JButton exit = new JButton("Exit");

        rollDice.setBounds(125, 100, buttonWidth, buttonHeight);
        maxClicks.setBounds(125, 150, buttonWidth, buttonHeight);
        SnL.setBounds(125, 200, buttonWidth, buttonHeight);
        exit.setBounds(125, 250, buttonWidth, buttonHeight);

        menu.add(title);
        menu.add(maxClicks);
        menu.add(rollDice);
        menu.add(SnL);
        menu.add(exit);
        menu.setSize(size, size);
        menu.setLocationRelativeTo(null);
        menu.setLayout(null);
        menu.setVisible(true);
        menu.setResizable(false);


        rollDice.addActionListener(e -> new DiceRollGame());
        maxClicks.addActionListener(e -> new MaxClickGame());
        SnL.addActionListener(e -> new SnakesAndLadders());
        exit.addActionListener(e -> menu.dispose());
        exit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "WARNING",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                menu.dispose();
            }
        });
    }
}
