package ui;

import domain.Request;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadableRequest extends JPanel {
    private final Request request;
    public LoadableRequest(Request request, PanelManager panelManager) {
        this.request = request;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(request.getMethod().name() + " " + request.getPath(), JLabel.CENTER));

        JButton load = new JButton("Cargar");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.setCurrentRequest(request.copy());
            }
        });
        this.add(load);

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}
