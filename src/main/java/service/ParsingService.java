package service;

import exception.HttpcException;
import method.BaseMethod;
import validate.OptionsValidator;

import java.util.HashMap;
import java.util.Map;

import static constant.Constants.*;

public class ParsingService {

    private static final Map<String, String> HELP_METHODS = new HashMap<>();

    static {
        HELP_METHODS.put(GET, GET_HELP);
        HELP_METHODS.put(POST, POST_HELP);
    }

    public static BaseMethod parseRequest(String[] args) throws HttpcException {
        BaseMethod httpMethod = null;

        // No arguments specified or 1 argument specified (that's not help)
        if (args.length == 0 || (args.length == 1 && !args[0].equalsIgnoreCase(HELP))) {
            throw new HttpcException("Invalid command");
        }
        // Singular argument... can only be HELP.
        else if (args.length == 1 && args[0].equalsIgnoreCase(HELP)) {
            System.out.println(GENERAL_HELP);
            System.exit(0);
        }
        // Two arguments, where first one is HELP (e.g. help post)
        else if (args.length == 2 && args[0].equalsIgnoreCase(HELP)) {
            String method = args[1].toUpperCase();

            if (HELP_METHODS.containsKey(method)) {
                System.out.println(HELP_METHODS.get(method));
                System.exit(0);
            }
            else {
                throw new HttpcException("Only GET/POST methods are currently supported");
            }
        }
        // Not help, therefore treat as request (at least 2 arguments)
        else {
            // First argument should always be HTTP method
            String requestMethod = args[0].toUpperCase();
            // Last argument should always be URL
            String urlHost = args[args.length - 1].replaceAll("(http://|https://)", "");
            // Clean up URL - extract host and URI
            String host;
            String uri;
            if (urlHost.contains("/")) {
                host = urlHost.substring(0, urlHost.indexOf("/"));
                uri = urlHost.substring(urlHost.indexOf("/"));
            } else {
                throw new HttpcException("Invalid URL provided, make sure the URL contains a location (e.g. example.com/status)");
            }

            // First check if valid HTTP method was provided (GET/POST) and flags
            if (HELP_METHODS.containsKey(requestMethod)) {
                String[] flagArgs = getFlagsArray(args);
                httpMethod = OptionsValidator.validate(requestMethod, flagArgs);
            }
            else {
                throw new HttpcException("Only GET/POST methods are currently supported");
            }

            httpMethod.setHost(host);
            httpMethod.setUri(uri);
        }

        return httpMethod;
    }

    // Method and URL have already been parsed, so do not consider these when parsing flags
    private static String[] getFlagsArray(String[] args) {
        String[] flagArgs = new String[args.length - 2];
        System.arraycopy(args, 1, flagArgs, 0, flagArgs.length);

        return flagArgs;
    }
}
