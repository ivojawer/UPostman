package ui;

import domain.Header;
import domain.Parameter;
import domain.Request;
import domain.RequestMethod;
import service.*;
import ui.actions.SendRequest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelManager {
    RequestFavoritingService requestFavoritingService;
    SendRequestService sendRequestService;
    RequestHistoryService requestHistoryService;

    Request currentRequest = new Request();
    List<RequestObserver> observers = new ArrayList<>();
    JFrame frame;
    Response response;
    LoadableRequestList history;
    LoadableRequestList favorites;

    public PanelManager(RequestFavoritingService requestFavoritingService, SendRequestService sendRequestService, RequestHistoryService requestHistoryService) {
        this.requestFavoritingService = requestFavoritingService;
        this.sendRequestService = sendRequestService;
        this.requestHistoryService = requestHistoryService;

    }

    public void sendRequest() {
        domain.response.Response res = this.sendRequestService.send(currentRequest);
        response.setContent(res);
        getHistory();
    }

    public void notifyObservers(){
        for(RequestObserver observer : observers){
            observer.newRequest(currentRequest);
        }
    }

    public void subscribeToCurrentRequest(RequestObserver observer){
        observers.add(observer);
    }

    public void setURI(String newURI) {
        try {
            String[] uri = newURI.split("\\?");
            if(uri.length > 1) {
               currentRequest.setParameters(uri[1]);
            } else {
                currentRequest.clearParameters();
            }
            currentRequest.setPath(uri[0]);
            notifyObservers();
        } catch (Exception e) {
            System.out.println("Invalid URI: " + newURI);
        }
    }

    public void selectMethod(String method){
        currentRequest.setMethod(RequestMethod.valueOf(method));
    }

    public void setHeaders(List<Header> headers){
        this.currentRequest.setHeaders(headers);
    }

    public void setParameters(List<Parameter> parameters){
        this.currentRequest.setParameters(parameters);
        this.notifyObservers();
    }

    public void setCurrentRequest(Request request) {
        this.currentRequest = request;
        response.clear();
        notifyObservers();
    }

    public void setBody(String body){
        this.currentRequest.setBody(body);
    }

    public void initializeFrame(){
        frame = new JFrame();
        frame.setTitle("UPostman");
        frame.getContentPane().setLayout(new BorderLayout(5, 5));


        favorites = new LoadableRequestList(this, "Requests favoritas");
        history = new LoadableRequestList(this, "Historial");

        JPanel request = new JPanel();
        request.setLayout(new BoxLayout(request, BoxLayout.Y_AXIS));


        JScrollPane historyScroll = new JScrollPane(history, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane favoritesScroll = new JScrollPane(favorites, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        JPanel topRowRequest = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRowRequest.setPreferredSize(new Dimension(400, 90));
        request.add(topRowRequest);
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            try {
                requestFavoritingService.addToFavorites(currentRequest);
                getFavorites();
            } catch (FavoriteRequestException ex) {
                // ToDo
                throw new RuntimeException(ex);
            }
        });
        topRowRequest.add(saveButton);


        UriInput uri = new UriInput(this);
        this.subscribeToCurrentRequest(uri);
        uri.setPreferredSize(new Dimension(300,40));
        topRowRequest.add(uri);

        MethodSelector methodSelector = new MethodSelector(this);
        currentRequest.setMethod(RequestMethod.GET);

        topRowRequest.add(methodSelector);
        subscribeToCurrentRequest(methodSelector);

        SendRequestButton sendButton = new SendRequestButton();
        sendButton.addActionListener(new SendRequest(this));
        sendButton.setSize(50, 40);
        topRowRequest.add(sendButton);

        ParametersGrid parameters = new ParametersGrid("Query Parameters", this);
        HeadersGrid headers = new HeadersGrid("Headers", this);
        subscribeToCurrentRequest(parameters);
        subscribeToCurrentRequest(headers);

        request.add(new JLabel("Body"));
        BodyInput body = new BodyInput(this);
        subscribeToCurrentRequest(body);
        request.add(body);
        request.add(parameters);
        request.add(headers);

        response = new Response();
        request.add(response);


        frame.add(favoritesScroll, BorderLayout.LINE_START);
        frame.add(request, BorderLayout.CENTER);
        frame.add(historyScroll, BorderLayout.LINE_END);
        frame.setSize(900,600);
        frame.setVisible(true);
    }

    public void intializeState() {
            getHistory();
            getFavorites();
    }

    private void getFavorites()  {
        try {
            favorites.loadRequests(requestFavoritingService.getFavorites());
        } catch (FavoriteRequestException e) {
            //ToDo
            throw new RuntimeException(e);
        }
    }

    private void getHistory()  {
        try {
            history.loadRequests(requestHistoryService.getHistory());
        } catch (RequestHistoryException e) {
            //ToDo
            throw new RuntimeException(e);
        }
    }
}
