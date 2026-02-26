package payload;

import com.google.gson.JsonElement;

public class ServerResponse {
    private String response;
    private JsonElement value;
    private String reason;

    public ServerResponse(String response) {
        this.response = response;
    }

    public ServerResponse(String response, JsonElement value) {
        this.response = response;
        this.value = value;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement responseValue) {
        this.value = responseValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
