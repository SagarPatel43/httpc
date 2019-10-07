package service;

import exception.HttpcException;
import method.BaseMethod;
import method.Response;

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

public final class RequestService {

    private RequestService() {}

    private static final int DEFAULT_PORT = 80;

    public static void execute(BaseMethod method) throws HttpcException {
        Response response;

        do {
            try {
                InetAddress address = InetAddress.getByName(method.getHost());
                Socket socket = new Socket(address, DEFAULT_PORT);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(method);

                response = getOutput(in);

                if (!response.getStatusCode().startsWith(REDIRECT_PREFIX)) {
                    String output = (method.isVerbose()) ? response.getVerboseOutput() : response.getSimpleOutput();
                    // Console
                    if (method.getFileOutput() == null) {
                        System.out.println(output);
                    }
                    else {
                        outputToFile(method.getFileOutput(), output);
                    }
                }
                else {
                    method.setUri(response.getHeaderValue(LOCATION));
                    System.out.println(
                            "\nDestination issued a " + response.getStatusCode() + " redirect to new location: " + method.getUri());
                    System.out.println("Issuing new request...\n");
                }

                in.close();
                socket.close();
            } catch (UnknownHostException e) {
                throw new HttpcException("Could not connect to URL provided");
            } catch (IOException e) {
                throw new HttpcException("Network error");
            }
        } while (response.getStatusCode().startsWith(REDIRECT_PREFIX));
    }

    private static Response getOutput(BufferedReader in) throws IOException, HttpcException {
        Response response = new Response();

        boolean entityBody = false;
        boolean status = false;

        String line;
        while ((line = in.readLine()) != null) {
            //parse out status present in first line of output
            if (!status) {
                if (line.split(" ").length < 3) {
                    throw new HttpcException("Invalid status-line returned in response");
                }
                String[] statusLine = line.split(" ", 3);
                response.setHttpVersion(statusLine[0]);
                response.setStatusCode(statusLine[1]);
                response.setReasonPhrase(statusLine[2]);

                status = true;
            }
            else if (!entityBody && line.trim().isEmpty()) {
                entityBody = true;
            }
            else if (!entityBody) {
                if (line.split(": ").length < 2) {
                    throw new HttpcException("Invalid header returned in response");
                }
                String[] headerValue = line.split(": ");
                response.addHeader(headerValue[0], headerValue[1]);
                response.appendHeaderResponse(line + "\r\n");
            }
            else {
                response.appendBody(line + "\n");
            }
        }

        if(!response.getBody().isEmpty()) {
            response.setBody(removeLastChar(response.getBody()));
        }

        return response;
    }

    private static String removeLastChar(String line) {
        return line.substring(0, line.length() - 1);
    }
}
