package ui;

import domain.Parameter;
import domain.Request;

import java.util.List;
import java.util.stream.Collectors;

public class ParametersGrid extends NameValueGrid<Parameter> {
    PanelManager panelManager;

    ParametersGrid(String title, PanelManager panelManager) {
        super(title);
        this.panelManager = panelManager;
    }

    @Override
    public Parameter mapRow(String key, String value) {
        return new Parameter(key, value);
    }

    @Override
    public void notifyNewRows(List<Parameter> rows) {
        listening = false;
        panelManager.setParameters(rows);
        listening = true;
    }

    @Override
    protected void loadNewRequest(Request newRequest) {
        for(Parameter param : newRequest.getParameters()){
            addRow(param.getName(), param.getValue());
        }
    }

}
