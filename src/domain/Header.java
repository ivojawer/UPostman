package domain;

public class Header {
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
}
