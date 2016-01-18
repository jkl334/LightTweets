package co.jeffersonjeonglee.lighttweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import co.jeffersonjeonglee.lighttweets.R;
import co.jeffersonjeonglee.lighttweets.application.ApplicationController;
import co.jeffersonjeonglee.lighttweets.application.LightTweetsApplication;
import co.jeffersonjeonglee.lighttweets.shared.Dimensions;
import co.jeffersonjeonglee.lighttweets.shared.SharedConstants;
import co.jeffersonjeonglee.lighttweets.util.LayoutUtil;

/**
 * Created by Jefferson on 1/18/16.
 */
public class TweetView extends FrameLayout {

    private ImageView profilePicture;
    private TextView displayName;
    private TextView userName;
    private TextView tweet;

    private ApplicationController applicationController;

    public TweetView(Context context) {
        this(context, null);
    }

    public TweetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TweetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applicationController = (ApplicationController)LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);

        profilePicture = new ImageView(context);
        profilePicture.setImageBitmap(applicationController.getUserProfileBitmap());
        addView(profilePicture);

        displayName = new TextView(context);
        displayName.setTextAppearance(context, R.style.MediumText);
        displayName.setTextColor(getResources().getColor(R.color.lt_dark_grey));
        displayName.setSingleLine(true);
        LayoutUtil.setWrapContentFL(displayName);
        addView(displayName);

        userName = new TextView(context);
        userName.setTextAppearance(context, R.style.SmallerText);
        userName.setTextColor(getResources().getColor(R.color.lt_light_grey_text));
        userName.setSingleLine(true);
        LayoutUtil.setWrapContentFL(userName);
        addView(userName);

        tweet = new TextView(context);
        tweet.setTextAppearance(context, R.style.SmallText);
        tweet.setTextColor(getResources().getColor(R.color.lt_dark_grey));
        tweet.setSingleLine(false);
        addView(tweet);
    }

    public void setInfo(String displayNameStr, String userNameStr, String tweetStr) {
        displayName.setText(displayNameStr);
        userName.setText(userNameStr);
        tweet.setText(tweetStr);
    }

    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap bitmap) {
        profilePicture.setImageBitmap(bitmap);
        LayoutUtil.setWrapContentFL(profilePicture);
        profilePicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (Dimensions.getRowHeight()*2.5f), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int marginLeft = getWidth()/32;
        int marginTop = (int) (Dimensions.getRowHeight()*.125f);
        LayoutUtil.defaultLayout(profilePicture, marginLeft, marginTop);

        marginLeft = marginLeft*2 + profilePicture.getMeasuredWidth();
        LayoutUtil.defaultLayout(displayName, marginLeft, marginTop);

        marginTop = marginTop + displayName.getMeasuredHeight();
        LayoutUtil.defaultLayout(userName, marginLeft, marginTop);

        marginTop = (int) (Dimensions.getRowHeight()*1.125f);
        marginLeft = getWidth()/32;
        tweet.layout(marginLeft, marginTop, marginLeft + tweet.getMeasuredWidth(), (int) (getHeight() - Dimensions.getRowHeight()*.125f));
    }
}
