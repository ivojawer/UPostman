package ui;

import domain.Request;

public interface RequestObserver {

    void notify(Request newRequest);

}
