package service;

import exception.HttpcException;
import method.BaseMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static constant.Constants.LOCATION;
import static constant.Constants.REDIRECT_PREFIX;
import static service.FileService.outputToFile;

public class RequestService {

    private static final int DEFAULT_PORT = 80;

    public static void execute(BaseMethod method) throws HttpcException {
        do {
            try {
                InetAddress address = InetAddress.getByName(method.getHost());
                Socket socket = new Socket(address, DEFAULT_PORT);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(method);

                String output = getOutput(in, method);

                if (!method.getStatus().startsWith(REDIRECT_PREFIX)) {
                    // Console
                    if (method.getFileOutput() == null) {
                        System.out.println(output);
                    }
                    else {
                        outputToFile(method.getFileOutput(), output);
                    }
                } else {
                    System.out.println("\nDestination issued a " + method.getStatus() + " redirect to new location: " + method.getUri());
                    System.out.println("Issuing new request...\n");
                }

                in.close();
                socket.close();
            } catch (UnknownHostException e) {
                throw new HttpcException("Could not connect to URL provided");
            } catch (IOException e) {
                throw new HttpcException("Network error");
            }
        } while(method.getStatus().startsWith(REDIRECT_PREFIX));
    }

    private static String getOutput(BufferedReader in, BaseMethod method) throws IOException {
        String line;
        StringBuilder output = new StringBuilder();
        boolean entityBody = false;
        boolean status = false;
        boolean redirect = false;
        while ((line = in.readLine()) != null) {
            //parse out status present in first line of output
            if (!status && line.split(" ").length > 1) {
                method.setStatus(line.split(" ")[1]);
                status = true;
                redirect = true;
            }
            if (redirect && line.startsWith(LOCATION)) {
                String urlHost = line.split(": ")[1].replaceAll("(http://|https://)", "");
                method.setUri(urlHost.substring(urlHost.indexOf("/")));
                break;
            }

            //if verbose, display everything (Header and Body)
            if (method.isVerbose()) {
                output.append(line).append("\n");
            }
            //if not verbose, display only body
            else {
                if (!entityBody && line.trim().isEmpty()) {
                    entityBody = true;
                }
                if (entityBody) {
                    output.append(line).append("\n");
                }

            }
        }
        return output.toString();
    }
}
