package services.v2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dispatch.AbstractRestService;
import dispatch.RestResponse;
import dispatch.RestResponseSuccessful;
import domain.TestResource;

import java.util.Collection;
import java.util.Map;

public class TestResourceRestService extends AbstractRestService {

    @Override
    public RestResponse list(Map<String, String> pathArgs) {
        Collection<String> entities = TestResource.list();
        JsonArray response = new JsonArray();
        for (String entity : entities) {
            response.add(new JsonPrimitive(entity));
        }
        return new RestResponseSuccessful(response);
    }

    @Override
    public RestResponse lookup(String id, Map<String, String> pathArgs) {
        return new RestResponseSuccessful();
    }

    @Override
    public RestResponse update(String id, JsonElement payload, Map<String, String> pathArgs) {
        System.out.println("updating entity "+id+", v1: " + payload);
        JsonObject response = new JsonObject();
        response.add("modified", new JsonPrimitive(true));
        return new RestResponseSuccessful(response);
    }

    @Override
    public RestResponse delete(String id, JsonElement payload, Map<String, String> pathArgs) {
        JsonObject response = new JsonObject();
        response.add("deleted", new JsonPrimitive(true));
        return new RestResponseSuccessful(response);
    }
}

