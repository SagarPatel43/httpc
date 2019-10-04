package service;

import method.BaseMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RequestService {

    private static final int DEFAULT_PORT = 80;

    public static void execute(BaseMethod method) {
        try{
            InetAddress address = InetAddress.getByName(method.getHost());
            Socket socket = new Socket(address, DEFAULT_PORT);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(method);

            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }

            // TODO Depending on verbose, produce different output using socket.


            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            // TODO double check exceptions everywhere
            System.err.println("Host given to InetAddress is invalid (invalid host)");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Network error");
            e.printStackTrace();
        }


    }
}
