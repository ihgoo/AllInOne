package com.ihgoo.allinone.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.ihgoo.allinone.support.Check;
import com.ihgoo.allinone.support.Persistence;
import com.ihgoo.allinone.support.ResourceUtil;
import com.ihgoo.allinone.support.ScreenUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class ImageUtil {
    /**
     * @param v
     * @param width
     * @param height
     * @return 获取view固定宽高的图像
     */
    public static Bitmap getViewCache(View v, int width, int height) {
        Bitmap b = null;
        if (v != null) {
            try {
                v.setDrawingCacheEnabled(true);
                v.measure(width == 0 ? View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED) : width, height == 0 ? View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED) : height);
                v.layout(0, 0, width == 0 ? v.getMeasuredWidth() : width, height == 0 ? v.getMeasuredHeight() : height);
                v.buildDrawingCache(true);
                if (v.getDrawingCache() != null) {
                    b = Bitmap.createBitmap(v.getDrawingCache());
                }
                v.setDrawingCacheEnabled(false); // clear drawing cache
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String getDarenShareImagePath(Context context,View view, String type) {
        String darenShareImagePath = "";
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight() + 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 绘制背景
        canvas.drawColor(Color.WHITE);
        // 绘制元素
        view.draw(canvas);
        // 绘制logo
        drawLogo(context,canvas, type);
        // 保存文件，并返回文件路径
        darenShareImagePath = saveImageToCache(bitmap);
        return darenShareImagePath;
    }

    /**
     * 在左下角绘制logo
     *
     * @param canvas
     */
    public static void drawLogo(Context context,Canvas canvas, String type) {
        float heightParent = canvas.getHeight();

        float heightWrap = ScreenUtil.dip2px(context,40);
        float heightLogo = ScreenUtil.dip2px(context,20);
        float heightText = ScreenUtil.dip2px(context,12);
        float leftMargin = ScreenUtil.dip2px(context,10);

        canvas.save();
        canvas.translate(leftMargin, 0);
        // 绘制logo
        Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), android.R.layout.select_dialog_item);
        Matrix logoMatrix = new Matrix();
        Paint logoPaint = new Paint();
        logoPaint.setAntiAlias(true);
        float scale = heightLogo / (float) logoBitmap.getHeight();
        logoMatrix.postScale(scale, scale, 0, 0);
        logoMatrix.postTranslate(0, heightParent - heightWrap + (heightWrap - heightLogo) / 2);

        canvas.drawBitmap(logoBitmap, logoMatrix, logoPaint);

        // 绘制文字
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(heightText);
        String text = "  " + type + "  by 微兼职";

        // 垂直居中高度修正
        // float fix = Ihgoo.dip2px(2);
        /*
         * canvas.drawText(text, heightLogo, heightParent - heightWrap +
		 * heightText - fix + (heightWrap - heightText) / 2, textPaint);
		 */

        canvas.drawText(text, heightLogo, heightParent - heightText, textPaint);

        canvas.restore();
    }

    /**
     * 临时保存图片
     *
     * @param bitmap
     * @return 图片路径
     */
    public static String saveImageToCache(Bitmap bitmap) {
        String result = null;
        try {
            if (Check.checkSDCard()) {
                String cacheDir = Persistence.getCacheDir();
                if (cacheDir != null) {
                    File cacheFile = new File(Persistence.getCacheDir(), "com.guangzhi.weijianzhi.imageShare.png");
                    cacheFile.deleteOnExit();
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(cacheFile));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    result = cacheFile.getPath();
                    // 文件关闭
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Drawable getDrawable(Context context,int id) {
        return ResourceUtil.getResources(context).getDrawable(id);
    }

    /**
     * 临时保存图片
     *
     * @param bitmap
     * @return 图片路径
     */
    public static String saveImageToAPP(Context context,Bitmap bitmap) {
        String result = "";
        OutputStream out = null;
        try {
            if (bitmap != null) {
                String path = context.getApplicationContext().getFilesDir().getAbsolutePath();
                path = path + "/../.cache/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File cacheFile = new File(file, "imageShare.jpeg");
                cacheFile.deleteOnExit();
                out = new BufferedOutputStream(new FileOutputStream(cacheFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                result = cacheFile.getPath();
                // 文件关闭
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Drawable getDrawable(Context context,String id) {
        int resourceId = -1;
        try {
            resourceId = ResourceUtil.getResourceId(context, id, "drawable");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getDrawable(resourceId);
    }

    /**
     * 手动回收imageView图片资源
     *
     * @param imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null)
            return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
}
