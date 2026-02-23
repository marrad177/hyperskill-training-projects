package payload;

public class ServerResponse {
    private String response;
    private String value;
    private String reason;

    public ServerResponse(String response) {
        this.response = response;
    }

    public ServerResponse(String response, String value) {
        this.response = response;
        this.value = value;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String responseValue) {
        this.value = responseValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
