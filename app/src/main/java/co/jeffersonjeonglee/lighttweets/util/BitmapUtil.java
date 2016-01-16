package co.jeffersonjeonglee.lighttweets.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;


/**
 * Created by Jefferson on 1/14/16.
 */
public class BitmapUtil {
    public static Bitmap getColorMaskedBitmap(Resources resources, int bitmapResId, int colorId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap mutableBitmap = BitmapFactory.decodeResource(resources, bitmapResId, options);
        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawColor(resources.getColor(colorId), PorterDuff.Mode.SRC_ATOP);
        return mutableBitmap;
    }

    public static Bitmap getColorMaskedBitmapRawColor(Resources resources, int bitmapResId, int rawColor) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap mutableBitmap = BitmapFactory.decodeResource(resources, bitmapResId, options);
        if (mutableBitmap != null) {
            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawColor(rawColor, PorterDuff.Mode.SRC_ATOP);
        }
        return mutableBitmap;
    }
}
