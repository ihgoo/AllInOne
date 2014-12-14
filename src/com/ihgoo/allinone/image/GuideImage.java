package com.ihgoo.allinone.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.ihgoo.allinone.BitmapUtils;
import com.ihgoo.allinone.util.SharePreferenceUtil;

/**
 * 引导图层
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class GuideImage {

	private int mTemp;

	private GuideImage() {
	}

	public static GuideImage getInstance() {
		return GuidImageHolder.INSTANCE;
	}

	private static class GuidImageHolder {
		public static final GuideImage INSTANCE = new GuideImage();
	}

	private OnGuidImageClickListener mOnClickListener;

	/**
	 * 回调点击,点击覆盖的图层之后要做的事情
	 */
	public interface OnGuidImageClickListener {
		void onClick();
	}

	public void setOnClickListener(OnGuidImageClickListener onClickListener) {
		this.mOnClickListener = onClickListener;
	}

	/**
	 * 在acitivity依次覆盖图层显示
	 * 
	 * @param act
	 * @param viewId
	 * @param imageIds
	 * @param preferenceName
	 */
	public void setGuidImages(final Activity act, final int viewId, final int[] imageIds, final String preferenceName) {
		if (preferenceName != null) {
			if (SharePreferenceUtil.getBoolean(act, preferenceName)) {
				return;
			}
		}

		int imageNumber = imageIds.length;
		if (imageNumber == 0) {
			throw new IllegalArgumentException("Image RESIds may be null,Please check it");
		}
		mTemp++;
		if (imageIds.length < mTemp) {
			mTemp = 0;

			SharePreferenceUtil.setValue(act, preferenceName, true);
			return;
		}

		View view = act.getWindow().getDecorView().findViewById(viewId);
		ViewParent viewParent = view.getParent();
		if (viewParent instanceof FrameLayout) {
			final FrameLayout frameLayout = (FrameLayout) viewParent;
			final ImageView guideImage = new ImageView(act.getApplication());
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			guideImage.setLayoutParams(params);
			guideImage.setScaleType(ScaleType.FIT_XY);

			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
			int x = localDisplayMetrics.heightPixels;
			int y = localDisplayMetrics.widthPixels;

			final Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(act.getResources(), imageIds[mTemp - 1], x, y);
			guideImage.setImageBitmap(bitmap);
			guideImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BitmapUtils.releaseImageViewResouce(guideImage);
					frameLayout.removeView(guideImage);
					bitmap.recycle();
					if (mOnClickListener != null) {
						mOnClickListener.onClick();
					}
					setGuidImages(act, viewId, imageIds, preferenceName);
				}

			});
			frameLayout.addView(guideImage);
		}

	}

	/**
	 * 在activity覆盖一层引导图层,无论何时都会显示
	 * 
	 * @param act
	 * @param viewId
	 * @param imageId
	 */
	public void setGuidImage(Activity act, int viewId, int imageId) {
		setGuidImage(act, viewId, imageId, null);
	}

	/**
	 * 在activity覆盖一层引导图层,初次进入activity时候显示
	 * 
	 * @param act
	 * @param viewId
	 * @param imageId
	 * @param preferenceName
	 */
	public void setGuidImage(Activity act, int viewId, int imageId, String preferenceName) {
		if (preferenceName != null) {
			if (SharePreferenceUtil.getBoolean(act, preferenceName)) {
				return;
			}
			SharePreferenceUtil.setValue(act, preferenceName, true);
		}

		View view = act.getWindow().getDecorView().findViewById(viewId); // 将要添加引导图片
		ViewParent viewParent = view.getParent();
		if (viewParent instanceof FrameLayout) {
			final FrameLayout frameLayout = (FrameLayout) viewParent;
			final ImageView guideImage = new ImageView(act.getApplication());
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			guideImage.setLayoutParams(params);
			guideImage.setScaleType(ScaleType.FIT_XY);

			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
			int x = localDisplayMetrics.heightPixels;
			int y = localDisplayMetrics.widthPixels;

			final Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(act.getResources(), imageId, x, y);

			guideImage.setImageBitmap(bitmap);
			guideImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					frameLayout.removeView(guideImage);
					BitmapUtils.releaseImageViewResouce(guideImage);
					if (mOnClickListener != null) { // 点击图层后将要做什么
						mOnClickListener.onClick();
						mOnClickListener = null;
					}
				}

			});
			frameLayout.addView(guideImage);
		}

	}

	/**
	 * 在activity覆盖一层引导图层,无论何时都会显示
	 * 
	 * @param act
	 * @param view
	 * @param imageId
	 */
	public void setGuidImage(Activity act, View view, int imageId) {
		setGuidImage(act, view, imageId, null);
	}

	/**
	 * 在activity覆盖一层引导图层,初次进入activity时候显示
	 * 
	 * @param act
	 * @param view
	 * @param imageId
	 * @param preferenceName
	 */
	public void setGuidImage(Activity act, View view, int imageId, String preferenceName) {
		if (preferenceName != null) {
			if (SharePreferenceUtil.getBoolean(act, preferenceName)) {
				return;
			}
			SharePreferenceUtil.setValue(act, preferenceName, true);
		}

		ViewParent viewParent = view.getParent();
		if (viewParent instanceof RelativeLayout) {
			final RelativeLayout frameLayout = (RelativeLayout) viewParent;
			final ImageView guideImage = new ImageView(act.getApplication());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			guideImage.setLayoutParams(params);
			guideImage.setScaleType(ScaleType.FIT_XY);

			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
			int x = localDisplayMetrics.heightPixels;
			int y = localDisplayMetrics.widthPixels;

			final Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(act.getResources(), imageId, x, y);
			guideImage.setImageBitmap(bitmap);
			guideImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					frameLayout.removeView(guideImage);
					BitmapUtils.releaseImageViewResouce(guideImage);
					if (mOnClickListener != null) { // 点击图层后将要做什么
						mOnClickListener.onClick();
						mOnClickListener = null;
					}
				}

			});
			frameLayout.addView(guideImage);
		} else {
		}
	}
}