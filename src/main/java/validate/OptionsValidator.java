package validate;

import exception.HttpcException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import method.BaseMethod;
import method.GetMethod;
import method.PostMethod;
import service.FileService;

import java.util.ArrayList;
import java.util.List;

import static constant.Constants.CONTENT_LENGTH;
import static constant.Constants.POST;

public final class OptionsValidator {

    private OptionsValidator() {
    }

    public static BaseMethod validate(String method, String[] args) throws HttpcException {
        try {
            boolean verbose = false;
            String outputFilePath = "";

            List<String> headers = new ArrayList<>();

            OptionParser parser = new OptionParser();

            OptionSpec<Void> verboseSpec = parser.accepts("v");
            OptionSpec<String> headersSpec = parser.accepts("h")
                    .withRequiredArg();
            OptionSpec<String> fileOutputSpec = parser.accepts("o")
                    .withRequiredArg();

            OptionSpec<String> inline = null;
            OptionSpec<String> file = null;

            if (method.equalsIgnoreCase(POST)) {
                inline = parser.accepts("d")
                        .withRequiredArg();
                file = parser.accepts("f")
                        .availableUnless(inline)
                        .withRequiredArg();
            }

            OptionSet options = parser.parse(args);

            if (options.has(verboseSpec)) {
                verbose = true;
            }
            if (options.has(fileOutputSpec)) {
                outputFilePath = options.valueOf(fileOutputSpec);
            }
            if (options.has(headersSpec)) {
                for (String header : options.valuesOf(headersSpec)) {
                    // Ignore explicit content-length headers, will be handled by the client.
                    if (!header.contains(CONTENT_LENGTH)) {
                        headers.add(header);
                    }
                }
            }

            if (method.equalsIgnoreCase(POST)) {
                String body = "";

                if (inline != null && options.has(inline)) {
                    body = options.valueOf(inline);
                }
                else if (file != null && options.has(file)) {
                    String filePath = options.valueOf(file);
                    body = FileService.getBodyFromFile(filePath);
                }
                headers.add(CONTENT_LENGTH + body.length());

                return new PostMethod("", "", headers, verbose, body, outputFilePath);
            }

            return new GetMethod("", "", headers, verbose, outputFilePath);
        } catch (HttpcException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpcException("Something went wrong while trying to parse the flags provided");
        }
    }
}
