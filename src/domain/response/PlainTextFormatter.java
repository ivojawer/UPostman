package domain.response;

public class PlainTextFormatter implements Formatter {
    @Override
    public String format(String originalString) throws FormattingException {
        return originalString;
    }
}
