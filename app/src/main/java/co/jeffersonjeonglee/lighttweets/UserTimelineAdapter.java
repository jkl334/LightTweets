package co.jeffersonjeonglee.lighttweets;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;

import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import co.jeffersonjeonglee.lighttweets.ui.TweetView;

/**
 * Created by Jefferson on 1/18/16.
 */
public class UserTimelineAdapter extends BaseAdapter {

    private ApplicationController applicationController;
    private ArrayList<Tweet> tweets;
    Bitmap bitmap;

    public UserTimelineAdapter(ArrayList<Tweet> tweets) {
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        this.tweets = tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TweetView tweetView;
        if (convertView == null) {
            tweetView = new TweetView(parent.getContext());
        } else {
            tweetView = (TweetView) convertView;
        }
        final Tweet tweet = tweets.get(position);
        tweetView.setInfo(tweet.user.name, "@" + tweet.user.screenName + " • " + tweet.user.createdAt, tweet.text);

        //  this is not necessary to do each time since it will always be the same user's
        // profile since it is their timeline, so I will download it once and keep reusing
       /* ProfilePictureRequest profilePictureRequest = new ProfilePictureRequest(tweet.user.profileImageUrl, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        Bitmap bm = (Bitmap) o;
                        bitmap = bm;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        */
        bitmap = applicationController.getUserProfileBitmap();
        tweetView.setProfilePicture(bitmap);

        return tweetView;
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public long getItemId(int position) {
        return tweets.get(position).getId();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
    }
}
