package ui;

import domain.response.ImageResponse;
import domain.response.TextResponse;

import javax.swing.*;
import java.awt.*;

public class Response extends JPanel {
    JComponent content;
    public Response(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.add(new JLabel("Response"));
        add(header);

        addContent(new TextResponse(""));
    }

    public void setContent(domain.response.Response response){
        remove(1);
        this.addContent(response);
    }

    public void addContent(domain.response.Response response){
        if(response.isText()){
            content = new ResponseText(((TextResponse)response).getText());
        } else {
            content = new JLabel(new ImageIcon(((ImageResponse)response).getImage()));
        }
        add(new JScrollPane(content));
        updateUI();

    }

    public void clear(){
        setContent(new TextResponse(""));
    }
}
