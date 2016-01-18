package co.jeffersonjeonglee.lighttweets.application;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import co.jeffersonjeonglee.lighttweets.ProfilePictureRequest;

/**
 * Created by Jefferson on 1/14/16.
 */
public class ApplicationController implements DependencyBaseInterface {

    private static final String LOG_TAG = "ApplicationController";
    private Set<ApplicationControllerListener> listeners;
    private Context context;
    private ArrayList<Tweet> timeline;
    private Bitmap userProfileBitmap;

    private TwitterSession twitterSession;

    public ApplicationController(Context context) {
        this.context = context;
        listeners = new LinkedHashSet<>();
    }

    //listener methods to overwrite
    public static abstract class ApplicationControllerListener {
        public void onSignInButtonPressed() {}
        public void onComposeTweetRequested() {}
        public void onFeedRefreshRequested() {}
        public void onUserTimelineFetchSuccess(ArrayList<Tweet> tweets) {}
    }

    public void addListener(final ApplicationControllerListener listenerToRegister) {
        listeners.add(listenerToRegister);
    }

    public void removeListener(ApplicationControllerListener listenerToRemove) {
        listeners.remove(listenerToRemove);
    }

    //notifiers
    public void notifyOnSignInButtonPressed() {
        for (ApplicationControllerListener listener : listeners) {
            listener.onSignInButtonPressed();
        }
    }

    public void notifyOnComposeTweetRequested() {
        for (ApplicationControllerListener listener : listeners) {
            listener.onComposeTweetRequested();
        }
    }
    public void notifyOnFeedRefreshRequested() {
        for (ApplicationControllerListener listener : listeners) {
            listener.onFeedRefreshRequested();
        }
    }
    public void notifyOnUserTimelineFetchSuccess(ArrayList<Tweet> tweets) {
        for (ApplicationControllerListener listener : listeners) {
            listener.onUserTimelineFetchSuccess(tweets);
        }
    }

    public TwitterSession getTwitterSession() {
        return twitterSession;
    }

    public void setTwitterSession(TwitterSession twitterSession) {
        this.twitterSession = twitterSession;
    }

    public void requestUserFeed() {
        TwitterCore.getInstance().getApiClient(getTwitterSession()).getStatusesService()
                .userTimeline(null, getTwitterSession().getUserName(), 30, null, null, null, null, null, null,
                        new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                ArrayList<Tweet> tweets = new ArrayList<Tweet>();
                                for (Tweet tweet : result.data) {
                                    tweets.add(tweet);
                                    android.util.Log.d("woo", "tweet: " + tweet.text);
                                }
                                //download image from first tweet since there will only be one
                                downloadProfilePicture(tweets.get(0));
                                timeline = tweets;
                                notifyOnUserTimelineFetchSuccess(tweets);
                            }

                            @Override
                            public void failure(TwitterException exception) {

                            }
                        });
    }

    public void downloadProfilePicture(Tweet tweet) {
        ProfilePictureRequest profilePictureRequest = new ProfilePictureRequest(tweet.user.profileImageUrl, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        Bitmap bm = (Bitmap) o;
                        setUserProfileBitmap(bm);

                        notifyOnUserTimelineFetchSuccess(timeline);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        LightTweetsApplication.getRequestQueue().add(profilePictureRequest);

    }

    public Bitmap getUserProfileBitmap() {
        return userProfileBitmap;
    }

    public void setUserProfileBitmap(Bitmap userProfileBitmap) {
        this.userProfileBitmap = userProfileBitmap;
    }

}
