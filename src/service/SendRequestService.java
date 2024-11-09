package service;

import domain.Header;
import domain.Request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SendRequestService {
    private RequestHistoryService requestHistoryService;

    public SendRequestService(RequestHistoryService requestHistoryService) {
        this.requestHistoryService = requestHistoryService;
    }


    public String send(Request req) {
       try {
           HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(req.getURI()));

           builder.method(req.getMethod().name(), HttpRequest.BodyPublishers.ofString(req.getBody()));

           for(Header header : req.getHeaders()){
               builder.header(header.getKey(), header.getValue());
           }

           HttpRequest httpReq = builder.build();

           HttpResponse<String> response = HttpClient.newHttpClient().send(
               httpReq,
               HttpResponse.BodyHandlers.ofString()
           );

           req.registerSendTime();
           requestHistoryService.registerHistory(req);

           return response.body();
       } catch (Exception e) {
           //ToDo
           throw new RuntimeException(e);
       }
    }
}
