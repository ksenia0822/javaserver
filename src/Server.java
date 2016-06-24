import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);

            while (true) {
                List<String> list = new ArrayList<String>();

                Socket clientSocket = serverSocket.accept();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream());


                int contentLength = Integer.valueOf("15");
                char[] body = new char[contentLength];
                int amountToRead = bufferedReader.read(body);

               String inputLine = bufferedReader.readLine();

//                StringWriter writer = new StringWriter();
//                char[] arr = new char[1000];
//
//                int read;
//                while((read = inputReader.read(arr, 0, 1000)) != -1) {
//                    writer.write(arr, 0, read);
//                }
//                inputReader.close();
//                System.out.println(writer.toString())

                int counter = 0;
                String reqLine = "";
                String currentHeader = "";
                String bodyChar = "";

                while (inputLine != null) {
                    //System.out.println(inputLine);
                    if(counter == 0) {
                        reqLine = inputLine;
                    }
                    else {
                        currentHeader = inputLine;
                    }
                    if(currentHeader.length() > 14 && currentHeader.substring(0, 15).equals("Content-Length:")) {
                        bodyChar = currentHeader.substring(15);
                    }

                    list.add(inputLine);

                    counter++;

                    output.println(inputLine);
                    inputLine = bufferedReader.readLine();

                }
                 System.out.println("this is the request line: " + reqLine);
                 System.out.println("this is current header line: " + currentHeader);
                 System.out.println("this is char in body: " + bodyChar);


//                bufferedReader.readLine();
//                String bodyLine = bufferedReader.readLine();
//                while(bodyLine != null && bodyLine.length() > 0){
//                    System.out.println(bodyLine);
//                    output.println(bodyLine);
//                    bodyLine = bufferedReader.readLine();
//                }

//                output.println("HTTP/1.0 200 OK");
//                output.println("Content-Type: text/html");
//                output.println("");
//                output.println("test");
//                output.println(Request.parse(list));

               Request r = Request.parse(list);
                System.out.println("Headers: " + r.getHeaders());

                if (r.getRequestLine().getMethod().equals("GET")){
                    output.println("HTTP/1.0 200 OK");
                    output.println("Content-Type: text/html");
                    output.println("");
                    output.println("<p>GET: </p><p>" + r + "</p>");
                }

                if (r.getRequestLine().getMethod().equals("POST")){
                    output.println("HTTP/1.0 200 OK");
                    output.println("Content-Type: text/html");
                    output.println("");
                    output.println("<p>POST:</p><p>" + r + "</p>");
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