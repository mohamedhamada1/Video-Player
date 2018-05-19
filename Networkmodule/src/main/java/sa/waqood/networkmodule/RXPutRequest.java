package sa.waqood.networkmodule;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 * <p>
 * RXPutRequest used to handle put requests
 */

 public class RXPutRequest {
    private Rx2ANRequest.PutRequestBuilder putRequestBuilder;
    private int requestID;

    /**
     * @param requestUrl
     */
    protected RXPutRequest(String requestUrl) {
        putRequestBuilder = Rx2AndroidNetworking.put(requestUrl);

    }

    /**
     * @param requestUrl
     * @param header
     */
    protected RXPutRequest(String requestUrl, HashMap<String, String> header) {
        putRequestBuilder = Rx2AndroidNetworking.put(requestUrl);
        putRequestBuilder.addHeaders(header);
    }

    /**
     * @param requestUrl
     * @param header
     * @param contentType
     */
    protected RXPutRequest(String requestUrl, HashMap<String, String> header, String contentType) {
        putRequestBuilder = Rx2AndroidNetworking.put(requestUrl);
        putRequestBuilder.addHeaders(header);
        putRequestBuilder.setContentType(contentType);

    }

    /**
     * @param header
     * @return
     */
    public RXPutRequest addHeader(HashMap<Object, Object> header) {
        putRequestBuilder.addHeaders(header);
        return this;

    }

    /**
     * @param header
     * @return
     */
    public RXPutRequest addHeader(Map<String, String> header) {
        putRequestBuilder.addHeaders(header);
        return this;

    }

    /**
     * @param object
     * @return
     */
    public RXPutRequest addApplicationJsonBody(Object object) {
        putRequestBuilder.addApplicationJsonBody(object);
        return this;

    }

    /**
     * @param jsonObject
     * @return
     */
    public RXPutRequest addJSONObjectBody(JSONObject jsonObject) {
        putRequestBuilder.addJSONObjectBody(jsonObject);
        return this;

    }

    /**
     * @param object
     * @return
     */
    public RXPutRequest addBodyParameter(Object object) {
        putRequestBuilder.addBodyParameter(object);
        return this;

    }

    /**
     * @param key
     * @param value
     * @return
     */
    public RXPutRequest addPathParameter(String key, String value) {
        putRequestBuilder.addPathParameter(key, value);
        return this;

    }

    /**
     * @param object
     * @return
     */
    public RXPutRequest addPathParameter(Object object) {
        putRequestBuilder.addPathParameter(object);
        return this;

    }

    /**
     * @param pathBody
     * @return
     */
    public RXPutRequest addPathParameter(Map<String, String> pathBody) {
        putRequestBuilder.addPathParameter(pathBody);
        return this;

    }

    /**
     * @param object
     * @return
     */
    public RXPutRequest addQueryParameter(Object object) {
        putRequestBuilder.addQueryParameter(object);
        return this;

    }

    /**
     * @param pathBody
     * @return
     */
    public RXPutRequest addQueryParameter(Map<String, String> pathBody) {
        putRequestBuilder.addQueryParameter(pathBody);
        return this;

    }

    /**
     * @param key
     * @param value
     * @return
     */
    public RXPutRequest addQueryParameter(String key, String value) {
        putRequestBuilder.addQueryParameter(key, value);
        return this;

    }

    /**
     * @param requestID
     * @return
     */
    public RXPutRequest setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }
    public RXPutRequest setContentType(String contentType) {
        putRequestBuilder.setContentType(contentType);
        return this;
    }
    public RequestParser build() {
        return RequestParser.build(putRequestBuilder.build());
    }
}
