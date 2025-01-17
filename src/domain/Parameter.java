package domain;

public class Parameter implements KeyValueEntity{
    private String name;
    private String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(String rawParameter) {
        name = "";
        value = "";
        boolean beenThroughEquals = false;

        for (int i = 0; i < rawParameter.length(); i++) {
            char c = rawParameter.charAt(i);
            if (c == '='){
                beenThroughEquals = true;
            } else {
                if(!beenThroughEquals){
                    name += c;
                } else {
                    value += c;
                }
            }
        }


    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return getName();
    }

    public String getValue() {
        return value;
    }

    public String toURIQueryParam() {
        // ToDo sanitize
        return name + "=" + value;
    }
}
