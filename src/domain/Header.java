package domain;

public class Header implements KeyValueEntity {
    private Integer requestId;
    private final String key;
    private String value;

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
