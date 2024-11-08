package ui;

import domain.Request;

import javax.swing.*;

public class FavoriteRequest extends JPanel {
    private Request request;
    public FavoriteRequest(Request request) {
        this.request = request;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(request.getName()));
        this.add(new JLabel(request.getId().toString()));
        this.add(new JButton("Cargar"));
    }
}
