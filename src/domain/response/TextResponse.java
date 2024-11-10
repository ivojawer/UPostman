package domain.response;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TextResponse implements Response {
    String body;
    Formatter formatter;
    public TextResponse(InputStream body, Formatter formatter) throws IOException {
        Scanner s = new Scanner(body).useDelimiter("\\A");
        this.body = s.hasNext() ? s.next() : "";

        this.formatter = formatter;
    }

    public TextResponse(String body){
        this.body = body;
        this.formatter = new PlainTextFormatter();
    }

    @Override
    public Boolean isImage() {
        return false;
    }

    @Override
    public Boolean isText() {
        return true;
    }


    public String getText() {
        return formatter.format(body);
    }


}
