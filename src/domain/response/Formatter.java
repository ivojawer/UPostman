package domain.response;

public interface Formatter {
    String format(String originalString) throws FormattingException;
}
