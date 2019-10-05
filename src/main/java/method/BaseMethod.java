package method;

import java.util.List;

import static constant.Constants.HTTP1;

public abstract class BaseMethod {

    private String host;
    private String uri;
    private List<String> headers;
    private boolean verbose;
    private String fileOutput;

    BaseMethod(String host, String uri, List<String> headers, boolean verbose, String fileOutput) {
        this.host = host;
        this.uri = uri;
        this.headers = headers;
        this.verbose = verbose;
        this.fileOutput = fileOutput;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getHeaders() {
        return headers;
    }

    String getHeadersRequest() {
        StringBuilder headersRequest = new StringBuilder();
        for (String header : headers) {
            headersRequest.append(header)
                    .append("\r\n");
        }

        return headersRequest.toString();
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getFileOutput() {
        return fileOutput;
    }

    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }

    public abstract String getMethod();

    @Override
    public String toString() {
        String requestLine = getMethod() + " " + getUri() + " " + HTTP1 + "\r\n";
        String headers = getHeadersRequest();
        return requestLine + headers;
    }
}
