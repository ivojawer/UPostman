package ui;

import domain.Request;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadableRequestList extends JPanel {
    JPanel list;
    PanelManager panelManager;
    public LoadableRequestList(PanelManager panelManager, String title){
        this.panelManager = panelManager;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        Map<TextAttribute, Object> attributes = new HashMap<>();

        attributes.put(TextAttribute.FAMILY, Font.DIALOG);
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.SIZE, 16);

        titleLabel.setFont(Font.getFont(attributes));
        add(titleLabel);

        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        this.add(list);
    }


    public void loadRequests(List<Request> requests){
        list.removeAll();
        requests.forEach(r -> list.add(new LoadableRequest(r, panelManager)));
        list.updateUI();
    }


}
