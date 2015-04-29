package dispatch;

import com.google.gson.JsonElement;

import java.util.Map;

public abstract class AbstractRestService implements RestService{

    @Override
    public RestResponse lookup(String id, Map<String, String> pathArgs) { return new RestResponseBadRequest(); }

    @Override
    public RestResponse list(Map<String, String> pathArgs) {
        return new RestResponseBadRequest();
    }

    @Override
    public RestResponse create(JsonElement payload, Map<String, String> pathArgs) { return new RestResponseBadRequest(); }

    @Override
    public RestResponse update(String id, JsonElement payload, Map<String, String> pathArgs) { return new RestResponseBadRequest(); }

    @Override
    public RestResponse delete(String id, JsonElement payload, Map<String, String> pathArgs) { return new RestResponseBadRequest(); }
}
