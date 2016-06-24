import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;


public class test {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        while (true) {
            Socket socket = serverSocket.accept();

            InputStream in = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String requestLine = bufferedReader.readLine();

            // assign the current headers to map
            Map<Header, String> headers = readHeaders(bufferedReader);
            System.out.println("HEADERS ARE: " + headers);
            //if there is no key "contains length, throw an exception
            if (!headers.containsKey(Header.CONTENT_LENGTH)) {
                throw new RuntimeException("EXPECTED A POST");
            }

            //get the content length from a header
            int contentLength = Integer.valueOf(headers.get(Header.CONTENT_LENGTH));
            // create an array of the size of content length
            char[] body = new char[contentLength];

            System.out.println("Reading more from input stream");

            int amountRead = bufferedReader.read(body);
            System.out.println("Read this much from the input stream: " + amountRead);

            System.out.println("Content was: " + new String(body));

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write("HTTP/1.1 200 OK\n");
            outputStreamWriter.write("Content-Length: " + contentLength + "\n");
            outputStreamWriter.write(new String(body));
            outputStreamWriter.close();
            bufferedReader.close();

            socket.close();
        }
    }

    private static Map<Header, String> readHeaders(BufferedReader bufferedReader) throws IOException {
        // initialize map with enum values as keys and strings as values
        Map<Header, String> headers = new LinkedHashMap<>();

        // initialized empty string
        String line;

        //compare string line with bufferedReader.readLine()
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            Header foundHeader = null;
            // iterate through enum obj
            for (Header header : Header.values()) {
                // if current line from bufferdReader starts with one of the enum values
                if (line.startsWith(header.asString())) {
                    //mathichHeader equals that enum
                    foundHeader = header;
                    //break after it finds matching enum
                    break;
                }
            }
            // if there is a mathing header


            if (foundHeader != null) {
                // get that header
                Header header = foundHeader;
                //get the value of a header
                String value = line.split(":")[1].trim();
                System.out.println("Found " + header + " pointing to: " + value);
                // put the key and value of the header in the hashmap
                headers.put(header, value);
            } else {
                System.out.println("Didn't find a matching header for " + line);
            }
        }
        //return hashmap of headers
        return headers;
    }

    enum Header {
        USER_AGENT("User-Agent"),
        HOST("Host"),
        ACCEPT("Accept"),
        CONTENT_LENGTH("Content-Length");

        private final String headerString;

        Header(String headerString) {
            this.headerString = headerString;
        }

        public String asString() {
            return headerString;
        }
    }
}