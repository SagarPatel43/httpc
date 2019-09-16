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
import java.util.List;

public class httpc {

    public static void main(String[] args) {
        // test parsing

        String method = args[0];
        String url = args[args.length - 1];

        // jopt should only detect two non option args i believe

        System.out.println(method);
        System.out.println(url);

        OptionParser parser = new OptionParser("v:");

        OptionSet options = parser.parse(args);


        List<?> nonOptionArguments = options.nonOptionArguments();
        // TODO assert that this is 2 - JOpt will catch invalid flags, manually will catch giberish
        System.out.println(nonOptionArguments.size());
        for(Object j : nonOptionArguments) {
            System.out.println(j);
        }

        // Perfect for headers
        System.out.println(options.valuesOf("v"));


//        // Quick example of jopt-simple
//        OptionParser parser = new OptionParser("h:");
//        OptionSpec<Void> helpOption = parser
//                .accepts("help", "show help").forHelp();
//        OptionSet options = parser.parse(args);
//        if (options.has(helpOption)) {
//            System.out.println("Print help...\n");
//        }
//
//        if (options.has("h")) {
//
//            System.out.println("Argument after -h: " + options.valueOf("h") + "\n");
//        }
//
//
////         Simple connection with sockets/InetAddress
//        try {
//            InetAddress address = InetAddress.getByName("httpbin.org");
//            Socket socket = new Socket(address, 80);
//
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            out.println("GET /status/418 HTTP/1.0");
//            out.println("");
//
//            String line;
//            while((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            in.close();
//            socket.close();
//
//        } catch (UnknownHostException e) {
//            System.err.println("Invalid URL. Unknown Host");
//        } catch (IOException e) {
//            System.err.println("Something went wrong while connecting");
//        }
//
//
//        try {
//            InetAddress address = InetAddress.getByName("httpbin.org");
//            Socket socket = new Socket(address, 80);
//
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            // TODO Only way to send valid JSON on bash
//            // "{\"Assignment\": 1}"
////            String test = "{\"Assignment\": 1}";
//            String test = args[0];
//            System.err.println(test);
//
//            out.println("POST /post HTTP/1.0");
//            out.println("Content-Length: " + test.length());
//            out.println("");
//            out.println(test);
//
//            String line;
//            while((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            in.close();
//            socket.close();
//
//        } catch (UnknownHostException e) {
//            System.err.println("Invalid URL. Unknown Host");
//        } catch (IOException e) {
//            System.err.println("Something went wrong while connecting");
//        }
    }
}
