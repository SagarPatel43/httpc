package service;

import method.BaseMethod;
import validate.OptionsValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static constant.Constants.*;

public class ParsingService {

    private static final Set<String> METHODS = new HashSet<>();
    private static final Map<String, String> HELP_METHODS = new HashMap<>();

    static {
        METHODS.add(GET);
        METHODS.add(POST);

        HELP_METHODS.put(GET, GET_HELP);
        HELP_METHODS.put(POST, POST_HELP);
    }

    public static BaseMethod parseRequest(String[] args) throws Exception {
        BaseMethod httpMethod = null;

        // No arguments specified
        if (args.length == 0) {
            //TODO print help
            System.err.println("No arguments specified");
        }
        // Singular argument... can only be HELP.
        else if (args.length == 1 && args[0].equalsIgnoreCase(HELP)) {
            //TODO print help
            System.out.println("help");
        }
        // Two arguments, where first one is HELP (e.g. help post)
        else if (args.length == 2 && args[0].equalsIgnoreCase(HELP)) {
            String method = args[1].toUpperCase();

            if (HELP_METHODS.get(method) != null) {
                System.out.println(HELP_METHODS.get(method));
            }
            else {
                //TODO print help
                //TODO make it an exception
                System.err.println("Only GET/POST methods are currently supported");
            }
        }
        // Not help, request
        else {
            // First argument should always be HTTP method
            String requestMethod = args[0].toUpperCase();
            // Last argument should always be URL
            String urlHost = args[args.length - 1].replaceAll("(http://|https://)", "");
            // Clean up URL - extract host and URI
            // TODO this can break if there's no /
            String host;
            String uri;
            if (urlHost.contains("/")) {
                host = urlHost.substring(0, urlHost.indexOf("/"));
                uri = urlHost.substring(urlHost.indexOf("/"));
            } else {
                throw new Exception("bad");
            }

            // First check if valid HTTP method was provided (GET/POST) and flags
            if (METHODS.contains(requestMethod)) {
                httpMethod = OptionsValidator.validate(requestMethod, args);
            }
            else {
                System.err.println("Only GET/POST methods are currently supported");
            }

            // TODO pretty sure this can NPE so try catch
            httpMethod.setHost(host);
            httpMethod.setUri(uri);
        }

        return httpMethod;
    }
}
