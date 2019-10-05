package method;

import java.util.List;

import static constant.Constants.GET;

public class GetMethod extends BaseMethod {

    public GetMethod(List<String> headers, boolean verbose) {
        super(headers, verbose);
    }

    @Override
    public String getMethod() {
        return GET;
    }
}
