package method;

import java.util.List;

import static constant.Constants.POST;

public class PostMethod extends BaseMethod {

    private String body;

    public PostMethod(String host, String uri, List<String> headers, boolean verbose, String body, String fileOutput) {
        super(host, uri, headers, verbose, fileOutput);
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
