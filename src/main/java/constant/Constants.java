package constant;

public abstract class Constants {

    public static final int DEFAULT_HTTP_PORT = 80;

    public final static String HELP = "help";
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String HTTP1 = "HTTP/1.0";
    public final static String REDIRECT_PREFIX = "3";
    public final static String LOCATION = "Location";

    public final static String GENERAL_HELP = "\nhttpc is a curl-like application but supports HTTP protocol only.\n" +
                                              "Usage:\n\thttpc command [arguments]\n" +
                                              "The commands are:\n" +
                                              "\tget  \texecutes a HTTP GET request and prints the response.\n" +
                                              "\tpost \texecutes a HTTP POST request and prints the response.\n" +
                                              "\thelp \tprints this screen.\n\n" +
                                              "Use \"httpc help [command]\" for more information about a command.";

    public final static String GET_HELP = "\nGet executes a HTTP GET request for a given URL.\n" +
                                          "Usage:\n\thttpc get [-v] [-h key:value] URL\nThe options are:\n" +
                                          "\t-v\t            prints the detail of the response such as protocol, status, and headers.\n" +
                                          "\t-h key:value\tassociates headers to HTTP Request with the format 'key:value'.\n";

    public final static String POST_HELP = "\nPost executes a HTTP POST request for a given URL with inline data or from file.\n" +
                                           "Usage:\n\t httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\nThe options are:\n" +
                                           "\t-v\t            prints the detail of the response such as protocol, status, and headers.\n" +
                                           "\t-h key:value\tassociates headers to HTTP Request with the format 'key:value'.\n" +
                                           "\t-d\t            associates an inline data to the body HTTP POST request.\n" +
                                           "\t-f file\t        associates the content of a file to the body HTTP POST request.\n" +
                                           "\nEither [-d] or [-f] can be used but not both\n";

    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String USER_AGENT = "User-Agent";
}
