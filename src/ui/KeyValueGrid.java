package ui;

import domain.KeyValueEntity;
import domain.Request;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class KeyValueGrid<T extends KeyValueEntity> extends JPanel implements RequestObserver {
    protected JPanel grid;
    protected Boolean listening = true;
    protected Boolean takeNewRequests = true;

    KeyValueGrid(String title){
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
    }

    public void removeRow(JPanel row){
        grid.remove(row);
        updateUI();
    }

    public abstract T mapRow(String key, String value);
    public abstract void notifyNewRows(List<T> rows);

    public void reactToChange() {
        if(listening){
            List<T> rows = new ArrayList<>();
            for(int i = 0; i < grid.getComponentCount(); i++){
                JPanel row = (JPanel) grid.getComponent(i);
                JTextField name = (JTextField)row.getComponent(0);
                JTextField value = (JTextField)row.getComponent(1);
                rows.add(mapRow(name.getText(), value.getText()));
            }

            notifyNewRows(rows);
        }
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
            reactToChange();
        });

        DocumentListener listener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                reactToChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reactToChange();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reactToChange();
            }
        };
        nameField.getDocument().addDocumentListener(listener);
        valueField.getDocument().addDocumentListener(listener);


        row.add(nameField);
        row.add(valueField);
        row.add(removeRow);
        grid.add(row);
        reactToChange();
        updateUI();
    }

    @Override
    public void newRequest(Request newRequest) {
        if(takeNewRequests){
            listening = false;
            List<T> rows = this.newRequestRows(newRequest);

            if(grid.getComponentCount() > rows.size()){
                grid.removeAll();
            }
            for(int i = 0; i<rows.size(); i++){
                if(grid.getComponentCount() > i){
                    JPanel row = (JPanel) grid.getComponent(i);
                    JTextField name = (JTextField)row.getComponent(0);
                    JTextField value = (JTextField)row.getComponent(1);
                    name.setText(rows.get(i).getKey());
                    value.setText(rows.get(i).getValue());
                } else {
                    addRow(rows.get(i).getKey(), rows.get(i).getValue());
                }
            }
            updateUI();
    //        grid.removeAll();
    //        for(T row : this.newRequestRows(newRequest)){
    //            addRow(row.getKey(), row.getValue());
    //        }
            listening = true;
        }
    }

    protected abstract List<T> newRequestRows(Request newRequest);
}
