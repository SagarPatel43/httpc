import method.BaseMethod;
import service.ParsingService;
import service.RequestService;

public class httpc {

    public static void main(String[] args) {
        BaseMethod httpMethod = ParsingService.parseRequest(args);

        RequestService.execute(httpMethod);
    }
}
