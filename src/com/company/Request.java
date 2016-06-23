package com.company;

import java.util.*;

/**
 * Created by ksenia on 6/23/16.
 */
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

    public static Request parse(ArrayList<String> lines) {
        String requestLineStr = lines.get(0);
        String[] requestLineArr = requestLineStr.split(" ");
        String method = requestLineArr[0];
        String URI = requestLineArr[1];
        String status = requestLineArr[2];

        RequestLine requestLine = new RequestLine(method, URI, status);

        // get header and the body
        int index = lines.indexOf("\r\n\r\n");
        List headerList = lines.subList(0, index);
        List bodyList = lines.subList(index, -1);

        Map<String, String> headersMap = new HashMap<>();

        for(int i = 0; i <headerList.size(); i++) {
            String line = headerList.get(i);
            headersMap.put(line[0], line[1]);
        }

        String bodyString = "";
        for(int i = 0; i < bodyList.size(); i++) {
            bodyString += bodyList.get(i);
        }

        return new Request(headersMap, bodyString, requestLine);
    }
}

