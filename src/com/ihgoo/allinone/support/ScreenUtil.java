package com.ihgoo.allinone.support;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 屏幕大小有关
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class ScreenUtil {
	/**
	 * 获取屏幕的大小
	 * 
	 * @param context
	 * @return
	 */
	public static Screen getScreenPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels);
	}

	/**
	 * 获取屏幕的宽
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidthPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels).widthPixels;
	}

	/**
	 * 获取屏幕的高
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeightPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels).heightPixels;
	}

	public static class Screen {
		public int widthPixels;
		public int heightPixels;

		public Screen() {
		}

		public Screen(int widthPixels, int heightPixels) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}
	}
	
	 /**
     * get fit width for different resolution,input param is width for 720
     *
     * @param context
     * @param inwidth
     */
    public static int getFitWidth(Context context, int inwidth) {
        if (context == null)
            return inwidth;
        int screenwidth = getResolution(context)[0];
        return (inwidth * screenwidth) / 720;
    }

    /**
     * get fit width for different resolution,input param is width for 1180
     *
     * @param context
     * @param inheight
     */
    public static int getFitHeight(Context context, int inheight) {
        if (context == null)
            return inheight;
        int screenheight = getResolution(context)[1];
        return (inheight * screenheight) / 1280;
    }

    /**
     * get resolution
     *
     * @param context
     * @return
     */
    public static int[] getResolution(Context context) {
        int resolution[] = new int[2];
        // DisplayMetrics dm = new DisplayMetrics();
        // getWindowManger(context).getDefaultDisplay().getMetrics(dm);
        Display display = getWindowManger(context).getDefaultDisplay();
        resolution[0] = display.getWidth();
        resolution[1] = display.getHeight();
        return resolution;
    }

    /**
     * get WindowManager
     */
    public static WindowManager getWindowManger(Context context) {
        if (context == null)
            return null;
        WindowManager windowManager = null;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager;
    }

    public static float getDenisty(Context context) {
        DisplayMetrics screenDpi = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(screenDpi);
        return screenDpi.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * double
     *
     * @return
     */
    public static int dip2px(Context context,Double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = ResourceUtil.getResources(context).getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获得可视高度
     *
     * @param parent
     * @param son
     * @return
     */
    public static int getVisiableHeight(ViewGroup parent, View son) {
        // parent 的可视区域
        /*
		 * parent.invalidate(); son.invalidate();
		 */
        Rect rect = new Rect();
        parent.getHitRect(rect);
        int offset = parent.getScrollY();
        int result = rect.height() - (son.getTop() - offset);
        return result;
    }

    public static Display display = null;

    public static Display getDisplay(Context context) {
        if (display == null) {
            WindowManager wm = ((Activity) context).getWindowManager();
            display = wm.getDefaultDisplay();
        }
        return display;
    }

    public static int getScreenWidth(Context context) {
        return getDisplay(context).getWidth();
    }

    public static int getScreenHeight(Context context) {
        return getDisplay(context).getHeight();
    }

}
