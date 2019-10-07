package method;

import java.util.Map;

import static constant.Constants.GET;

public class GetMethod extends BaseMethod {

    public GetMethod(Map<String, String> headers, boolean verbose) {
        super(headers, verbose);
    }

    @Override
    public String getMethod() {
        return GET;
    }
}
