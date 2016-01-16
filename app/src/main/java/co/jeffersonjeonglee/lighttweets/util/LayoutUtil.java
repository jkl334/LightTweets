package co.jeffersonjeonglee.lighttweets.util;

import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Jefferson on 1/14/16.
 */
public class LayoutUtil {

    public static void defaultLayout(View view, int marginLeft, int marginTop) {
        view.layout(marginLeft, marginTop, marginLeft + view.getMeasuredWidth(),
                marginTop + view.getMeasuredHeight());
    }

    public static void setWrapContentFL(View view){
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
    }

    public static void setMatchParentFL(View view){
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
    }

}
