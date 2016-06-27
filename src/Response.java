/**
 * Created by ksenia on 6/24/16.
 */
import java.util.*;

public class Response {
    private String startLine;
    private final String CONTENT_TYPE = "Content-Type: text/html";
    private final Date DATE = new Date();
    private String body;

    public Response(String startLine, String body) {
        this.startLine = startLine;
        this.body = body;
    }

    public String getStartLine() {
        return startLine;
    }

    public String getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }

    public Date getDATE() {
        return DATE;
    }

    public String getBody() {
        return body;
    }
}
