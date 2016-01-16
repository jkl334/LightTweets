package co.jeffersonjeonglee.lighttweets.ui;

/**
 * Created by Jefferson on 1/15/16.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import co.jeffersonjeonglee.lighttweets.R;

public class BubbleBackgroundDrawable extends Drawable {

    Paint boxPaint;
    private RectF boxBounds = new RectF();
    private int cornerRadius;
    private final int dimensionPixelSize;

    public BubbleBackgroundDrawable(Context context) {
        boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setColor(Color.WHITE);
        dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.dp_spacing);
        cornerRadius = dimensionPixelSize*4;
    }

    public void setBackgroundColor(int color) {
        boxPaint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        boxBounds.set(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRoundRect(boxBounds, cornerRadius, cornerRadius, boxPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
