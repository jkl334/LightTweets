package co.jeffersonjeonglee.lighttweets.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import co.jeffersonjeonglee.lighttweets.shared.Dimensions;


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

    public static Bitmap generateProfilePicBitmap(Bitmap bitmap) {
        int length = (int) (Dimensions.getRowHeight()*.75f);
        Bitmap output = Bitmap.createBitmap(length,
                length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff000000;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, length, length);
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawBitmap(bitmap, null, rect, paint);
        return output;
    }


}
