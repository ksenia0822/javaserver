// java.io provides for sytem I/O throught data streams, serialization and the file system
import java.io.IOException;
// java.net package provides classes for implementing networking applications
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader; // reads text from a character-input stream, buffering characters so as to provide ofr the efficient reading of characters, arrays and lines
import java.io.InputStreamReader; // is a bridge form byte streams to character streams. It reads bytes and decodes them into characters using a specified charset
import java.io.PrintWriter;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader bufferedReader;
    private static String inputLine;
    private static PrintWriter output;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(3000); // create an instance of a server socket bound to the specified port

            while (true) {
                clientSocket = serverSocket.accept(); //listens for a connection to be made to this socket and accepts it
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //create a reader
                // it is advisible to wrap BufferedReader around any Reader whose read operations can be costly
                //.getInputStream() returns an input stream for reading bytes from this socket;
                output = new PrintWriter(clientSocket.getOutputStream());

                inputLine = bufferedReader.readLine();
                while (inputLine != null) {
                    if (inputLine.length() == 0) {
                        break;
                    }
                    output.println(inputLine);
                    inputLine = bufferedReader.readLine();
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