/**
 * Created by ksenia on 6/24/16.
 */
import java.util.*;

public class Response {
    String startLine;
    final String CONTENT_TYPE = "Content-Type: text/html";
    final Date DATE = new Date();
    String body;

    public Response(String startLine, String body) {
        this.startLine = startLine;
        this.body = body;
    }

}
