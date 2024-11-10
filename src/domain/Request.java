package domain;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Request {
    private Integer id;
    private final List<Header> headers;
    private final List<Parameter> parameters;
    private RequestMethod method;
    private Boolean isFavorite;
    private String body;
    private String path;
    private LocalDateTime lastSent;

    public Request() {
        headers = new ArrayList<>();
        parameters = new ArrayList<>();
        path = "";
        isFavorite = false;
        body = "";
    }

    public Request(Integer id) {
        this();
        this.id = id;
    }

    public String getURI() {
        Optional<String> paramsOpt =
                parameters
                        .stream()
                        .map(Parameter::toURIQueryParam)
                        .reduce((String p1, String p2) -> p1.concat("&").concat(p2));
        return paramsOpt.map(params -> path + "?" + params).orElseGet(() -> path);
    }

    public Integer getId() {
        return id;
    }

    public boolean isPersisted() {
        return id != null;
    }

    public void setAsFavorite() {
        isFavorite = true;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters.clear();
        this.parameters.addAll(parameters);
    }

    /**
     * @param rawQueryParams the raw string of the query params sections (e.g. "key1=value1&key2=value2")
     */
    public void setParameters(String rawQueryParams) throws URIParseException {
        List<Parameter> newParameters = new ArrayList<>();
        try{
            for(String param : rawQueryParams.split("&")){
                newParameters.add(new Parameter(param));
            }
        } catch (Exception e){
            throw new URIParseException(e);
        }

        setParameters(newParameters);
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void setHeaders(List<Header> headers) {
        this.headers.clear();
        this.headers.addAll(headers);
    }

    public void clearParameters() {
        parameters.clear();
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public String getPath(){
        return path;
    }

    public void registerSendTime(){
        lastSent = LocalDateTime.now();
    }

    public LocalDateTime getLastSent() {
        return lastSent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastSent(LocalDateTime lastSent) {
        this.lastSent = lastSent;
    }

    /**
     * Makes a copy of a request without it's meta-state (id, favorite, lastSent)
     */
    public Request copy(){
        Request request = new Request();
        request.setMethod(method);
        request.setPath(path);
        request.setHeaders(headers);
        request.setParameters(parameters);
        request.setBody(body);
        return request;
    }
}
