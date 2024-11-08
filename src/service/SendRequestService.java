package service;

import domain.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SendRequestService {
   public String send(Request req){
       HttpRequest httpReq = null;
       try {
           httpReq = HttpRequest.newBuilder(new URI(req.getURI())).build();
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
