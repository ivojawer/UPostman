package ui.actions;

import ui.PanelManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendRequest implements ActionListener {
    PanelManager panelManager;
    public SendRequest(PanelManager panelManager){
        this.panelManager = panelManager;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        panelManager.sendRequest();
    }
}
