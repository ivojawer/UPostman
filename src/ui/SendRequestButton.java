package ui;

import ui.actions.SendRequest;

import javax.swing.*;

public class SendRequestButton extends JButton {
    public SendRequestButton() {
        super("Send");
        this.addActionListener(new SendRequest());
    }


}
