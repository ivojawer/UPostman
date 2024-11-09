package ui;

import domain.Header;
import domain.Parameter;
import domain.Request;
import domain.RequestMethod;
import service.FavoriteRequestException;
import service.RequestFavoritingService;
import service.SendRequestService;
import ui.actions.SendRequest;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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

    public void setBody(String body){
        this.currentRequest.setBody(body);
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
        JScrollPane favoritesScroll = new JScrollPane(favorites, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        JPanel topRowRequest = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRowRequest.setPreferredSize(new Dimension(400, 90));
        request.add(topRowRequest);
        UriInput uri = new UriInput(this);
        this.subscribeToCurrentRequest(uri);
        uri.setPreferredSize(new Dimension(300,40));
        topRowRequest.add(uri);

        JComboBox<String> methodSelector = new JComboBox<>(new String[]{"GET", "POST", "PUT", "DELETE"});
        currentRequest.setMethod(RequestMethod.GET);
        methodSelector.addActionListener(e-> selectMethod((String) methodSelector.getSelectedItem()));

        topRowRequest.add(methodSelector);

        SendRequestButton sendButton = new SendRequestButton();
        sendButton.addActionListener(new SendRequest(this));
        sendButton.setSize(50, 40);
        topRowRequest.add(sendButton);

        ParametersGrid parameters = new ParametersGrid("Query Parameters", this);
        subscribeToCurrentRequest(parameters);
        NameValueGrid<Header> headers = new HeadersGrid("Headers", this);

        request.add(new JLabel("Body"));
        JTextArea body = new JTextArea();
        body.getDocument().addDocumentListener(
            new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    setBody(body.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setBody(body.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    setBody(body.getText());
                }
            }
        );
        request.add(body);

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


        try {
            requestFavoritingService.getFavorites().forEach(req -> {
                favorites.add(new FavoriteRequest(req));
                history.add(new FavoriteRequest(req));
            });
        } catch (FavoriteRequestException e) {
            // ToDo handle
            throw new RuntimeException(e);
        }


        frame.add(favoritesScroll, BorderLayout.LINE_START);
        frame.add(request, BorderLayout.CENTER);
        frame.add(historyScroll, BorderLayout.LINE_END);
        frame.setSize(900,600);
        frame.setVisible(true);
    }
}
