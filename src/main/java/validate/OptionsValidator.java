package validate;

import exception.HttpcException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import method.BaseMethod;
import method.GetMethod;
import method.PostMethod;
import service.FileService;

import java.util.HashMap;
import java.util.Map;

import static constant.Constants.*;

public final class OptionsValidator {

    private OptionsValidator() {}

    public static BaseMethod validate(String method, String[] args) throws HttpcException {
        try {
            BaseMethod baseMethod;
            boolean verbose;
            Map<String, String> headers = new HashMap<>();

            // Configure j-opt parser for valid flags
            OptionParser parser = new OptionParser();

            OptionSpec<Void> verboseSpec = parser.accepts("v");
            OptionSpec<String> headersSpec = parser.accepts("h")
                    .withRequiredArg();
            OptionSpec<String> fileOutputSpec = parser.accepts("o")
                    .withRequiredArg();

            OptionSpec<String> inlineSpec = null;
            OptionSpec<String> fileSpec = null;

            // If method is POST, parser should also accept d xor f
            if (method.equalsIgnoreCase(POST)) {
                inlineSpec = parser.accepts("d")
                        .withRequiredArg();
                fileSpec = parser.accepts("f")
                        .availableUnless(inlineSpec)
                        .requiredUnless(inlineSpec)
                        .withRequiredArg();
            }

            OptionSet options = parser.parse(args);

            verbose = options.has(verboseSpec);

            if (options.has(headersSpec)) {
                for (String header : options.valuesOf(headersSpec)) {
                    if (header.split(": ").length > 1) {
                        String[] headerLine = header.split(": ");
                        headers.put(headerLine[0], headerLine[1]);
                    } else{
                        throw new HttpcException("Invalid header provided (must be in format k: v)");
                    }
                }
            }

            headers.put(USER_AGENT, "445 Httpc Client");

            if (method.equalsIgnoreCase(POST)) {
                String body = "";

                if (inlineSpec != null && options.has(inlineSpec)) {
                    body = options.valueOf(inlineSpec);
                }
                else if (fileSpec != null && options.has(fileSpec)) {
                    String filePath = options.valueOf(fileSpec);
                    body = FileService.getBodyFromFile(filePath);
                }
                // implicitly added content-length header since mandatory for POST requests
                headers.put(CONTENT_LENGTH, Integer.toString(body.length()));

                baseMethod = new PostMethod(headers, verbose, body);
            } else {
                baseMethod = new GetMethod(headers, verbose);
            }

            if (options.has(fileOutputSpec)) {
                baseMethod.setFileOutput(options.valueOf(fileOutputSpec));
            }

            return baseMethod;
        } catch (HttpcException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpcException("Something went wrong while trying to parse the flags provided");
        }
    }
}
