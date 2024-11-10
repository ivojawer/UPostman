package ui;

import domain.Header;
import domain.Request;

import java.util.List;

public class HeadersGrid extends NameValueGrid<Header> {
    PanelManager panelManager;

    public HeadersGrid(String title, PanelManager panelManager) {
        super(title);
        this.panelManager = panelManager;
    }

    @Override
    public Header mapRow(String key, String value) {
        return new Header(key, value);
    }

    @Override
    public void notifyNewRows(List<Header> rows) {
        panelManager.setHeaders(rows);
    }

    @Override
    protected void loadNewRequest(Request newRequest) {
        for(Header header: newRequest.getHeaders()){
            addRow(header.getKey(), header.getValue());
        }
    }
}
