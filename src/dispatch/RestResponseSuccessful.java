package dispatch;

import com.google.gson.JsonElement;

public class RestResponseSuccessful extends RestResponse {

    private final JsonElement data;

    public RestResponseSuccessful() {
        this.data = null;
    }

    public RestResponseSuccessful(JsonElement data) {
        this.data = data;
    }

    @Override
    public int getStatus() {
        return 200;
    }

    @Override
    public JsonElement getData() {
        return data;
    }
}
