import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Server {

    private static HashMap<String, Integer> requestCount = new HashMap<String, Integer>();

    public static void sendResponseHeaders(Response response, PrintWriter output) {
        output.println(response.getStartLine());
        output.println(response.getCONTENT_TYPE());
        output.println(response.getDATE());
        output.println("");
        output.println(response.getBody());
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);

            while (true) {

                Socket clientSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

                String reqLine = bufferedReader.readLine();

                Map<String, String> headers = readHeaders(bufferedReader);

                String method = reqLine.split(" ")[0].trim();
                String startLine = new String();
                String responseBody = new String();

                addToRequestCount(method);

                if(method.equals("POST")){
                    int contentLength = Integer.valueOf(headers.get("Content-Length"));
                    if(contentLength > 0) {
                        char[] body = new char[contentLength];
                        int amountToRead = bufferedReader.read(body);
                        String bodyStr = new String(body);
                        String email = bodyStr.split("\n")[0].substring(6);
                        String password = bodyStr.split("\n")[1].substring(9);

                        Credentials userCredentials = new Credentials("ksenia@gmail.com", "123");

//                        if(email.equals(userCredentials.getEmail()) && password.equals(userCredentials.getPassword())) {
//                            startLine = "HTTP/1.0 200 OK";
//                            responseBody = "The credentials are correct. You are logged in";
//                        } else {
//                            if(requestCount.get("POST") > 3) {
//                                startLine = "HTTP/1.0 429 Too Many Requests";
//                                responseBody = "You've exceeded the ammount of attepts. Try again later";
//                                output.close();
//                                bufferedReader.close();
//                                clientSocket.close();
//                            }
//                            else {
//                                startLine = "HTTP/1.0 401 Unauthorized";
//                                responseBody = "The credentials are not correct. Try again";
//                            }
//                        }


                        if(requestCount.get("POST") < 4) {
                            if(email.equals(userCredentials.getEmail()) && password.equals(userCredentials.getPassword())) {
                                startLine = "HTTP/1.0 200 OK";
                                responseBody = "The credentials are correct. You are logged in";
                            } else {
                                startLine = "HTTP/1.0 401 Unauthorized";
                                responseBody = "The credentials are not correct. Try again";
                            }
                        } else {
                            startLine = "HTTP/1.0 429 Too Many Requests";
                            responseBody = "You've exceeded the ammount of attepts. Try again later";
                            output.close();
                            bufferedReader.close();
                            clientSocket.close();
                        }

                    } else {
                        startLine = "HTTP/1.0 401 Unauthorized";
                        responseBody = "Please enter email and password";
                    }

                }

                if (method.equals("GET")){
                    startLine = "HTTP/1.0 200 OK";
                    responseBody = "hello world";
                }

                Response newResponse = new Response(startLine, responseBody);
                sendResponseHeaders(newResponse, output);

                output.close();
                bufferedReader.close();
                clientSocket.close();
            }

        } catch (IOException error) {
            System.out.println(error);
        }
    }


    public static Map<String, String> readHeaders(BufferedReader bufferedReader) throws IOException{

        Map<String, String> headers = new LinkedHashMap<>();

        String inputLine = bufferedReader.readLine();
        while(inputLine != null && !inputLine.isEmpty()) {

            String key = inputLine.split(":")[0].trim();
            String value = inputLine.split(":")[1].trim();

            headers.put(key, value);
            inputLine = bufferedReader.readLine();

        }
        return headers;
    }

    public static void addToRequestCount(String method) throws IOException{
        if (!requestCount.containsKey(method)) {
            requestCount.put(method, 1);
        } else {
            int currentCount = requestCount.get(method);
            currentCount++;
            requestCount.put(method, currentCount);
        };
    }

}





