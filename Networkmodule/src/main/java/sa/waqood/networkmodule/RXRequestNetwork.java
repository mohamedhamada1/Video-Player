package sa.waqood.networkmodule;

import java.util.HashMap;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 */

public class RXRequestNetwork {


    public static RXGetRequest get(String requestUrl, HashMap<String, String> header) {
        return new RXGetRequest(requestUrl, header);

    }

    public static RXGetRequest get(String requestUrl) {
        return new RXGetRequest(requestUrl);

    }


    public static RXPostRequestCreator post(String requestUrl) {
        return new RXPostRequestCreator(requestUrl);

    }

    public static RXPostRequestCreator post(String requestUrl, HashMap<String, String> header) {
        return new RXPostRequestCreator(requestUrl, header);

    }

    public static RXPostRequestCreator post(String requestUrl, HashMap<String, String> header, String contentType) {
        return new RXPostRequestCreator(requestUrl, header, contentType);

    }

    public static RXPutRequest put(String requestUrl) {
        return new RXPutRequest(requestUrl);

    }

    public static RXPutRequest put(String requestUrl, HashMap<String, String> header) {
        return new RXPutRequest(requestUrl, header);

    }

    public static RXPutRequest put(String requestUrl, HashMap<String, String> header, String contentType) {
        return new RXPutRequest(requestUrl, header, contentType);

    }


    public static RXDeleteRequest delete(String requestUrl) {
        return new RXDeleteRequest(requestUrl);

    }

    public static RXDeleteRequest delete(String requestUrl, HashMap<String, String> header) {
        return new RXDeleteRequest(requestUrl, header);

    }

    public static RXDeleteRequest delete(String requestUrl, HashMap<String, String> header, String contentType) {
        return new RXDeleteRequest(requestUrl, header, contentType);

    }


}
