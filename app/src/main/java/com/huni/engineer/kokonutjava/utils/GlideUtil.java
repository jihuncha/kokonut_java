package com.huni.engineer.kokonutjava.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.huni.engineer.kokonutjava.R;

import java.io.File;

public class GlideUtil {
    public static final String TAG = GlideUtil.class.getSimpleName();

    public static void loadImage(Context context, ImageView view, String url, boolean placeholder, String f) {
        Log.d(TAG, "loadImage() - f: " + f + ", view: " + view + ", url: " + url);
        Uri uri = null;
        if (url != null && (url.startsWith("/") || url.startsWith("file://"))) {
            uri = Uri.fromFile(new File(url));
        }
        RequestBuilder builder = Glide.with(context)
                .load((uri != null) ? uri : url)
                .centerCrop()
                .skipMemoryCache(false)
                .dontTransform()
                .transition(new DrawableTransitionOptions().crossFade());
        if (placeholder) {
            builder.placeholder(R.drawable.shape_placeholder);
        }
        builder.into(view);
    }
}
