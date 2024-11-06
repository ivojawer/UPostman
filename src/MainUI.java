import ui.Frame;

import java.net.URISyntaxException;

public class MainUI {
    public static void main(String[] args) throws URISyntaxException {
        Frame frame = new Frame();
        frame.setVisible(true);


//        HttpRequestProvider http = new JavaHttpRequestProvider();
//
//        HttpRequest req = HttpRequest.newBuilder(new URI("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02")).build();
//
//        HttpResponse<String> response =  http.request(req);
//
//
//        System.out.println(response.body());
    }
}
