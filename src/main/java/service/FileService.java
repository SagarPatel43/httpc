package service;

import exception.HttpcException;

import java.io.*;

public final class FileService {

    private FileService() {}

    public static String getBodyFromFile(String filePath) throws HttpcException {
        File file = new File(filePath);
        StringBuilder body = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                body.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new HttpcException("File at " + filePath + " could not be found");
        }

        return body.toString();
    }

    static void outputToFile(String filePath, String output) throws HttpcException {
        try {
            PrintWriter printWriter;
            printWriter = new PrintWriter(new FileWriter(filePath));
            printWriter.println(output);
            printWriter.close();
        } catch (IOException e) {
            throw new HttpcException("File at " + filePath + " could not be found");
        }
    }
}
