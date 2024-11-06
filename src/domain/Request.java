package domain;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private Integer id;
    private String name;
    private final Map<String, Header> headers;
    private final Map<String, Parameter> parameters;
    private RequestMethod method;
    private Boolean favorite;


    public Request(Integer id, String name) {
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        this.id = id;
        this.name = name;
    }


    //ToDo no repetir logica header/parameter
    public void setHeader(String key, String value) {
        Header header = this.headers.get(key);
        if(header == null) {
            header = new Header(key, value);
            this.headers.put(key, header);
        } else {
            header.setValue(value);
        }
    }

    public void setParameter(String name, String value) {
        Parameter parameter = this.parameters.get(name);
        if(parameter == null) {
            parameter = new Parameter(name, value);
            this.parameters.put(name, parameter);
        } else {
            parameter.setValue(value);
        }
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
}
