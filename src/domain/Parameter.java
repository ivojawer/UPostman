package domain;

public class Parameter {
    private Integer requestId;
    private String name;
    private String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(String rawParameter) throws URIParseException {
        String[] splitParam = rawParameter.split("=");
        if(splitParam.length != 2){
            throw new URIParseException("Argument was not a valid parameter");
        }
        name = splitParam[0];
        value = splitParam[1];

    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String toURIQueryParam() {
        // ToDo sanitize
        return name + "=" + value;
    }
}
