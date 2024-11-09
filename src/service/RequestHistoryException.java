package service;

public class RequestHistoryException extends Exception {
    public RequestHistoryException(String message) {
        super(message);
    }
    public RequestHistoryException(Exception e) {
    super(e);
  }
}
