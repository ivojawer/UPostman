package ui.actions;

import ui.PaneManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class SendRequest implements ActionListener {
    PaneManager paneManager;
    public SendRequest(PaneManager paneManager){
        this.paneManager = paneManager;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        paneManager.sendRequest();
    }
}
