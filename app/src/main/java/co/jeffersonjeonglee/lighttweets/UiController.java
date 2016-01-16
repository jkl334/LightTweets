package co.jeffersonjeonglee.lighttweets;

import android.content.Context;

import java.util.Set;

import co.jeffersonjeonglee.lighttweets.application.DependencyBaseInterface;

/**
 * Created by Jefferson on 1/14/16.
 */
public class UiController implements DependencyBaseInterface {

    private static final String LOG_TAG = "UiController";
    private Set<UiControllerListener> listeners;
    private Context context;

    public UiController(Context context) {
        this.context = context;
    }

    //listener methods to overwrite
    public static abstract class UiControllerListener {

    }

    public void addListener(final UiControllerListener listenerToRegister) {
        listeners.add(listenerToRegister);
    }

    public void removeListener(UiControllerListener listenerToRemove) {
        listeners.remove(listenerToRemove);
    }


    //notifiers


}
