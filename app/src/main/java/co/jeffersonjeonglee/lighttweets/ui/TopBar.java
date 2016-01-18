package co.jeffersonjeonglee.lighttweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.R;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.Dimensions;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import co.jeffersonjeonglee.lighttweets.util.BitmapUtil;
import co.jeffersonjeonglee.lighttweets.util.LayoutUtil;

/**
 * Created by Jefferson on 1/15/16.
 */
public class TopBar extends FrameLayout {

    private ApplicationController applicationController;
    private ImageView tweetButton;
    private TextView titleView;
    private Bitmap tweetBitmap;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);
        setBackgroundColor(getResources().getColor(R.color.lt_blue));

        titleView = new TextView(context);
        titleView.setTextAppearance(context, R.style.MediumText);
        //titleView.setText(getResources().getString(R.string.app_name));
        titleView.setText("@"+applicationController.getTwitterSession().getUserName() +"'s Feed");
        titleView.setTextColor(Color.WHITE);
        LayoutUtil.setWrapContentFL(titleView);
        addView(titleView);

        tweetButton = new ImageView(context);
        tweetBitmap = BitmapUtil.getColorMaskedBitmapRawColor(getResources(), R.drawable.bubble, Color.WHITE);
        tweetButton.setImageBitmap(tweetBitmap);
        tweetButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Dimensions.getRowHeight()));
        tweetButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(tweetButton);

        tweetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //applicationController.notifyOnComposeTweetRequested();
                applicationController.requestUserFeed();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Dimensions.getTopBarHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int marginTop = (getHeight() - titleView.getMeasuredHeight())/2;
        int marginLeft = (getWidth() - titleView.getMeasuredWidth())/2;
        LayoutUtil.defaultLayout(titleView, marginLeft, marginTop);

        marginTop = (getHeight() - getMeasuredHeight())/2;
        marginLeft = (getWidth() - tweetButton.getMeasuredWidth() - getWidth()/32);
        LayoutUtil.defaultLayout(tweetButton, marginLeft, marginTop);
    }
}
