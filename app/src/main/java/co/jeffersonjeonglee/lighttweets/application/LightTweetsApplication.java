package co.jeffersonjeonglee.lighttweets.application;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.TwitterSession;

import java.util.HashMap;

import co.jeffersonjeonglee.lighttweets.ApplicationController;
import co.jeffersonjeonglee.lighttweets.shared.Dimensions;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;

/**
 * Created by Jefferson on 1/14/16.
 */
public class LightTweetsApplication extends Application {

    private static String LOG_TAG = "LightTweetsApplication";
    private static HashMap<String, DependencyBaseInterface> controllerHashMap = new HashMap<>();
    private ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        Dimensions.initialize(getResources());
        initControllers();
    }

    private void initControllers() {
        applicationController = new ApplicationController(this);
        controllerHashMap.put(SharedConstants.APPLICATION_CONTROLLER, applicationController);
    }

    public static DependencyBaseInterface getControllerAtKey(String key) {
        if (controllerHashMap.get(key) != null) {
            return controllerHashMap.get(key);
        } else {
            Log.e(LOG_TAG, "controller not found");
            return null;
        }
    }


}
