package ui;

import domain.Request;
import service.RequestFavoritingService;
import service.SendRequestService;
import ui.actions.SendRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaneManager {

    RequestFavoritingService requestFavoritingService;
    SendRequestService sendRequestService;
    Response response;

    public PaneManager(RequestFavoritingService requestFavoritingService, SendRequestService sendRequestService) {
        this.requestFavoritingService = requestFavoritingService;
        this.sendRequestService = sendRequestService;
    }

    public void initializeFrame(){


        JFrame frame = new JFrame();
        frame.setName("UPostman");
        frame.getContentPane().setLayout(new BorderLayout(10, 10));


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
        JTextField uri = new UriInput();//creating instance of JButton
        uri.setPreferredSize(new Dimension(300,40));
        topRowRequest.add(uri);

        JComboBox<String> methodSelector = new JComboBox<>(new String[]{"GET", "POST", "PUT", "DELETE"});
        topRowRequest.add(methodSelector);

        SendRequestButton sendButton = new SendRequestButton();
        sendButton.addActionListener(new SendRequest(this));
        sendButton.setSize(50, 40);
        topRowRequest.add(sendButton);

        NameValueGrid parameters = new NameValueGrid("Query Parameters");
        NameValueGrid headers = new NameValueGrid("Headers");

        request.add(parameters);
        request.add(headers);

        response = new Response();
        request.add(response);

        JLabel favoritesLabel =new JLabel("Requests favoritas");
        favoritesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        favorites.add(favoritesLabel);

        JLabel historyLabel =new JLabel("Historial");
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

    public void sendRequest(){
        String res = this.sendRequestService.send(new Request(1, "hola"));
        response.setContent(res);

    }
}
