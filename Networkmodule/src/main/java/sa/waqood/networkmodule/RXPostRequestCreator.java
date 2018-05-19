package sa.waqood.networkmodule;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 */

 public class RXPostRequestCreator {
    Rx2ANRequest.PostRequestBuilder postRequestBuilder;
    private int requestID;

    protected RXPostRequestCreator(String requestUrl) {
        postRequestBuilder = Rx2AndroidNetworking.post(requestUrl);

    }

    protected RXPostRequestCreator(String requestUrl, HashMap<String, String> header) {
        postRequestBuilder = Rx2AndroidNetworking.post(requestUrl);
        postRequestBuilder.addHeaders(header);
    }

    protected RXPostRequestCreator(String requestUrl, HashMap<String, String> header, String contentType) {
        postRequestBuilder = Rx2AndroidNetworking.post(requestUrl);
        postRequestBuilder.addHeaders(header);
        postRequestBuilder.setContentType(contentType);
    }

    public RXPostRequestCreator addHeader(Map<String, String> header) {
        postRequestBuilder.addHeaders(header);
        return this;

    }

    public RXPostRequestCreator addApplicationJsonBody(Object object) {
        postRequestBuilder.addApplicationJsonBody(object);
        return this;

    }

    public RXPostRequestCreator addJSONObjectBody(JSONObject jsonObject) {
        postRequestBuilder.addJSONObjectBody(jsonObject);
        return this;

    }

    public RXPostRequestCreator addBodyParameter(Object object) {
        postRequestBuilder.addBodyParameter(object);

        return this;

    }

    public RXPostRequestCreator addPathParameter(String key, String value) {
        postRequestBuilder.addPathParameter(key, value);
        return this;

    }

    public RXPostRequestCreator addPathParameter(Object object) {
        postRequestBuilder.addPathParameter(object);
        return this;

    }

    public RXPostRequestCreator addPathParameter(Map<String, String> pathBody) {
        postRequestBuilder.addPathParameter(pathBody);
        return this;

    }

    public RXPostRequestCreator addQueryParameter(String key, String value) {
        postRequestBuilder.addQueryParameter(key, value);
        return this;

    }

    public RXPostRequestCreator addQueryParameter(Object object) {
        postRequestBuilder.addQueryParameter(object);
        return this;

    }

    public RXPostRequestCreator addQueryParameter(Map<String, String> pathBody) {
        postRequestBuilder.addQueryParameter(pathBody);
        return this;

    }

    public RXPostRequestCreator setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }

    public RXPostRequestCreator setContentType(String contentType) {
        postRequestBuilder.setContentType(contentType);
        return this;
    }

    public RequestParser build() {
        return RequestParser.build(postRequestBuilder.build());
    }
}
