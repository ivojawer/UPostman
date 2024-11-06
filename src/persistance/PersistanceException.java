package persistance;

public class PersistanceException extends Exception {
    public PersistanceException(Exception e) {
        super(e);
    }

    public PersistanceException(String msg) {
        super(msg);
    }
}
