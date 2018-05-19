package ae.intigral.streaming.videoplayer.views.player;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import ae.intigral.streaming.videoplayer.BR;
import ae.intigral.streaming.videoplayer.R;
import ae.intigral.streaming.videoplayer.callback.RetryCallback;
import ae.intigral.streaming.videoplayer.databinding.ActivityVideoPlayerBinding;
import ae.intigral.streaming.videoplayer.views.base.BaseActivity;
import sa.waqood.networkmodule.enums.Status;

/**
 * to support multi screen size should use different qualifier
 * 320dp,480dp,600dp,720dp
 * I didn't use legacy size qualifiers(small,large,xlagre)because this is deprecated.
 * https://developer.android.com/training/multiscreen/screensizes
 *  support receive intent with specific start position to play in ms PARAM_START_VIDEO_PLAYER_POS
 *  support change configuration to start from same position after change configuration
 */

public class VideoPlayerActivity extends BaseActivity<ActivityVideoPlayerBinding, VideoPlayerActivityViewModel> {

    private SimpleExoPlayer player;
    private VideoPlayerActivityViewModel videoPlayerActivityViewModel;
    private ActivityVideoPlayerBinding activityVideoPlayerBinding;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private RecyclerView RVLeft, RVRight;
    private RelativeLayout LLPlayerOverLay;
    private TextView TVHome, TVAway;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    PlayerAdapter rightPlayerAdapter, leftPlayerAdapter;

    private final static String PARAM_START_VIDEO_PLAYER_POS = "start_video_player_pos";

    // to create intent for player activity and startPosition
    public static Intent getStartIntent(Context context, int startVideoPlayerPosition) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(PARAM_START_VIDEO_PLAYER_POS, startVideoPlayerPosition);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init view binding
        activityVideoPlayerBinding = getViewDataBinding();

        // check if this method called after change configuration
        // if true should resume player from pause point
        // if false check if there is start video position extra to play from this start position
        if (videoPlayerActivityViewModel.getCurrentPlayingPosition() == 0) {
            if (getIntent().hasExtra(PARAM_START_VIDEO_PLAYER_POS))
                videoPlayerActivityViewModel.setCurrentPlayingPosition(getIntent().getExtras().getLong(PARAM_START_VIDEO_PLAYER_POS, 0));
        }

        if (!isNetworkConnected()) {
            activityVideoPlayerBinding.setNoInternet(true);
            activityVideoPlayerBinding.setCallback(new RetryCallback() {
                @Override
                public void retry() {
                    if (isNetworkConnected()) {
                        activityVideoPlayerBinding.setNoInternet(false);
                        initViews();
                        initializePlayer();
                    }
                }
            });
            return;
        }
        initViews();

    }

    //--------------------- init views------------------------------------------------------------------------------------------------//
    public void initViews() {
        RVLeft = findViewById(R.id.RVLeft);
        RVRight = findViewById(R.id.RVRight);

        RVRight.setLayoutManager(new LinearLayoutManager(this));
        if (RVLeft != null)
            RVLeft.setLayoutManager(new LinearLayoutManager(this));
        TVHome = findViewById(R.id.TVHome);
        TVAway = findViewById(R.id.TVAway);
        initVideoPlayer();
        handleOverLayPlayersView();
    }

    private void initVideoPlayer() {
        videoPlayerActivityViewModel.setShouldAutoPlay(true);
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
    }


    //-----------------------------------------------------------------------------------------------------------------------//

    // this method guarantee that even after change configuration always I will have same View model object
    @Override
    public VideoPlayerActivityViewModel getViewModel() {
        videoPlayerActivityViewModel = ViewModelProviders.of(this, mViewModelFactory).get(VideoPlayerActivityViewModel.class);
        return videoPlayerActivityViewModel;
    }

    //-------------------------------player control-----------------------------------------------------------//
    public void releasePlayer() {
        if (player != null) {
            videoPlayerActivityViewModel.setShouldAutoPlay(player.getPlayWhenReady());
            videoPlayerActivityViewModel.setCurrentPlayingPosition(player.getCurrentPosition());
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    private void initializePlayer() {

        activityVideoPlayerBinding.playerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        activityVideoPlayerBinding.playerView.setPlayer(player);
        player.setPlayWhenReady(videoPlayerActivityViewModel.isShouldAutoPlay());
        MediaSource mediaSource = new HlsMediaSource(Uri.parse("http://streaming.akamai.dawriplus.com/i/DPS5SPLHILXFTH120418_1@515532/master.m3u8?hdnea=exp=1526901437~acl=/*~hmac=e3ef79a8789bd3f6c304331d1f0d837e46ba28b28e07953c24af1d65c1ba0611&hdcore=2.11.2"),
                mediaDataSourceFactory, null, null);

        player.prepare(mediaSource);
        player.seekTo(videoPlayerActivityViewModel.getCurrentPlayingPosition());
        setVideoPlayerListener();

    }
    //---------------------------------------------------------------------------------------------------------//

    //----------------------- handle recycler views ----------------------------------------------------------------//

    /**
     * depend on screen size OS is gonna select suitable layout
     * in case phone
     * - RVLeft is null
     * -RVRight will has reference to recycler view
     * in case tablet
     * - RVLeft and RVRight will has reference to recycler views
     */
    private void initRecyclerViews() {
        if (RVRight != null) {
            initRightRecyclerView();
        }
        if (RVLeft != null) {
            initLeftRecyclerView();
        }
    }

    /**
     * init right recycler view
     */
    private void initRightRecyclerView() {
        if (RVRight.getAdapter() == null)
            RVRight.setAdapter(rightPlayerAdapter);
        // check what was last user selection Home or Away team
        if (videoPlayerActivityViewModel.isHomeViewIsCurrentOpen())
            rightPlayerAdapter.replace(videoPlayerActivityViewModel.getData().getHomeTeam().getPlayers());
        else
            rightPlayerAdapter.replace(videoPlayerActivityViewModel.getData().getAwayTeam().getPlayers());
        // in case phone I should handle home and away btn click actions
        handleHomeAwayBtn();
    }

    /**
     * init left recycler view
     */
    private void initLeftRecyclerView() {
        if (RVLeft.getAdapter() == null)
            RVLeft.setAdapter(leftPlayerAdapter);
        leftPlayerAdapter.replace(videoPlayerActivityViewModel.getData().getAwayTeam().getPlayers());
    }

    /**
     * handle click for home and away to swipe between them and change colors
     */
    private void handleHomeAwayBtn() {
        // make sure this is phone version layout
        // in case tablet TVHome and TVAway will be null
        if (TVHome == null || TVAway == null) return;
        // set selected button correctly
        changeHomeAwaySelector();
        // handle swipe between home and away
        TVHome.setOnClickListener(view -> {
            rightPlayerAdapter.replace(videoPlayerActivityViewModel.getData().getHomeTeam().getPlayers());
            videoPlayerActivityViewModel.setHomeViewIsCurrentOpen(true);
            changeHomeAwaySelector();

        });
        TVAway.setOnClickListener(view -> {
            rightPlayerAdapter.replace(videoPlayerActivityViewModel.getData().getAwayTeam().getPlayers());
            videoPlayerActivityViewModel.setHomeViewIsCurrentOpen(false);
            changeHomeAwaySelector();
        });

    }

    private void changeHomeAwaySelector() {
        if (videoPlayerActivityViewModel.isHomeViewIsCurrentOpen()) {
            TVHome.setTextColor(getResources().getColor(android.R.color.white));
            TVAway.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            TVAway.setTextColor(getResources().getColor(android.R.color.white));
            TVHome.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    /**
     * load lineups
     */
    private void getLineups() {
        // if data already loaded before , so we don't need to load it again we just need to init recycler views
        if (videoPlayerActivityViewModel.getData() != null) {
            // set adapters
            initRecyclerViews();
            return;
        }
        // load data , will recive live data to refer to Loading, Error, or success
        videoPlayerActivityViewModel.getLineups().observe(this, lineupsResource -> {
            if (lineupsResource.status == Status.SUCCESS) {
                activityVideoPlayerBinding.setLoading(false);
                // in case success just set recycler view
                if (lineupsResource.data != null && lineupsResource.data.getLineups() != null)
                    videoPlayerActivityViewModel.setData(lineupsResource.data.getLineups().getData());
                initRecyclerViews();
            } else if (lineupsResource.status == Status.ERROR) {
                // show error
                showSnackMessage(lineupsResource.message);
                activityVideoPlayerBinding.setLoading(false);

            } else if (lineupsResource.status == Status.LOADING) {
                activityVideoPlayerBinding.setLoading(true);

            }
        });
    }


    /**
     * handle open and close overlay events
     */
    private void handleOverLayPlayersView() {
        ImageView IVOpenClosePlayerOverlay = findViewById(R.id.IVOpenPlayerOverlay);
        LLPlayerOverLay = findViewById(R.id.LLPlayerOverLay);
        // to keep last state after change configuration
        if (videoPlayerActivityViewModel.isPlayerOverLayIsVisiable()) {
            LLPlayerOverLay.setVisibility(View.VISIBLE);
            getLineups();
        }
        // open and close overlay
        IVOpenClosePlayerOverlay.setOnClickListener(view -> {
            getLineups();
            LLPlayerOverLay.setVisibility(videoPlayerActivityViewModel.isPlayerOverLayIsVisiable() ? View.GONE : View.VISIBLE);
            videoPlayerActivityViewModel.setPlayerOverLayIsVisiable(!videoPlayerActivityViewModel.isPlayerOverLayIsVisiable());
        });

        findViewById(R.id.IVCLosePlayer).setOnClickListener(view -> finish());
    }

    private void setVideoPlayerListener() {


        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    activityVideoPlayerBinding.setLoading(true);
                } else {
                    activityVideoPlayerBinding.setLoading(false);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR._all;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_player;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }


}
