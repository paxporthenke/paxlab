package dispatch;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher extends AbstractHandler {

    private static final String PACKAGE = "services";
    private static final String USERNAME = "paxport";
    private static final String PASSWORD = "paxp0rt";

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        String httpMethod = request.getMethod();
        target = StringUtils.strip(target, "/");
        RestResponse serviceResponse;
        String version = resolveAPIVersion(baseRequest);
        JsonObject wrapper = new JsonObject();
        try {
            authenticate(baseRequest);
            AbstractRestService service = resolveTargetClass(target, version);
            String[] parts = target.split("/");

            Map<String, String> pathArgs = resolvePathArgs(baseRequest);
            switch (httpMethod) {
                case "GET":
                    if (parts.length == 1) {
                        serviceResponse = service.list(pathArgs);
                    } else if (parts.length == 2) {
                        serviceResponse = service.lookup(parts[1], pathArgs);
                    } else {
                        serviceResponse = new RestResponseError(404);
                    }
                    break;
                case "POST":
                    if (parts.length == 1) {
                        serviceResponse = service.create(resolvePayload(baseRequest), pathArgs);
                    } else {
                        serviceResponse = new RestResponseBadRequest();
                    }
                    break;
                case "PATCH":
                    if (parts.length == 2) {
                        serviceResponse = service.update(parts[1], resolvePayload(baseRequest), pathArgs);
                    } else {
                        serviceResponse = new RestResponseBadRequest();
                    }
                    break;
                case "DELETE":
                    if (parts.length == 2) {
                        serviceResponse = service.delete(parts[1], resolvePayload(baseRequest), pathArgs);
                    } else {
                        serviceResponse = new RestResponseBadRequest();
                    }
                    break;
                default:
                    serviceResponse = new RestResponseBadRequest();
                    break;
            }
        } catch (AuthenticationException e){
            serviceResponse = new RestResponseError(401);
        } catch (ReflectiveOperationException e){
            serviceResponse = new RestResponseError(404);
        } catch (IOException e) {
            System.out.println(": " +e);
            serviceResponse = new RestResponseError(400);
        } catch (Exception e) {
            serviceResponse = new RestResponseError(500);
        }

        response.setStatus(serviceResponse.getStatus());
        JsonObject requestMirror = new JsonObject();
        requestMirror.add("version", new JsonPrimitive(version));
        requestMirror.add("target", new JsonPrimitive(target));
        wrapper.add("request", requestMirror);
        wrapper.add("response", serviceResponse.getData());
        String json = wrapper.toString();
        response.getWriter().println(json);
        baseRequest.setHandled(true);
    }

    private static JsonElement resolvePayload(Request request) throws IOException {
        if (request.getContentLength() > 0) {
            return new JsonParser().parse(request.getReader());
        } else {
            return new JsonObject();
        }
    }

    private void authenticate(Request request) throws AuthenticationException {
        String auth = request.getHeader("Authorization");
        if (auth.toUpperCase().startsWith("BASIC ")) {
            try {
                sun.misc.BASE64Decoder b64decoder = new sun.misc.BASE64Decoder();
                String credentials = new String(b64decoder.decodeBuffer(auth.substring(6)));
                String[] parts = credentials.split(":");
                String username = parts[0];
                String password = parts[1];
                if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
                    throw new AuthenticationException();
                }
            } catch (IOException e) {
                throw new AuthenticationException();
            }
        } else {
            throw new AuthenticationException();
        }
    }

    private static Map<String, String> resolvePathArgs(Request baseRequest) {
        Map<String, String> result = new HashMap<>();
        Map<String, String[]> pathParams = baseRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : pathParams.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    private static String resolveAPIVersion(Request request) {
        return request.getHeader("version") != null ? request.getHeader("version") : "1";
    }

    private static AbstractRestService resolveTargetClass(String target, String version) throws Exception {
        String[] parts = target.split("/");
        String className = PACKAGE + ".v" + version + "." + StringUtils.remove(WordUtils.capitalizeFully(parts[0], '_'), "_") + "RestService";
        Class theClass = Class.forName(className);
        return (AbstractRestService)theClass.newInstance();
    }
}
