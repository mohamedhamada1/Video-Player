package sa.waqood.networkmodule;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 */

/**
 * RXGetRequest used to handle get requests
 */
public class RXGetRequest<ResultType> {
    private Rx2ANRequest.GetRequestBuilder getRequestBuilder;
    private int requestID;


    /**
     * @param requestUrl
     */
    protected RXGetRequest(String requestUrl) {
        getRequestBuilder = Rx2AndroidNetworking.get(requestUrl);

    }

    /**
     * @param requestUrl
     * @param header
     */
    protected RXGetRequest(String requestUrl, HashMap<String, String> header) {
        getRequestBuilder = Rx2AndroidNetworking.get(requestUrl);
        getRequestBuilder.addHeaders(header);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public RXGetRequest addPathParameter(String key, String value) {
        getRequestBuilder.addPathParameter(key, value);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXGetRequest addPathParameter(Object object) {
        getRequestBuilder.addPathParameter(object);
        return this;
    }

    /**
     * @param pathBody
     * @return
     */
    public RXGetRequest addPathParameter(Map<String, String> pathBody) {
        getRequestBuilder.addPathParameter(pathBody);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public RXGetRequest addQueryParameter(String key, String value) {
        getRequestBuilder.addQueryParameter(key, value);
        return this;
    }

    /**
     * @param object
     * @return
     */
    public RXGetRequest addQueryParameter(Object object) {
        getRequestBuilder.addQueryParameter(object);
        return this;
    }

    /**
     * @param pathBody
     * @return
     */
    public RXGetRequest addQueryParameter(Map<String, String> pathBody) {
        getRequestBuilder.addQueryParameter(pathBody);
        return this;
    }

    /**
     * @param header
     * @return
     */
    public RXGetRequest addHeader(Map<String, String> header) {
        getRequestBuilder.addHeaders(header);
        return this;
    }

    /**
     * @param requestID
     * @return
     */
    public RXGetRequest setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }

    public RequestParser build() {
        return RequestParser.build(getRequestBuilder.build()).setRequestTag(requestID);
    }

}
