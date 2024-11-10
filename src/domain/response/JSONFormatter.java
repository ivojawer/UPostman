package domain.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JSONFormatter implements Formatter {

    @Override
    public String format(String originalString) throws FormattingException {
        String formatted;
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            formatted =  gson.toJson(JsonParser.parseString(originalString));

        }catch (JsonSyntaxException e){
            throw new FormattingException("Could not parse string as JSON");
        }
        if(formatted == null) throw new FormattingException("Could not format JSON");
        return formatted;
    }
}
