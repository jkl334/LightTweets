package co.jeffersonjeonglee.lighttweets.application;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import co.jeffersonjeonglee.lighttweets.shared.Dimensions;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;

/**
 * Created by Jefferson on 1/14/16.
 */
public class LightTweetsApplication extends Application {

    private static RequestQueue requestQueue;
    private static String LOG_TAG = "LightTweetsApplication";
    private static HashMap<String, DependencyBaseInterface> controllerHashMap = new HashMap<>();
    private ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
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

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }




}
