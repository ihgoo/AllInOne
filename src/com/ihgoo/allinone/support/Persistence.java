package com.ihgoo.allinone.support;

import android.os.Environment;

import java.io.File;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class Persistence {

	public static final String APP_FOLDER_ON_SD = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/AllInOne/AllInOne";

	public static final String PHOTOCACHE_FOLDER = APP_FOLDER_ON_SD
			+ "/photo_cache";
	public static final String MY_FAVOURITE_FOLDER = APP_FOLDER_ON_SD
			+ "/my_favourite";
	/**
	 * 创建用户时的头像缓存
	 */
	public static final String USER_AVATAR_FOLDER = APP_FOLDER_ON_SD
			+ "/avatar";

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
