package domain.http;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface HttpRequestProvider {
    HttpResponse<String> request(HttpRequest req);
}
