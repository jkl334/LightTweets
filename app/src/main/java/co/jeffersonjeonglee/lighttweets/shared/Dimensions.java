package co.jeffersonjeonglee.lighttweets.shared;

import android.content.res.Resources;

import co.jeffersonjeonglee.lighttweets.R;

/**
 * Created by Jefferson on 1/15/16.
 */
public class Dimensions {

    private static int rowHeight;
    private static int topBarHeight;

    public static void initialize(Resources resources) {
        rowHeight = resources.getDimensionPixelSize(R.dimen.lt_row_height);
        topBarHeight = resources.getDimensionPixelSize(R.dimen.lt_row_height);
    }

    public static int getRowHeight() {
        return rowHeight;
    }

    public static int getTopBarHeight() {
        return topBarHeight;
    }

}
