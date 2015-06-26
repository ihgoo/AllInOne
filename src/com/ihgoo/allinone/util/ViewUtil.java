package com.ihgoo.allinone.util;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class ViewUtil {

    public static void setUnderLine(TextView textView, String text1, String text2) {
        textView.setText(Html.fromHtml(text1 + "<u>" + text2 + "</u>"));
    }

    /**
     * @param color
     * @param relative
     * @param str
     * @return
     */
    public static CharSequence getSpan(int color, float relative, CharSequence str) {
        SpannableStringBuilder sb = new SpannableStringBuilder(str);
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (relative != 1f) {
            sb.setSpan(new RelativeSizeSpan(relative), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sb;
    }

    /**
     * @param color
     * @param relative
     * @param typeFace
     * @param str
     * @return
     */
    public static CharSequence getSpan(int color, float relative, int typeFace, CharSequence str) {
        SpannableStringBuilder sb = new SpannableStringBuilder(str);
        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (relative != 1f) {
            sb.setSpan(new RelativeSizeSpan(relative), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        sb.setSpan(new StyleSpan(typeFace), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static View getSpaceViewForListView(Context context,int width, int height) {
        View view = new TextView(context);
        view.setLayoutParams(new ListView.LayoutParams(width, height));
        return view;
    }

    public static View inflate(Context context,int id) {
        return LayoutInflater.from(context).inflate(id, null);
    }

}
