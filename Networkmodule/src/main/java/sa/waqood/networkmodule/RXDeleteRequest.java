package sa.waqood.networkmodule;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 * <p>
 * class to handle Delete request
 */

 public class RXDeleteRequest {
    private Rx2ANRequest.DeleteRequestBuilder deleteRequestBuilder;
    private int requestID;

    /**
     * @param requestUrl only send request url
     */
    protected RXDeleteRequest(String requestUrl) {
        deleteRequestBuilder = Rx2AndroidNetworking.delete(requestUrl);
    }

    /**
     * @param requestUrl
     * @param header
     */
    protected RXDeleteRequest(String requestUrl, HashMap<String, String> header) {
        deleteRequestBuilder = Rx2AndroidNetworking.delete(requestUrl);
        deleteRequestBuilder.addHeaders(header);
    }

    /**
     * @param requestUrl
     * @param header
     * @param contentType
     */
    protected RXDeleteRequest(String requestUrl, HashMap<String, String> header, String contentType) {
        deleteRequestBuilder = Rx2AndroidNetworking.delete(requestUrl);
        deleteRequestBuilder.addHeaders(header);
        deleteRequestBuilder.setContentType(contentType);
    }

    /**
     * @param header
     * @return
     */
    public RXDeleteRequest addHeader(HashMap<Object, Object> header) {
        deleteRequestBuilder.addHeaders(header);
        return this;
    }

    /**
     * @param header
     * @return
     */
    public RXDeleteRequest addHeader(Map<String, String> header) {
        deleteRequestBuilder.addHeaders(header);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXDeleteRequest addApplicationJsonBody(Object object) {
        deleteRequestBuilder.addApplicationJsonBody(object);
        return this;
    }

    /**
     * @param jsonObject
     * @return
     */
    public RXDeleteRequest addJSONObjectBody(JSONObject jsonObject) {
        deleteRequestBuilder.addJSONObjectBody(jsonObject);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXDeleteRequest addBodyParameter(Object object) {
        deleteRequestBuilder.addBodyParameter(object);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public RXDeleteRequest addPathParameter(String key, String value) {
        deleteRequestBuilder.addPathParameter(key, value);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXDeleteRequest addPathParameter(Object object) {
        deleteRequestBuilder.addPathParameter(object);
        return this;
    }

    /**
     * @param pathBody
     * @return
     */
    public RXDeleteRequest addPathParameter(Map<String, String> pathBody) {
        deleteRequestBuilder.addPathParameter(pathBody);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXDeleteRequest addQueryParameter(Object object) {
        deleteRequestBuilder.addQueryParameter(object);
        return this;
    }

    /**
     * @param pathBody
     * @return
     */
    public RXDeleteRequest addQueryParameter(Map<String, String> pathBody) {
        deleteRequestBuilder.addQueryParameter(pathBody);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     *
     */
    public RXDeleteRequest addQueryParameter(String key, String value) {
        deleteRequestBuilder.addQueryParameter(key, value);
        return this;
    }

    /**
     * @param requestID
     * @return
     */
    public RXDeleteRequest setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }
    public RXDeleteRequest setContentType(String contentType) {
        deleteRequestBuilder.setContentType(contentType);
        return this;
    }


    public RequestParser build() {
        return RequestParser.build(deleteRequestBuilder.build());
    }

}
