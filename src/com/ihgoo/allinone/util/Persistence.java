package com.ihgoo.allinone.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class Persistence {


    /**
     * @return 精简版缓存路径
     */
    public static String getCacheDir() {
        File cacheDir = new File(StringUtil.concat(getSDcardRootPath(), "/", ".cache/dayima/concept/"));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * @return 精简版crash路径
     */
    public static String getCrashReportDir() {
        File cacheDir = new File(StringUtil.concat(getSDcardRootPath(), "/", ".cache/dayima/concept/crash/"));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    public static String getSDcardRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
