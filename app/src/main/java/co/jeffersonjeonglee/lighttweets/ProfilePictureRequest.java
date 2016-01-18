package co.jeffersonjeonglee.lighttweets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import co.jeffersonjeonglee.lighttweets.util.BitmapUtil;

/**
 * Created by Jefferson on 1/18/16.
 */
public class ProfilePictureRequest extends Request<Bitmap>{

    Response.Listener listener;
    private String photoObject;

    public ProfilePictureRequest(String url, String photoObject, Response.Listener listener,
                              Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        this.listener = listener;
        this.photoObject = photoObject;
    }

    @Override
    protected Response<Bitmap> parseNetworkResponse(NetworkResponse networkResponse) {
            if (networkResponse.data != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bitmap = BitmapFactory.decodeByteArray(networkResponse.data,
                        0, networkResponse.data.length, options);
                Bitmap profilePicBitmap = BitmapUtil.generateProfilePicBitmap(bitmap);
                return Response.success(profilePicBitmap, null);
            } else {
                return Response.error(new VolleyError(networkResponse));
            }

    }
    @Override
    @SuppressWarnings("unchecked")
    protected void deliverResponse(Bitmap bitmap) {
        listener.onResponse(bitmap);
    }

}