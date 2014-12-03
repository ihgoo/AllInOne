package com.ihgoo.allinone;

import java.io.InputStream;

import android.R.integer;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 对Bitmap二次处理，避免OOM异常出现，
 * 
 * getBitmap()、decodeSampledBitmapFromResource()
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class BitmapUtils {

	private static final int TEMP_STORAGE_SIZE = 1024 * 100;

	/**
	 * Return bitmap through by imagePath.
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmap(String imagePath, int width, int height) {
		if (imagePath == null || "".equals(imagePath)) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[TEMP_STORAGE_SIZE];
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imagePath, options);
	}

	/**
	 * Return bitmap through by resources.
	 * 
	 * @param res
	 * @param is
	 * @param width
	 * @param height
	 * @return bitmap
	 */
	public static Bitmap getBitmap(InputStream is, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[TEMP_STORAGE_SIZE];
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 根据View得到自适应的bitmap对象
	 * 
	 * @param imagePath
	 * @param view
	 * @return bitmap
	 */
	public static Bitmap getBitmapByViewSize(String imagePath, View view) {
		if (imagePath == null || "".equals(imagePath)) {
			return null;
		}

		if (view == null) {
			return null;
		}

		return getBitmap(imagePath, view.getWidth(), view.getHeight());
	}

	/**
	 * 根据View得到自适应的bitmap对象
	 * 
	 * @param imagePath
	 * @param view
	 * @return bitmap
	 */
	public static Bitmap getBitmapByViewSize(InputStream is, View view) {
		if (view == null) {
			return null;
		}

		return getBitmap(is, view.getWidth(), view.getHeight());
	}

	/**
	 * bitmap转drawable
	 * 
	 * @param bitmap
	 * @return drawable
	 */
	public static Drawable convertBitmap2Drawable(Resources res, Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(res, bitmap);
		return bd;
	}

	/**
	 * 通过简单处理后获取res中的bitmap对象
	 * 
	 * @param paramResources
	 * @param res
	 * @param width
	 *            长度
	 * @param height
	 *            高度
	 * @return bitmap 对象
	 */
	public static Bitmap decodeSampledBitmapFromResource(
			Resources paramResources, int res, int width, int height) {
		InputStream localInputStream = paramResources.openRawResource(res);
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inTempStorage = new byte[TEMP_STORAGE_SIZE];
		localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		localOptions.inPurgeable = true;
		localOptions.inInputShareable = true;
		localOptions.inSampleSize = calculateInSampleSize(localOptions, width,
				height);
		localOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(localInputStream, null, localOptions);
	}

	/**
	 * 根据屏幕计算缩放比率
	 * 
	 * @param paramOptions
	 * @param width
	 *            长度
	 * @param height
	 *            高度
	 * @return 缩放比率
	 */
	public static int calculateInSampleSize(BitmapFactory.Options paramOptions,
			int width, int height) {
		int i = paramOptions.outHeight;
		int j = paramOptions.outWidth;
		int k = 1;
		if ((i > width) || (j > width)) {
			int m = Math.round(i / height);
			int n = Math.round(j / width);
			if (m < n)
				;
			for (k = m;; k = n) {
				float f1 = j * i;
				float f2 = 2 * (width * height);
				while (f1 / (k * k) > f2)
					k++;
			}
		}
		return k;
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