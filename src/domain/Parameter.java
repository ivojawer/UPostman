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
        name = "";
        value = "";
        boolean beenThrowEquals = false;

        for (int i = 0; i < rawParameter.length(); i++) {
            char c = rawParameter.charAt(i);
            if (c == '='){
                beenThrowEquals = true;
            } else {
                if(!beenThrowEquals){
                    name += c;
                } else {
                    value += c;
                }
            }
        }


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
