package method;

import java.util.List;

import static constant.Constants.GET;

public class GetMethod extends BaseMethod {

    public GetMethod(String host, String uri, List<String> headers, boolean verbose) {
        super(host, uri, headers, verbose);
    }

    @Override
    public String getMethod() {
        return GET;
    }
}
