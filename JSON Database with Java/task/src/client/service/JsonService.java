package client.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.sun.security.auth.UnixNumericGroupPrincipal;
import payload.ClientRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonService {
    static public List<String> getValuesInObject(JsonObject jsonObject, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (String currentKey : jsonObject.keySet()) {
            Object value = jsonObject.get(currentKey);
            if (currentKey.equals(key)) {
                accumulatedValues.add(value.toString());
            }

            if (value instanceof JsonObject) {
                accumulatedValues.addAll(getValuesInObject((JsonObject) value, key));
            } else if (value instanceof JsonArray) {
                accumulatedValues.addAll(getValuesInArray((JsonArray) value, key));
            }
        }

        return accumulatedValues;
    }

    static public List<String> getValuesInArray(JsonArray jsonArray, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (Object obj : jsonArray) {
            if (obj instanceof JsonArray) {
                accumulatedValues.addAll(getValuesInArray((JsonArray) obj, key));
            } else if (obj instanceof JsonObject) {
                accumulatedValues.addAll(getValuesInObject((JsonObject) obj, key));
            }
        }

        return accumulatedValues;
    }

    static public List<ClientRequest> readJsonStream(InputStream in) {
        try(JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));) {
            return readClientRequestObject(reader);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return null;
        }
    }

    static public List<ClientRequest> readClientRequestObject(JsonReader reader) throws IOException {
        List<ClientRequest> clientRequest = new ArrayList<>();
        String type = null;
        String key = null;
        String value = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("type")) {
                type = reader.nextString();
            } else if (name.equals("key")) {
                key = reader.nextString();
            } else if (name.equals("value")) {
                value = reader.nextString();
            }
            clientRequest.add(new ClientRequest(type, key, value));
//            clientRequest.add(readClientRequest(reader));
        }
        reader.endObject();
        return clientRequest;
    }

    static public ClientRequest readClientRequest(JsonReader reader) throws IOException {
        String type = null;
        String key = null;
        String value = null;

        reader.beginArray();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("type")) {
                type = reader.nextString();
            } else if (name.equals("key")) {
                key = reader.nextString();
            } else if (name.equals("value")) {
                value = reader.nextString();
            }
        }
        reader.endArray();
        return new ClientRequest(type, key, value);
    }
}
