package ui;

import javax.swing.*;
import java.awt.*;

public class NameValueGrid extends JPanel {
    protected JPanel grid;
    protected final Integer columnsPerGrid = 3;

    NameValueGrid(String title){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addRow = new JButton("+");
        addRow.addActionListener(e -> addRow("", ""));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));


        header.add(new JLabel(title));
        header.add(addRow);
        grid = new JPanel();
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));


        add(header);
        add(grid);
        addRow("", "");


    }

    public void removeRow(JPanel row){
        grid.remove(row);
        updateUI();
    }

    public void addRow(String key, String value){
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField nameField = new JTextField(key, 20);
        JTextField valueField = new JTextField(value, 20);
        JButton removeRow = new JButton("X");
        valueField.setMaximumSize(new Dimension(10000, 30));
        nameField.setMaximumSize(new Dimension(10000, 30));

        removeRow.setPreferredSize(new Dimension(20, 20));
        removeRow.setMaximumSize(new Dimension(20, 20));
        removeRow.addActionListener(e -> {
            removeRow(row);
            updateUI();
        });


        row.add(nameField);
        row.add(valueField);
        row.add(removeRow);
        grid.add(row);
        updateUI();
    }
}
