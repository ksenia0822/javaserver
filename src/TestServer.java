import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class TestServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);

            while (true) {
                List<String> list = new ArrayList<String>();

                Socket clientSocket = serverSocket.accept();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

                String reqLine = bufferedReader.readLine();

                Map<String, String> headers = readHeaders(bufferedReader);

                int contentLength = Integer.valueOf(headers.get("Content-Length"));

                char[] body = new char[contentLength];

                int amountToRead = bufferedReader.read(body);

                System.out.println("this is the request line: " + reqLine);
                System.out.println("this is the headers: " + headers);
                System.out.println("this is content length: " + contentLength);

                System.out.println(body);

//                List<String> headers = readHeaders(bufferedReader);
//                System.out.println("these are the headers: " + headers);


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


//    public static List<String> readHeaders(BufferedReader bufferedReader) throws IOException{
//
//        List<String> headers = new ArrayList<>();
//
//        String inputLine = bufferedReader.readLine();
//
//        while(inputLine != null && !inputLine.isEmpty()) {
//
//            headers.add(inputLine);
//            inputLine = bufferedReader.readLine();
//
//        }
//        return headers;
//
//    }
}














