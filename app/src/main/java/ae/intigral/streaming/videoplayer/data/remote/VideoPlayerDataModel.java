package ae.intigral.streaming.videoplayer.data.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import ae.intigral.streaming.videoplayer.models.LineupsContainer;
import ae.intigral.streaming.videoplayer.views.ApiEndPoint;
import ae.intigral.streaming.videoplayer.models.Lineups;
import sa.waqood.networkmodule.AppExecutors;
import sa.waqood.networkmodule.NetworkBoundResource;
import sa.waqood.networkmodule.RXRequestNetwork;
import sa.waqood.networkmodule.Resource;

/**
 * data layer for video player
 * manage local and external DB
 */
public class VideoPlayerDataModel {


    private final AppExecutors appExecutors;

    @Inject
    public VideoPlayerDataModel(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<LineupsContainer>> getLineups() {

        /*
        networkBoundResource is generic class I can handle with it local and external DB
         depend on what you will return
        */
        return new NetworkBoundResource<LineupsContainer, LineupsContainer>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull LineupsContainer item) {
                // save it on local DB
                // I should using Room or any Orm Lib
                // I expected to return Observable or live data (this is default response from Room )
            }

            @Override
            protected boolean shouldFetch(@Nullable LineupsContainer data) {
                // in case I wanna depend on local data
                return true;
            }

            @Override
            protected LiveData<LineupsContainer> loadFromDb() {
                // load from DB , it should use this method if you want to have sync between local and server data
                // in some times I need to handle offline or cache data , or use local data mean while updated data loading
                return null;
            }

            @NonNull
            @Override
            protected LiveData<Resource<?>> createCall() {
                // this is call using RXJava, it will return live data response
                return fetchFromNetwork();
            }

            @NonNull
            @Override
            protected int getRequestId() {
                // attach request id with call
                return 101;
            }
        }.asLiveData();
    }

    public LiveData<Resource<?>> fetchFromNetwork() {
        return RXRequestNetwork.get(ApiEndPoint.PLAYERS_URL)
                .build()
                .asObjectObservable(LineupsContainer.class);
    }

}
