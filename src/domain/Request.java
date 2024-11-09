package domain;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Request {
    private Integer id;
    private String name;
    private final List<Header> headers;
    private final List<Parameter> parameters;
    private RequestMethod method;
    private Boolean isFavorite;
    private String body;
    private String path;


    public Request() {
        headers = new ArrayList<>();
        parameters = new ArrayList<>();
        path = "";
        isFavorite = false;
    }

    public Request(Integer id, String name) {
        this();
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
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

    public List<Header> getHeaders() {
        return headers;
    }
}
