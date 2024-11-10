package ui;

import domain.Request;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class BodyInput extends JTextArea implements RequestObserver {
    public BodyInput(PanelManager panelManager){
        setPreferredSize(new Dimension(400, 300));
        getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        panelManager.setBody(getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        panelManager.setBody(getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        panelManager.setBody(getText());
                    }
                }
        );
    }

    @Override
    public void newRequest(Request newRequest) {
        setText(newRequest.getBody());
    }
}
