package domain.http;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JavaHttpRequestProvider implements HttpRequestProvider {

    private HttpClient httpClient;

    public JavaHttpRequestProvider(){
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public HttpResponse<String> request(HttpRequest req) {
        try {
            HttpResponse<String> response = httpClient.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );

            return response;

        } catch (Exception e){
            //ToDo
            throw new RuntimeException(e);
        }
    }
}
