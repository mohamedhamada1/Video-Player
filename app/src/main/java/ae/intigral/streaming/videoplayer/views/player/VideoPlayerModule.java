package ae.intigral.streaming.videoplayer.views.player;

import android.arch.lifecycle.ViewModelProvider;

import ae.intigral.streaming.videoplayer.di.ViewModelProviderFactory;
import dagger.Module;
import dagger.Provides;

/**
 * class used to provide object for module
 * so as dependency injection[dagger2] I should define objects in one place
 */

@Module
public class VideoPlayerModule {

    @Provides
    ViewModelProvider.Factory provideVideoPlayerActivityViewModelFactory(VideoPlayerActivityViewModel videoPlayerActivityViewModel) {
        return new ViewModelProviderFactory<>(videoPlayerActivityViewModel);
    }

    @Provides
    PlayerAdapter providePlayerAdapter(){
        return new PlayerAdapter();
    }

}
