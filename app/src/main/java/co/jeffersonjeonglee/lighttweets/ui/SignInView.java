package co.jeffersonjeonglee.lighttweets.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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
public class SignInView extends FrameLayout {

    private ApplicationController applicationController;

    private BubbleBackgroundDrawable box;
    private TextView loginButton;
    private int loginPadding;

    private ImageView logo;
    private Bitmap logoBitmap;

    private TextView desc;
    private TextView author;

    public SignInView(Context context) {
        this(context, null);
    }

    public SignInView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applicationController = (ApplicationController) LightTweetsApplication.getControllerAtKey(SharedConstants.APPLICATION_CONTROLLER);

        setBackgroundColor(getResources().getColor(R.color.lt_blue));
        loginPadding = getResources().getDimensionPixelOffset(R.dimen.dp_spacing) * 20;
        box = new BubbleBackgroundDrawable(context);
        box.setBackgroundColor(Color.WHITE);
        loginButton = new TextView(context);
        loginButton.setTextAppearance(context, R.style.LargeText);
        loginButton.setBackgroundDrawable(box);
        loginButton.setTextColor(getResources().getColor(R.color.lt_blue));
        loginButton.setText(getResources().getString(R.string.sign_in_with_twitter));
        loginButton.setPadding(loginPadding, loginPadding * 2, loginPadding, loginPadding * 2);
        LayoutUtil.setWrapContentFL(loginButton);
        addView(loginButton);

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationController.notifyOnSignInButtonPressed();
            }
        });

        loginButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        box.setBackgroundColor(getResources().getColor(R.color.lt_grey));
                        break;
                    case MotionEvent.ACTION_UP:
                        box.setBackgroundColor(Color.WHITE);
                        break;
                }
                return false;
            }
        });


        logo = new ImageView(context);
        logoBitmap = BitmapUtil.getColorMaskedBitmap(getResources(), R.drawable.twitteroutline, R.color.lt_grey);
        logo.setImageBitmap(logoBitmap);
        logo.setLayoutParams(new LayoutParams(Dimensions.getRowHeight() * 3, Dimensions.getRowHeight() * 3));
        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(logo);

        desc = new TextView(context);
        desc.setTextAppearance(context, R.style.SmallText);
        desc.setTextColor(getResources().getColor(R.color.lt_grey));
        desc.setText(getResources().getString(R.string.desc));
        LayoutUtil.setWrapContentFL(desc);
        addView(desc);

        author = new TextView(context);
        author.setTextAppearance(context, R.style.SmallerText);
        author.setTextColor(getResources().getColor(R.color.lt_grey));
        author.setText(getResources().getString(R.string.author));
        LayoutUtil.setWrapContentFL(author);
        addView(author);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int marginTop = (int) (getHeight()*.65f);
        int marginLeft = (getWidth() - loginButton.getMeasuredWidth())/2;
        LayoutUtil.defaultLayout(loginButton, marginLeft, marginTop);

        marginTop = (int) (getHeight()*.1f);
        marginLeft = (getWidth() - logo.getMeasuredWidth())/2;
        LayoutUtil.defaultLayout(logo, marginLeft, marginTop);

        marginTop = getHeight()/2;
        marginLeft = (getWidth() - desc.getMeasuredWidth())/2;
        LayoutUtil.defaultLayout(desc, marginLeft, marginTop);

        marginTop = (int) (getHeight()/2 + desc.getMeasuredHeight()*1.5f);
        marginLeft = (getWidth() - author.getMeasuredWidth())/2;
        LayoutUtil.defaultLayout(author, marginLeft, marginTop);
    }
}
