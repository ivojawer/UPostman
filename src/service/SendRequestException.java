package service;

public class SendRequestException extends RuntimeException {
    public SendRequestException(Exception e) {
        super(e);
    }
}
