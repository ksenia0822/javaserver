
import java.util.*;

public class Request {
    private Map<String, String> headers;
    private String body;
    private RequestLine requestLine;

    public Request(Map<String, String> headers, String body, RequestLine requestLine) {
        this.headers = headers;
        this.body = body;
        this.requestLine = requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }



    public String toString() {
        return "Request Line: " + getRequestLine() + '\n' +  "Headers: " + getHeaders();
    }

    public static Request parse(List<String> lines) {
        String requestLineStr = lines.get(0);
        String[] requestLineArr = requestLineStr.split(" ");
        String method = requestLineArr[0];
        String URI = requestLineArr[1];
        String status = requestLineArr[2];

        RequestLine requestLine = new RequestLine(URI, method, status);


        //  int index = lines.indexOf("\r\n\r\n");
        //  List<String> headerList = lines.subList(1, index);
        // List<String> bodyList = lines.subList(index, -1);

        List<String> headerList = lines.subList(1, lines.size()-1);
        Map<String, String> headersMap = new LinkedHashMap<>();

        for(int i = 0; i <headerList.size()-1; i++) {
            String line = headerList.get(i);
            String[] arr = line.split(" ");
            headersMap.put(arr[0], arr[1]);
        }

        /*String bodyString = "";
        for(int i = 0; i < bodyList.size(); i++) {
            bodyString += bodyList.get(i);
        }*/

        return new Request(headersMap, "", requestLine);
    }

    public static void parseString(String str) {

    }


}