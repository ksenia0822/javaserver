import java.util.HashMap;

/**
 * Created by ksenia on 6/24/16.
 */
public class Req {

    int requestLine;
    HashMap<String, String> headers;
    HashMap<String, String> body;

    public Req(int requestLine, HashMap<String, String> headers, HashMap<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public int getRequestLine() {
        return requestLine;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setRequestLine(int requestLine) {
        this.requestLine = requestLine;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }
}
