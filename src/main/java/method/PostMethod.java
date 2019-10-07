package method;

import java.util.Map;

import static constant.Constants.POST;

public class PostMethod extends BaseMethod {

    private String body;

    public PostMethod(Map<String, String> headers, boolean verbose, String body) {
        super(headers, verbose);
        this.body = body;
    }

    @Override
    public String getMethod() {
        return POST;
    }

    @Override
    public String toString() {
        return super.toString() + "\r\n" + body;
    }
}
