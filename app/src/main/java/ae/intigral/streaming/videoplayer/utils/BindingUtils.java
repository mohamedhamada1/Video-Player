/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package ae.emaratech.shari.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ae.emaratech.shari.R;
import ezy.ui.view.NumberStepper;

/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter("android:roundedsrc")
    public static void setRoundedImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).placeholder(R.drawable.no_image).error(R.drawable.no_image).bitmapTransform(new RoundedCornersTransformation(context, 3, 0)).into(imageView);
    }

    @BindingAdapter("android:srcwithplaceholder")
    public static void setImageWithPlaceHolderUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleInvisible")
    public static void visibleInvisible(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("android:htmltxt")
    public static void setTextWithHtml(TextView textView, String desc) {
        if (desc == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            textView.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT));
        else
            textView.setText(Html.fromHtml(desc));
    }

    @BindingAdapter("android:customNumStars")
    public static void setNumOfStarsForRatingBar(RatingBar ratingBar, float stars) {
    }
}
