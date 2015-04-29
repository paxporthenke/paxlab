package dispatch;

import com.google.gson.JsonElement;

import java.io.Serializable;

public abstract class RestResponse implements Serializable {
    public abstract int getStatus();
    public abstract JsonElement getData();
}
