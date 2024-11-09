package ui;

import domain.Request;

public interface RequestObserver {

    void newRequest(Request newRequest);

}
