package ui;

import domain.Request;
import domain.RequestMethod;

import javax.swing.*;
import java.util.Arrays;

public class MethodSelector extends JComboBox<String> implements RequestObserver{
    public MethodSelector(PanelManager panelManager) {
        super(Arrays.stream(RequestMethod.values()).map(Enum::name).toArray(String[]::new));

        addActionListener(e-> panelManager.selectMethod((String) this.getSelectedItem()));
    }

    @Override
    public void newRequest(Request newRequest) {
        this.setSelectedItem(newRequest.getMethod().name());
    }
}
