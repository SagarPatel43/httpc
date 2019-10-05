package service;

import exception.HttpcException;
import method.BaseMethod;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RequestService {

    private static final int DEFAULT_PORT = 80;

    public static void execute(BaseMethod method) throws HttpcException {
        try {
            InetAddress address = InetAddress.getByName(method.getHost());
            Socket socket = new Socket(address, DEFAULT_PORT);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(method);

            String output = getOutput(in, method.isVerbose());
            // Console
            if (method.getFileOutput().isEmpty()) {
                System.out.println(output);
            } else {
                outputToFile(method.getFileOutput(), output);
            }

            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            throw new HttpcException("Could not connect to URL provided");
        } catch (IOException e) {
            throw new HttpcException("Network error");
        }
    }

    private static void outputToFile(String filePath, String output) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filePath));
        printWriter.println(output);
        printWriter.close();
    }

    private static String getOutput(BufferedReader in, boolean verbose) throws IOException {
        String line;
        StringBuilder output = new StringBuilder();
        boolean entityBody = false;
        while ((line = in.readLine()) != null) {
            //if verbose, display everything (Header and Body)
            if (verbose) {
                output.append(line).append("\n");
            }
            //if not verbose, display only body by trimming header
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
