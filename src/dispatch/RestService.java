package dispatch;

import com.google.gson.JsonElement;

import java.util.Map;

public interface RestService {
    RestResponse list(Map<String, String> pathArgs);
    RestResponse lookup(String id, Map<String, String> pathArgs);
    RestResponse create(JsonElement payload, Map<String, String> pathArgs);
    RestResponse update(String id, JsonElement payload, Map<String, String> pathArgs);
    RestResponse delete(String id, JsonElement payload, Map<String, String> pathArgs);
}
