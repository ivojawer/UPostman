package domain;

public class URIParseException extends Exception {
    public URIParseException(String message) {
        super (message);
    }
    public URIParseException(Exception e) {
        super(e);
    }
}
