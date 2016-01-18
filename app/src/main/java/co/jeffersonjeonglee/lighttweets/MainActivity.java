package co.jeffersonjeonglee.lighttweets;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private ApplicationController applicationController;
    private ApplicationController.ApplicationControllerListener applicationControllerListener = new ApplicationController.ApplicationControllerListener() {
        @Override
        public void onComposeTweetRequested() {
            PackageManager pkManager = MainActivity.this.getPackageManager();
            try {
                PackageInfo pkgInfo = pkManager.getPackageInfo("com.twitter.android", 0);
                String getPkgInfo = pkgInfo.toString();
                if (getPkgInfo.equals("com.twitter.android"))   {
                    // twitter is not installed - use Twitter's ComposerActivity
                    final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                            .getActiveSession();
                    final Intent intent = new ComposerActivity.Builder(MainActivity.this)
                            .session(session)
                            .createIntent();
                    startActivity(intent);
                    Log.d("woop", "woop");
                } else {
                    // twitter is installed - use Twitter's TweetComposer
                    TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this).
                            text("Jeff is awesome!");
                    Intent intent = builder.createIntent();
                    startActivity(intent);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                // twitter is not installed - use Twitter's ComposerActivity
                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(MainActivity.this)
                        .session(session)
                        .createIntent();
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(applicationController.getTwitterSession().getAuthToken().token, applicationController.getTwitterSession().getAuthToken().secret);
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        applicationController.addListener(applicationControllerListener);
        applicationController.notifyOnFeedRefreshRequested();
    }

    @Override
    protected void onPause() {
        super.onPause();
        applicationController.removeListener(applicationControllerListener);
        applicationController.notifyOnFeedRefreshRequested();
    }
}
