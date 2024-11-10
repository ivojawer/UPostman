package ui;

import javax.swing.*;
import java.awt.*;

public class ResponseText extends JTextArea {

    public ResponseText(String text) {
        this.setLineWrap(true);
        this.setText(text);
        this.setEditable(false);
        this.setPreferredSize(new Dimension(300, 900));
    }

}
