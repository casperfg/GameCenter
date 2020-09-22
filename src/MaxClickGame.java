import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;


public class MaxClickGame {
    static final String recFile = "record.txt";
    public int clicks = 0; // number of clicks
    public int timeLeft = 0; // time left of game
    public final int timeStart = 60; // time limit in game
    public int clickRecord = 0; // record of the game
    public boolean started = false;

    public MaxClickGame() {
        String description = "See how many clicks you can do in a minute";

        JFrame MaxClick = new JFrame("Maximum Clicks"); // new Frame with title
        MaxClick.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea text = new JTextArea(description); // Descibtion
        text.setOpaque(false);
        text.setFont(text.getFont().deriveFont(14f));
        text.setBounds(60, 20, 400, 80);
        text.setEditable(false);

        JTextField record = new JTextField("Record: 0 clicks"); // record button
        record.setEditable(false);
        record.setHorizontalAlignment(JTextField.CENTER);
        record.setBounds(100, 60, 200, 30);
        record.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        JButton start = new JButton("Start"); // start button
        start.setBounds(100, 100, 200, 50);

        JButton click = new JButton("Reset"); // reset button
        click.setBounds(100, 250, 200, 30);

        JTextField counter = new JTextField("0"); // counter for click button
        counter.setHorizontalAlignment(JTextField.CENTER);
        counter.setEditable(false);
        counter.setBounds(180, 160, 50, 30);
        counter.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        JTextArea timeText = new JTextArea("Time Left:         seconds"); // time left text
        timeText.setOpaque(false);
        timeText.setFont(text.getFont().deriveFont(14f));
        timeText.setBounds(120, 210, 400, 80);
        timeText.setEditable(false);

        JTextField timeCounter = new JTextField(String.valueOf(timeStart)); // time counter
        timeCounter.setEditable(false);
        timeCounter.setHorizontalAlignment(JTextField.CENTER);
        timeCounter.setBounds(190, 205, 30, 30);
        timeCounter.setBorder(javax.swing.BorderFactory.createEmptyBorder());


        JButton button_back = new JButton("Back");   // back button
        button_back.setBounds(280, 300, 90, 30);


        MaxClick.add(text);       // add everything
        MaxClick.add(button_back);
        MaxClick.add(record);
        MaxClick.add(start);
        MaxClick.add(click);
        MaxClick.add(counter);
        MaxClick.add(timeText);
        MaxClick.add(timeCounter);


        MaxClick.setSize(400, 400);   // set window size
        MaxClick.setLocationRelativeTo(null);
        MaxClick.setLayout(null);
        MaxClick.setResizable(false);
        MaxClick.setVisible(true);

        try {
            clickRecord = readRecord(); // read current record from file
            record.setText("Record: " + clickRecord + " clicks"); // update text
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }

        ActionListener taskPerformer = e -> { // clock time listener
            if (timeLeft > 0) { // only counts down if timeLeft>0
                timeLeft -= 1;
                timeCounter.setText(String.valueOf(timeLeft));
            } else {
                if (clicks > clickRecord) { // new Record
                    clickRecord = clicks;
                    try { // write Record
                        writeRecord(clicks);
                    } catch (Exception er) {
                        System.out.println(er.getMessage());
                    }
                    record.setText("Record: " + clickRecord + " clicks"); // update text
                }
            }
        };
        new Timer(1000, taskPerformer).start(); // start clock time listener

        start.addActionListener(e -> { // start game
            if(!started) {
                timeLeft = timeStart; // set timeLeft to 60
                clicks = 0; // reset number of clicks
                counter.setText("0");
                start.setText("Click");
                started = true;

            } else {
                if (timeLeft > 0) { // only register if there is time left
                    clicks += 1;
                    counter.setText(String.valueOf(clicks)); // update txt
                }
            }
        });

        click.addActionListener(e -> {
            started = false;
            counter.setText("0");
            clicks = 0;
            String tl = Integer.toString(timeStart);
            start.setText("Start");
            timeCounter.setText(tl);
        });
        
        button_back.addActionListener(e -> MaxClick.dispose()); // close this window
    }

    public static void writeRecord(int rec) throws IOException {
        FileWriter writer = new FileWriter(recFile);
        writer.write(String.valueOf(rec));
        writer.close();
    }

    public static int readRecord() throws FileNotFoundException {
        File fil = new File(recFile);
        Scanner sc = new Scanner(fil);
        return sc.nextInt();
    }

}
