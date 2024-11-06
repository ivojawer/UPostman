package ui;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super("Postgres");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        JTextField uri = new UriInput();//creating instance of JButton
        uri.setBounds(40,40,560,40);
        this.add(uri);

        SendRequestButton sendButton = new SendRequestButton();
        sendButton.setBounds(630, 40, 50, 40);
        this.add(sendButton);

        this.setSize(1080,720);
        this.setLayout(null);
    }
}
