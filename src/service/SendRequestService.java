package service;

import domain.Header;
import domain.Request;
import domain.response.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SendRequestService {
    private final RequestHistoryService requestHistoryService;

    public SendRequestService(RequestHistoryService requestHistoryService) {
        this.requestHistoryService = requestHistoryService;
    }


    public Response send(Request req) throws SendRequestException {
       try {
           HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(req.getURI()));

           builder.method(req.getMethod().name(), HttpRequest.BodyPublishers.ofString(req.getBody()));

           for(Header header : req.getHeaders()){
               builder.header(header.getKey(), header.getValue());
           }

           HttpRequest httpReq = builder.build();

           HttpResponse<InputStream> response = HttpClient.newHttpClient().send(
               httpReq,
               HttpResponse.BodyHandlers.ofInputStream()
           );

           req.registerSendTime();
           requestHistoryService.registerHistory(req);
           return buildResponse(response);
       } catch (URISyntaxException | IllegalArgumentException e) {
           throw new SendRequestException("La URL ingresada no es valida, revisa el formato");
       } catch (Exception e){
           throw new SendRequestException("Ocurrio un error intentando enviar la request:" + e.getMessage());
       }

    }

   private Response buildResponse(HttpResponse<InputStream> httpRes) throws IOException {
        String contentType = httpRes.headers().firstValue("Content-Type").orElse(null);
        if(contentType == null) return new TextResponse(httpRes.body(), new PlainTextFormatter());
        switch (contentType.split(";")[0]){
            case "text/html":
            case "text/plain":
            case "text/xml": //ToDo make xml formatter
                return new TextResponse(httpRes.body(), new PlainTextFormatter());
            case "image/jpeg":
            case "image/png":
                return new ImageResponse(httpRes.body());
            case "application/json":
                return new TextResponse(httpRes.body(), new JSONFormatter());
            default:
                return new TextResponse(httpRes.body(), new PlainTextFormatter());
        }

   }
}
