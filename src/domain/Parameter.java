package domain;

public class Parameter {
    private Integer requestId;
    private String name;
    private String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
