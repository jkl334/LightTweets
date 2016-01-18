package co.jeffersonjeonglee.lighttweets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import io.fabric.sdk.android.Fabric;

public class SignInActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    private SharedPreferences prefs;

    private ApplicationController.ApplicationControllerListener applicationControllerListener = new ApplicationController.ApplicationControllerListener() {
        @Override
        public void onSignInButtonPressed() {
            loginButton.performClick();
        }
    };

    private ApplicationController applicationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(SharedConstants.TWITTER_KEY, SharedConstants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_sign_in);

        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        applicationController.addListener(applicationControllerListener);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                applicationController.setTwitterSession(session);

                String msg = getResources().getString(R.string.sign_in_success);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
                String msg = getResources().getString(R.string.sign_in_failure);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        applicationController.addListener(applicationControllerListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        applicationController.removeListener(applicationControllerListener);
    }
}
