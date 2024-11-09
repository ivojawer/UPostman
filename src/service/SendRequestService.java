package service;

import domain.Header;
import domain.Request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SendRequestService {
   public String send(Request req){
       try {
           HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(req.getURI()));

           builder
               .method(req.getMethod().name(), HttpRequest.BodyPublishers.ofString(req.getBody()))
               .build();

           for(Header header : req.getHeaders()){
               builder.header(header.getKey(), header.getValue());
           }

           HttpRequest httpReq = builder.build();

           HttpResponse<String> response = HttpClient.newHttpClient().send(
               httpReq,
               HttpResponse.BodyHandlers.ofString()
           );

           return response.body();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
}
