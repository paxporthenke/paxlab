package dispatch;

import com.google.gson.JsonElement;

public class RestResponseError extends RestResponse {

    private final int status;

    public RestResponseError(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public JsonElement getData() {
        return null;
    }
}
