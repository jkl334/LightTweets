package co.jeffersonjeonglee.lighttweets;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterSession;

import java.util.LinkedHashSet;
import java.util.Set;

import co.jeffersonjeonglee.lighttweets.application.DependencyBaseInterface;

/**
 * Created by Jefferson on 1/14/16.
 */
public class ApplicationController implements DependencyBaseInterface {

    private static final String LOG_TAG = "ApplicationController";
    private Set<ApplicationControllerListener> listeners;
    private Context context;

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

    public TwitterSession getTwitterSession() {
        return twitterSession;
    }

    public void setTwitterSession(TwitterSession twitterSession) {
        this.twitterSession = twitterSession;
    }


}
