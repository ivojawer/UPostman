package ui;

import javax.swing.*;
import java.awt.*;

public class NameValueGrid extends JPanel {

    NameValueGrid(String title){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addRow = new JButton("+");
        addRow.addActionListener(e -> addRow());
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));


        header.add(new JLabel(title));
        header.add(addRow);
        add(header);

        addRow();
    }

    public void addRow(){
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField nameField = new JTextField("", 20);
        JTextField valueField = new JTextField("", 20);
        JButton removeRow = new JButton("X");
        valueField.setMaximumSize(new Dimension(10000, 30));
        nameField.setMaximumSize(new Dimension(10000, 30));

        removeRow.setPreferredSize(new Dimension(20, 20));
        removeRow.setMaximumSize(new Dimension(20, 20));
        removeRow.addActionListener(e -> {
            remove(row);
            updateUI();
        });


        row.add(nameField);
        row.add(valueField);
        row.add(removeRow);
        add(row);
        updateUI();
    }
}
