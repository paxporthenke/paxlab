package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestResource {

    public static Collection<String> list() {
        List<String> response = new ArrayList<>();
        response.add("hello");
        response.add("world!");
        return response;
    }
}
