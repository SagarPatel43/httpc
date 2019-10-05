import exception.HttpcException;
import method.BaseMethod;
import service.ParsingService;
import service.RequestService;

import static constant.Constants.GENERAL_HELP;

public class httpc {

    public static void main(String[] args) {
        BaseMethod httpMethod;
        try {
            httpMethod = ParsingService.parseRequest(args);
            RequestService.execute(httpMethod);
        } catch (HttpcException e) {
            e.printStackTrace();
            System.out.println(GENERAL_HELP);
        }

    }
}
