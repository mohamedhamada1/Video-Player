package sa.waqood.networkmodule;

import com.androidnetworking.error.ANError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohamed.Shaaban on 1/18/2018.
 * this class used to parse throwable back from server on Resource class to get to error message from body
 */
public class ErrorChecker {

    public static String getErrorMsg(Throwable throwable) {
        if (throwable == null) return null;
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            String errorBody = anError.getErrorBody();
            if (errorBody == null) {
                return serverNotReachableError(anError);
            } else {
                return handleServerError(errorBody);
            }

        } else {
            return throwable.getMessage();
        }


    }

    private static String handleServerError(String errorBody) {
        try {
            JSONObject errorBodyJson = new JSONObject(errorBody);
            if (errorBodyJson.has("message")) {
                JSONObject errorMsgJson = errorBodyJson.getJSONArray("message").getJSONObject(0);
                String messageKey = errorMsgJson.getString("messageKey");
                if (messageKey.contentEquals("unknownError")) {
                    return null;
                }
            }

        } catch (JSONException e) {
            return null;

        }
        return null;
    }

    private static String serverNotReachableError(ANError anError) {
        if (anError == null) {

            return null;
        }
        String errorMsg = null;
        if (anError.getCause() != null) {
            errorMsg = anError.getCause().toString();
        }
        if (errorMsg == null)
            errorMsg = anError.getMessage();
        return errorMsg;
    }


}
