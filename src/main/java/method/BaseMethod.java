package method;

import java.util.Map;

import static constant.Constants.HTTP1;

public abstract class BaseMethod {

    private String host;
    private String uri;
    private int port;
    private Map<String, String> headers;
    private boolean verbose;
    private String fileOutput;

    BaseMethod(Map<String, String> headers, boolean verbose) {
        this.headers = headers;
        this.verbose = verbose;
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    private String getHeadersRequest() {
        StringBuilder headersRequest = new StringBuilder();
        headers.forEach((header, headerValue) -> headersRequest.append(header)
                .append(": ")
                .append(headerValue)
                .append("\r\n"));

        return headersRequest.toString();
    }

    public void setHeaders(Map<String, String> headers) {
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
