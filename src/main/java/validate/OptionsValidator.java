package validate;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import method.BaseMethod;
import method.GetMethod;
import method.PostMethod;
import service.FileReadingService;

import java.util.ArrayList;
import java.util.List;

import static constant.Constants.CONTENT_LENGTH;
import static constant.Constants.POST;

public final class OptionsValidator {

    private OptionsValidator() {
    }

    public static BaseMethod validate(String method, String[] args) {
        try {
            boolean verbose = false;
            List<String> headers = new ArrayList<>();

            OptionParser parser = new OptionParser();

            OptionSpec<Void> verboseSpec = parser.accepts("v");
            OptionSpec<String> headersSpec = parser.accepts("h")
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
            if (options.has(headersSpec)) {
                headers.addAll(options.valuesOf(headersSpec));
            }

            if (method.equalsIgnoreCase(POST)) {
                String body = "";

                if (inline != null && options.has(inline)) {
                    body = options.valueOf(inline);
                } else if (file != null && options.has(file)) {
                    String filePath = options.valueOf(file);
                    body = FileReadingService.getBodyFromFile(filePath);
                }
                headers.add(CONTENT_LENGTH + body.length());

                return new PostMethod("", "", headers, verbose, body);
            }

            return new GetMethod("", "", headers, verbose);
        } catch(Exception e) {
            System.err.println("idk exception names");
            System.err.println(e);
        }

        return null;
    }

}
