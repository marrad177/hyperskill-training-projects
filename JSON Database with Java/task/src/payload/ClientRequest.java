package payload;

public class ClientRequest {
    private String type;
    private String key;
    private String value;

    public ClientRequest(String commandType, String key, String message) {
        this.type = commandType;
        this.key = key;
        this.value = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String commandType) {
        this.type = commandType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String message) {
        this.value = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
