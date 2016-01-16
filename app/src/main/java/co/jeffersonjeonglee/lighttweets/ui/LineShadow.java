package co.jeffersonjeonglee.lighttweets.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import co.jeffersonjeonglee.lighttweets.R;

/**
 * Created by Jefferson on 1/16/16.
 */
public class LineShadow extends View {

    private Paint shadowPaint;
    private LinearGradient linearGradient;
    public int shadowBlurRadius;

    public LineShadow(Context context) {
        this(context, null);
    }

    public LineShadow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineShadow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shadowBlurRadius = getResources().getDimensionPixelSize(R.dimen.dp_spacing) * 4;

        float[] controlPoints = {0, 1.f};
        int[] colors = {getResources().getColor(R.color.lt_shadow), Color.TRANSPARENT};
        linearGradient = new LinearGradient(0, 0, 0, shadowBlurRadius, colors, controlPoints, Shader.TileMode.CLAMP);
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setShader(linearGradient);
        setWillNotDraw(false);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, shadowBlurRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), shadowPaint);
    }

}

