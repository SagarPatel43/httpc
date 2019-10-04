import method.BaseMethod;
import service.ParsingService;
import service.RequestService;

public class httpc {

    public static void main(String[] args) {
        BaseMethod httpMethod = null;
        try {
            httpMethod = ParsingService.parseRequest(args);
            RequestService.execute(httpMethod);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something bad happened, print help, I want everything handled from here k thanks");
        }

    }
}
