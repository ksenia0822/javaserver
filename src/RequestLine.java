/**
 * Created by ksenia on 6/23/16.
 */
public class RequestLine {
    private final String URI;
    private final String method;
    private final String version;

    public RequestLine(String URI, String method, String version) {
        this.URI = URI;
        this.method = method;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getURI() {
        return URI;
    }

    public String getMethod() {
        return method;
    }

    public String toString() {
        return "Version: " + getVersion() + " - URI: " + getURI() + " - Method: " + getMethod();
    }

}