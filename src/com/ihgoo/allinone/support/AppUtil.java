package com.ihgoo.allinone.support;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class AppUtil {
	
    public static int getVersion(Context context) {
        int result = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            result = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getVersionName(Context context) {
        String result = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            result = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    

    public static boolean isAppInstalled(final Context context, final String packageName) {
        try {
            final PackageManager pm = context.getPackageManager();
            final PackageInfo info = pm.getPackageInfo(packageName, 0);
            return info != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMainProcess(final Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceRunning(Context context, Class<?> cls) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        final String className = cls.getName();
        for (RunningServiceInfo service : services) {
            if (className.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
        if (apps == null || apps.isEmpty()) {
            return false;
        }
        for (RunningAppProcessInfo app : apps) {
            if (packageName.equals(app.processName)) {
                return true;
            }
        }
        return false;
    }

    public static PackageInfo getCurrentPackageInfo(final Context context) {
        final PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static PackageInfo getPackageInfo(final Context context, final String packageName) {
        final PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isDisabled(Context context, Class<?> clazz) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        return pm.getComponentEnabledSetting(componentName)
                == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    public static boolean isEnabled(Context context, Class<?> clazz) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        return pm.getComponentEnabledSetting(componentName)
                != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    public static void enableComponent(Context context, Class<?> clazz) {
        setComponentState(context, clazz, true);
    }

    public static void disableComponent(Context context, Class<?> clazz) {
        setComponentState(context, clazz, false);
    }

    public static void setComponentState(Context context, Class<?> clazz, boolean enable) {
        ComponentName componentName = new ComponentName(context, clazz);
        PackageManager pm = context.getPackageManager();
        final int oldState = pm.getComponentEnabledSetting(componentName);
        final int newState = enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        if (newState != oldState) {
            final int flags = PackageManager.DONT_KILL_APP;
            pm.setComponentEnabledSetting(componentName, newState, flags);
        }
    }

   
}
