package ui;

import domain.Request;
import domain.RequestMethod;
import service.RequestFavoritingService;
import service.SendRequestService;
import ui.actions.SendRequest;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PanelManager {
    RequestFavoritingService requestFavoritingService;
    SendRequestService sendRequestService;

    Request currentRequest = new Request();
    List<RequestObserver> observers = new ArrayList<>();
    JFrame frame;
    Response response;

    public PanelManager(RequestFavoritingService requestFavoritingService, SendRequestService sendRequestService) {
        this.requestFavoritingService = requestFavoritingService;
        this.sendRequestService = sendRequestService;

    }

    public void sendRequest() {
        String res = this.sendRequestService.send(currentRequest);
        response.setContent(res);
    }

    public void notifyObservers(){
        for(RequestObserver observer : observers){
            observer.notify(currentRequest);
        }
    }

    public void subscribeToCurrentRequest(RequestObserver observer){
        observers.add(observer);
    }

    public void setURI(String newURI) {
        try {
            URI uri = new URI(newURI);
            if(uri.getQuery() != null) {
               currentRequest.setParameters(uri.getQuery());
            }
            currentRequest.setPath(newURI.split("\\?")[0]);
            notifyObservers();
        } catch (Exception e) {
            System.out.println("Invalid URI: " + newURI);
        }
    }

    public void selectMethod(String method){
        currentRequest.setMethod(RequestMethod.valueOf(method));
    }

    public void initializeFrame(){


        frame = new JFrame();
        frame.setTitle("UPostman");
        frame.getContentPane().setLayout(new BorderLayout(5, 5));


        JPanel favorites = new JPanel();
        favorites.setLayout(new BoxLayout(favorites, BoxLayout.Y_AXIS));
        favorites.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JPanel request = new JPanel();
        request.setLayout(new BoxLayout(request, BoxLayout.Y_AXIS));

        JPanel history = new JPanel();
        history.setLayout(new BoxLayout(history, BoxLayout.Y_AXIS));
        history.setAutoscrolls(true);
        history.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JScrollPane historyScroll = new JScrollPane(history, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane favoritesScroll =new JScrollPane(favorites, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        JPanel topRowRequest = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRowRequest.setPreferredSize(new Dimension(400, 90));
        request.add(topRowRequest);
        UriInput uri = new UriInput(this);
        this.subscribeToCurrentRequest(uri);
        uri.setPreferredSize(new Dimension(300,40));
        topRowRequest.add(uri);

        JComboBox<String> methodSelector = new JComboBox<>(new String[]{"GET", "POST", "PUT", "DELETE"});

        methodSelector.addActionListener(e-> selectMethod((String) methodSelector.getSelectedItem()));
        topRowRequest.add(methodSelector);

        SendRequestButton sendButton = new SendRequestButton();
        sendButton.addActionListener(new SendRequest(this));
        sendButton.setSize(50, 40);
        topRowRequest.add(sendButton);

        ParametersGrid parameters = new ParametersGrid("Query Parameters");
        subscribeToCurrentRequest(parameters);
        NameValueGrid headers = new NameValueGrid("Headers");

        request.add(parameters);
        request.add(headers);

        response = new Response();
        request.add(response);

        JLabel favoritesLabel =new JLabel("Requests favoritas");
        favoritesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        favorites.add(favoritesLabel);

        JLabel historyLabel = new JLabel("Historial");
        historyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        history.add(historyLabel);

        for(int i = 0; i<5; i++){
            Request req =new Request(i, "Request #"+ i+1);
            favorites.add(new FavoriteRequest(req));
            history.add(new FavoriteRequest(req));
        }


        frame.add(favoritesScroll, BorderLayout.LINE_START);
        frame.add(request, BorderLayout.CENTER);
        frame.add(historyScroll, BorderLayout.LINE_END);
        frame.setSize(900,600);
        frame.setVisible(true);
    }
}
