package method;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String httpVersion;
    private String statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private String headerResponse;
    private String body;

    public Response() {
        this.statusCode = "";
        this.headers = new HashMap<>();
        this.headerResponse = "";
        this.body = "";
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String header, String headerValue) {
        headers.put(header, headerValue);
    }

    public String getHeaderValue(String header) {
        return headers.get(header);
    }

    public void appendBody(String body) {
        this.body += body;
    }

    public void appendHeaderResponse(String line) {
        headerResponse += line;
    }

    public String getVerboseOutput() {
        return httpVersion + " " + statusCode + " " + reasonPhrase + "\r\n" + headerResponse + "\r\n" + body;
    }

    public String getSimpleOutput() {
        return body;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaderResponse() {
        return headerResponse;
    }

    public void setHeaderResponse(String headerResponse) {
        this.headerResponse = headerResponse;
    }
}
