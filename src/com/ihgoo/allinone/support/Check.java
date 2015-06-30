package com.ihgoo.allinone.support;

import java.util.List;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class Check {

    /**
     * 检测是否有sd卡
     *
     * @return
     */
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
   
    
    /**
     * 检测App是否在前台
     *
     * @param context
     * @param processName
     * @return APP在后台或者锁屏了返回TRUE,如果在前台返回false
     */
    public static boolean isBackgroundRunning(Context context, String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (activityManager == null)
            return false;
        // get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && process.importance != android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
    
	private static long lastClickTime;

	/**
	 * 是否快速点击
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
