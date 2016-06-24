import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader bufferedReader;
    private static String inputLine;
    private static PrintWriter output;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(3000);

            while (true) {
                List<String> list = new ArrayList<String>();
                clientSocket = serverSocket.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream());

                inputLine = bufferedReader.readLine();
                while (inputLine.length() > 0) {
                    System.out.println(inputLine);
                    list.add(inputLine);
                    inputLine = bufferedReader.readLine();
                }

//                String bodyLine = bufferedReader.readLine();
//                while(bodyLine != null && bodyLine.length() > 0){
//                    System.out.println(bodyLine);
//                    output.println(bodyLine);
//                    bodyLine = bufferedReader.readLine();
//                }


                Request r = Request.parse(list);
                if (r.getRequestLine().getMethod().equals("GET")){
                    output.println("HTTP/1.0 200 OK");
                    output.println("Content-Type: text/html");
                    output.println("");
                    output.println("<p>GET REQUEST: </p><p>" + r + "</p>");
                }

                if (r.getRequestLine().getMethod().equals("POST")){
                    output.println("HTTP/1.0 200 OK");
                    output.println("Content-Type: text/html");
                    output.println("");
                    output.println("<p>POST REQUEST:</p><p>" + r + "</p>");
                }

                output.close();
                bufferedReader.close();
                clientSocket.close();

            }


        } catch (IOException error) {
            System.out.println(error);

        }

    }
}