package ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;

public class Response extends JPanel {
    JTextArea content;
    public Response(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.add(new JLabel("Response"));
        header.add(new JComboBox<>(new String[]{"JSON", "XML"}));

        content = new JTextArea();
        content.setLineWrap(true);
        content.setText("");
        content.setEditable(false);
        content.setPreferredSize(new Dimension(300, 900));

        add(header);
        add(new JScrollPane(content));
    }

    public void setContent(String newContent){
        // ToDo strategy con combobox
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        content.setText(gson.toJson(JsonParser.parseString(newContent)));
    }
}
