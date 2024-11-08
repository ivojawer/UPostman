package ui;

import domain.Parameter;
import domain.Request;

public class ParametersGrid extends NameValueGrid implements RequestObserver {
    Boolean listening = false;

    ParametersGrid(String title) {
        super(title);
    }

    private void reactToChange(){

    }


    @Override
    public void notify(Request newRequest) {
        grid.removeAll();
        for(Parameter param : newRequest.getParameters()){
            addRow(param.getName(), param.getValue());
        }
    }


}
