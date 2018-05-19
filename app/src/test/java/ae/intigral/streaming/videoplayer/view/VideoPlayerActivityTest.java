package ae.intigral.streaming.videoplayer.view;

import android.app.Activity;
import android.content.Intent;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import ae.intigral.streaming.videoplayer.BuildConfig;
import ae.intigral.streaming.videoplayer.R;
import ae.intigral.streaming.videoplayer.views.player.VideoPlayerActivity;
import ae.intigral.streaming.videoplayer.views.player.VideoPlayerActivityViewModel;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * using roboelectri for medium test and integration
 * Roboelectric is very useful to test against different configuration and to test UI in JVM
 * you can test lifecycle of activity
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class VideoPlayerActivityTest {


    private VideoPlayerActivity activity;
    //  @Mock
    //VideoPlayerActivityViewModel videoPlayerActivityViewModel;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(VideoPlayerActivity.class);
    }

    /**
     * test to check OS choose correct layout in case phone
     */
    @Test
    public void testinitViewsAgaintsPhone() {
        activity.initViews();
        // in case phone , should RVLeft is null and RVRight not null
        assertEquals(activity.findViewById(R.id.RVLeft), null);
        assertThat("right recycler view should not be null", activity.findViewById(R.id.RVRight), is(CoreMatchers.notNullValue()));
        // in case phone should TVAway and TVHome is not null
        assertThat("TVAway button should not be null", activity.findViewById(R.id.TVAway), is(CoreMatchers.notNullValue()));
        assertThat("TVHome should not be null", activity.findViewById(R.id.TVHome), is(CoreMatchers.notNullValue()));
    }


    /**
     * test to check OS choose correct layout in case tablet
     * against tablet 7 in
     */
    @Test
    @Config(qualifiers = "sw600dp")
    public void testinitViewsAgaintsTablet7in() {
        activity.initViews();
        // in case tablet , should RVLeft is not null and RVRight is not null
        assertThat("RVLeft should not null", activity.findViewById(R.id.RVLeft), is(CoreMatchers.notNullValue()));
        assertThat("RVRight shouldn't null", activity.findViewById(R.id.RVRight), is(CoreMatchers.notNullValue()));
        // in case phone should TVAway and TVHome is  null
        assertThat("TVAways should null", activity.findViewById(R.id.TVAway), is(CoreMatchers.nullValue()));
        assertThat("TVHome should null", activity.findViewById(R.id.TVHome), is(CoreMatchers.nullValue()));

    }

    /**
     * test to check OS choose correct layout in case tablet
     * against tablet
     */
    @Test
    @Config(qualifiers = "sw720dp")
    public void testinitViewsAgaintsTablet10in() {
        activity.initViews();
        // in case tablet , should RVLeft is not null and RVRight is not null
        assertThat("Next activity should not started", activity.findViewById(R.id.RVLeft), is(CoreMatchers.notNullValue()));
        assertThat("Next activity should not started", activity.findViewById(R.id.RVRight), is(CoreMatchers.notNullValue()));
        // in case phone should TVAway and TVHome is  null
        assertThat("Next activity should not started", activity.findViewById(R.id.TVAway), is(CoreMatchers.nullValue()));
        assertThat("Next activity should not started", activity.findViewById(R.id.TVHome), is(CoreMatchers.nullValue()));

    }


    /**
     * test against SDK higher than 23 to check onStart will start player
     */
    @Test
    @Config(sdk = 24)
    public void testStartVideoPlayerActivityWithIntentToStartPlayingFromSpecificPositionAgainstSDK24() {
        Intent intent = new Intent();
        intent.putExtra("start_video_player_pos", 707431L);
        VideoPlayerActivity activity = Robolectric.buildActivity(VideoPlayerActivity.class, intent).create().start().get();
        assertEquals(707431, activity.getPlayer().getCurrentPosition());
    }

    /**
     * test against SDK less than 23 to check onResume will start player
     */
    @Test
    @Config(sdk = 22)
    public void testStartVideoPlayerActivityWithIntentToStartPlayingFromSpecificPositionAgainstSDK22() {
        Intent intent = new Intent();
        intent.putExtra("start_video_player_pos", 707431L);
        VideoPlayerActivity activity = Robolectric.buildActivity(VideoPlayerActivity.class, intent).create().resume().get();
        assertEquals(707431, activity.getPlayer().getCurrentPosition());
    }


    /**
     * test against SDK higher than 23 to check player is null after release
     */
    @Test
    @Config(sdk = 24)
    public void testStopVideoPlayerActivity() {
        VideoPlayerActivity activity = Robolectric.buildActivity(VideoPlayerActivity.class).create().get();
        activity.releasePlayer();
        assertThat("video player should null after release", activity.getPlayer(), is(CoreMatchers.nullValue()));

    }


}
