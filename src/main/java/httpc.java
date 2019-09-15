import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class httpc {

    public static void main(String[] args) {
        // Quick example of jopt-simple
        OptionParser parser = new OptionParser("h:");
        OptionSpec<Void> helpOption = parser
                .accepts("help", "show help").forHelp();
        OptionSet options = parser.parse(args);
        if (options.has(helpOption)) {
            System.out.println("Print help...\n");
        }

        if (options.has("h")) {

            System.out.println("Argument after -h: " + options.valueOf("h") + "\n");
        }


        // Simple connection with sockets/InetAddress
        try {
            InetAddress address = InetAddress.getByName("httpbin.org");
            Socket socket = new Socket(address, 80);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET /status/418 HTTP/1.0");
            out.println("");

            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            System.err.println("Invalid URL. Unknown Host");
        } catch (IOException e) {
            System.err.println("Something went wrong while connecting");
        }
    }
}
