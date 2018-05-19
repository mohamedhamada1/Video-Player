package sa.waqood.networkmodule;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.rx2androidnetworking.Rx2ANRequest;

import io.reactivex.functions.Consumer;
import sa.waqood.networkmodule.rx.AppSchedulerProvider;
import sa.waqood.networkmodule.rx.SchedulerProvider;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 * <p>
 * class used to parse json and return response in any form
 */

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */

public class RequestParser<ResultType, RequestType> {

    private int requestID;
    private Rx2ANRequest rx2ANRequest;
    // helper class used in RX
    private static SchedulerProvider schedulerProvider;
    // live data between data layer and view layer
    private MutableLiveData<Resource<?>> result = new MutableLiveData<>();

    static {
        schedulerProvider = new AppSchedulerProvider();
    }


    private RequestParser(Rx2ANRequest rx2ANRequest) {
        this.rx2ANRequest = rx2ANRequest;
    }

    public static RequestParser build(Rx2ANRequest rx2ANRequest) {
        return new RequestParser(rx2ANRequest);

    }

    /**
     * @param objectClass want to get response in that form
     * @return live data
     */

    public LiveData<Resource<?>> asObjectObservable(Class objectClass) {

        rx2ANRequest.getObjectObservable(objectClass).subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(responseData ->
                        result.setValue(Resource.success(responseData, requestID)), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue(Resource.error(null, throwable, requestID));
                    }
                });
        return result;
    }


    /**
     * @param objectClass which you want to get response in this list form
     * @return error throwable
     * loading
     * success will get it as string
     */
    public LiveData<Resource<?>> asObjectListObservable(Class objectClass) {
        MutableLiveData<Resource<?>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        rx2ANRequest.getObjectListObservable(objectClass).subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(responseData ->
                        result.setValue(Resource.success(responseData, requestID)), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue(Resource.error(null, throwable, requestID));
                    }
                });

        return result;
    }

    /**
     * @return return live data response
     * error throwable
     * loading
     * success will get it as string
     */
    public LiveData<Resource<?>> asStringObservable() {
        MutableLiveData<Resource<?>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        rx2ANRequest.getStringObservable().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(responseData ->
                        result.setValue(Resource.success(responseData, requestID)), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue(Resource.error(null, throwable, requestID));
                    }
                });

        return result;
    }

    /**
     * @return return live data response
     * error throwable
     * loading
     * success will get it as jsonObject
     */
    public LiveData<Resource<?>> asJSONObjectObservable() {
        MutableLiveData<Resource<?>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        rx2ANRequest.getJSONObjectObservable().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(responseData ->
                        result.setValue(Resource.success(responseData, requestID)), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue(Resource.error(null, throwable, requestID));
                    }
                });

        return result;
    }

    /**
     * @return return live data response
     * error throwable
     * loading
     * success will get it as JsonArray
     */
    public LiveData<Resource<?>> asJSONArrayObservable() {
        MutableLiveData<Resource<?>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        rx2ANRequest.getJSONArrayObservable().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(responseData ->
                        result.setValue(Resource.success(responseData, requestID)), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.setValue(Resource.error(null, throwable, requestID));
                    }
                });

        return result;
    }

    /**
     * @param requestID
     */

    protected RequestParser setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }


}
