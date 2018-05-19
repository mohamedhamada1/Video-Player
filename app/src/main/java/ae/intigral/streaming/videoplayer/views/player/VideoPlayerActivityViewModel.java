package ae.intigral.streaming.videoplayer.views.player;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.json.JSONObject;

import javax.inject.Inject;

import ae.intigral.streaming.videoplayer.data.remote.VideoPlayerDataModel;
import ae.intigral.streaming.videoplayer.models.Data;
import ae.intigral.streaming.videoplayer.models.Lineups;
import ae.intigral.streaming.videoplayer.models.LineupsContainer;
import sa.waqood.networkmodule.Resource;

/**
 * this represent View model in MVVM pattern
 * I extend ViewModel class from android architecture component to solve  change configuration problems
 */
public class VideoPlayerActivityViewModel extends ViewModel {

    private boolean shouldAutoPlay;
    private long currentPlayingPosition; //to start player from last position before pause
    private VideoPlayerDataModel videoPlayerDataModel; // data model to handle backend
    private boolean playerOverLayIsVisiable = false; //to check if overlay was open , so after change config i can keep open
    private boolean homeViewIsCurrentOpen = true;  //to identify home or away list was open before change config ,so I can keep the same open
    private Data data;


    @Inject
    public VideoPlayerActivityViewModel(VideoPlayerDataModel videoPlayerDataModel) {
        this.videoPlayerDataModel = videoPlayerDataModel;
    }

    /**
     * get lineups
     * @return LiveData<Resource<LineupsContainer>>
     */
    public LiveData<Resource<LineupsContainer>> getLineups() {
        return videoPlayerDataModel.getLineups();
    }

    public boolean isShouldAutoPlay() {
        return shouldAutoPlay;
    }

    public void setShouldAutoPlay(boolean shouldAutoPlay) {
        this.shouldAutoPlay = shouldAutoPlay;
    }

    public long getCurrentPlayingPosition() {
        return currentPlayingPosition;
    }

    public void setCurrentPlayingPosition(long currentPlayingPosition) {
        this.currentPlayingPosition = currentPlayingPosition;
    }

    public boolean isPlayerOverLayIsVisiable() {
        return playerOverLayIsVisiable;
    }

    public void setPlayerOverLayIsVisiable(boolean playerOverLayIsVisiable) {
        this.playerOverLayIsVisiable = playerOverLayIsVisiable;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isHomeViewIsCurrentOpen() {
        return homeViewIsCurrentOpen;
    }

    public void setHomeViewIsCurrentOpen(boolean homeViewIsCurrentOpen) {
        this.homeViewIsCurrentOpen = homeViewIsCurrentOpen;
    }
}
