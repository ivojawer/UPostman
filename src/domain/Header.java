package domain;

public class Header implements KeyValueEntity {
    private final String key;
    private String value;

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
