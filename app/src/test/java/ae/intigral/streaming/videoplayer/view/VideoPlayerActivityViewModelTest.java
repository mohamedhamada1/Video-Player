package ae.intigral.streaming.videoplayer.view;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ae.intigral.streaming.videoplayer.data.remote.VideoPlayerDataModel;
import ae.intigral.streaming.videoplayer.models.LineupsContainer;
import ae.intigral.streaming.videoplayer.views.player.VideoPlayerActivityViewModel;
import io.reactivex.Observable;
import sa.waqood.networkmodule.Resource;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VideoPlayerActivityViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    // target test class
    VideoPlayerActivityViewModel playerActivityViewModel;
    //Mock this class
    @Mock
    VideoPlayerDataModel videoPlayerDataModel;

    @Before
    public void setup() {
        // mock any other dependency
        playerActivityViewModel = new VideoPlayerActivityViewModel(videoPlayerDataModel);

    }

    @Test
    public void getLineupsSuccess() {
        playerActivityViewModel.getLineups();
        verify(videoPlayerDataModel).getLineups();
    }

}
