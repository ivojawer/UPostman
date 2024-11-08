package domain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private Integer id;
    private String name;
    private final List<Header> headers;
    private final List<Parameter> parameters;
    private RequestMethod method;
    private Boolean favorite;
    private String uri;


    public Request(Integer id, String name) {
        this.headers = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public String getURI() {
        return  "https://cat-fact.herokuapp.com/facts";
    }

    public Integer getId() {
        return id;
    }

    public boolean isPersisted() {
        return id != null;
    }

    public void setAsFavorite() {
        favorite = true;
    }

    public String getName() {
        return name;
    }
}
