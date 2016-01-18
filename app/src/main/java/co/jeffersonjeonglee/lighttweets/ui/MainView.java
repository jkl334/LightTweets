package co.jeffersonjeonglee.lighttweets.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;

import co.jeffersonjeonglee.lighttweets.UserTimelineAdapter;
import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import co.jeffersonjeonglee.lighttweets.util.LayoutUtil;

/**
 * Created by Jefferson on 1/15/16.
 */
public class MainView extends FrameLayout {

    private TwitterApiClient twitterApiClient;
    private UserTimelineAdapter adapter;

    private ApplicationController applicationController;
    private ApplicationController.ApplicationControllerListener applicationControllerListener = new ApplicationController.ApplicationControllerListener() {
        @Override
        public void onFeedRefreshRequested() {
            applicationController.requestUserFeed();
        }

        @Override
        public void onUserTimelineFetchSuccess(ArrayList<Tweet> tweets) {
            if (adapter == null) {
                adapter = new UserTimelineAdapter(tweets);
            } else {
                adapter.setTweets(tweets);
            }
            listView.setAdapter(adapter);
        }
    };

    private TopBar topBar;
    private LineShadow topBarShadow;
    private ListView listView;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        topBar = new TopBar(context);
        addView(topBar);
        topBarShadow = new LineShadow(context);
        addView(topBarShadow);

        //Twitter user feed (using twitter kit)
        listView = new ListView(context);
        //final UserTimeline userTimeline = new UserTimeline.Builder()
        //        .screenName(applicationController.getTwitterSession().getUserName())
        //        .build();
        //final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(context)
        //        .setTimeline(userTimeline)
        //        .build();
        //listView.setAdapter(adapter);
        addView(listView);

    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LayoutUtil.defaultLayout(topBar, 0, 0);
        LayoutUtil.defaultLayout(topBarShadow, 0, topBar.getMeasuredHeight());

        listView.layout(0, topBar.getMeasuredHeight(), right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        applicationController.addListener(applicationControllerListener);
        applicationController.requestUserFeed();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        applicationController.removeListener(applicationControllerListener);
    }
}
