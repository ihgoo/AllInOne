package com.ihgoo.allinone.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class PhoneUtils {
	private PhoneUtils() {
	}

	/**
	 * @param context
	 * @param dirName
	 *            Only the folder name, not full path.
	 * @return app_cache_path/dirName
	 */
	public static String getDiskCacheDir(Context context, String dirName) {
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File externalCacheDir = context.getExternalCacheDir();
			if (externalCacheDir != null) {
				cachePath = externalCacheDir.getPath();
			}
		}
		if (cachePath == null) {
			File cacheDir = context.getCacheDir();
			if (cacheDir != null && cacheDir.exists()) {
				cachePath = cacheDir.getPath();
			}
		}

		return cachePath + File.separator + dirName;
	}

	public static long getAvailableSpace(File dir) {
		try {
			final StatFs stats = new StatFs(dir.getPath());
			return (long) stats.getBlockSize()
					* (long) stats.getAvailableBlocks();
		} catch (Throwable e) {
			LogUtils.e(e.getMessage(), e);
			return -1;
		}

	}

	/**
	 * The end-user-visible name for the end product.
	 * 
	 * @param context
	 * @return
	 */
	public static String getModel(Context context) {
		return Build.MODEL;
	}

	//
	// public static String getHardware(Context context) {
	// if (getPhoneSDK(context) < 8) {
	// return "undefined";
	// } else {
	// Logger.d(TAG, "hardware:" + Build.HARDWARE);
	// }
	// return Build.HARDWARE;
	// }

	public static String getManufacturer(Context context) {
		return Build.MANUFACTURER;
	}

	/**
	 * 获取发行版版本
	 * 
	 * @param context
	 * @return
	 */
	public static String getFirmware(Context context) {
		return Build.VERSION.RELEASE;
	}

	public static String getSDKVer() {
		return Integer.valueOf(Build.VERSION.SDK_INT).toString();
	}

	/**
	 * 获取本机语言
	 * 
	 * @return
	 */
	public static String getLanguage() {
		Locale locale = Locale.getDefault();
		String languageCode = locale.getLanguage();
		if (TextUtils.isEmpty(languageCode)) {
			languageCode = "";
		}
		return languageCode;
	}

	/**
	 * 获取城市编号
	 * 
	 * @return
	 */
	public static String getCountry() {
		Locale locale = Locale.getDefault();
		String countryCode = locale.getCountry();
		if (TextUtils.isEmpty(countryCode)) {
			countryCode = "";
		}
		return countryCode;
	}

	/**
	 * 获取IMEI（移动设备国际身份码）
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		if (TextUtils.isEmpty(imei) || imei.equals("000000000000000")) {
			imei = "0";
		}

		return imei;
	}

	/**
	 * 获取国际移动用户识别码（IMSI）
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		if (TextUtils.isEmpty(imsi)) {
			return "0";
		} else {
			return imsi;
		}
	}

	// public static String getLac(Context context) {
	// CellIdInfo cell = new CellIdInfo();
	// CellIDData cData = cell.getCellId(context);
	// return (cData != null ? cData.getLac() : "1");
	// }
	//
	// public static String getCellid(Context context) {
	// CellIdInfo cell = new CellIdInfo();
	// CellIDData cData = cell.getCellId(context);
	// return (cData != null ? cData.getCellid() : "1");
	// }

	public static String getMcnc(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mcnc = tm.getNetworkOperator();
		if (TextUtils.isEmpty(mcnc)) {
			return "0";
		} else {
			return mcnc;
		}
	}

	/**
	 * Get phone SDK version
	 * 
	 * @return
	 */
	public static int getPhoneSDK(Context mContext) {
		TelephonyManager phoneMgr = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		LogUtils.i("Bild model:" + Build.MODEL);
		LogUtils.i("Bild model:" + phoneMgr.getLine1Number());
		LogUtils.i("Bild model:" + Build.VERSION.SDK);
		LogUtils.i("Bild model:" + Build.VERSION.RELEASE);
		int sdk = 7;
		try {
			sdk = Integer.parseInt(Build.VERSION.SDK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdk;
	}

	public static Object getMetaData(Context context, String keyName) {
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);

			Bundle bundle = info.metaData;
			Object value = bundle.get(keyName);
			return value;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取该app的版本
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			String versionName = pi.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 返回十进制序列号，用于唯一标识一个磁盘卷
	 * 
	 * @param context
	 * @return
	 */
	public static String getSerialNumber(Context context) {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
			if (serial == null || serial.trim().length() <= 0) {
				TelephonyManager tManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				serial = tManager.getDeviceId();
			}
			LogUtils.d("Serial:" + serial);
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
		return serial;
	}

	/**
	 * 判断SDCard是否已满
	 * 
	 * @return
	 */
	public boolean isSDCardSizeOverflow() {
		boolean result = false;
		// 取得SDCard当前的状态
		String sDcString = android.os.Environment.getExternalStorageState();

		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {

			// 取得sdcard文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

			// 获取SDCard上BLOCK总数
			long nTotalBlocks = statfs.getBlockCount();

			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();

			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();

			// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
			long nFreeBlock = statfs.getFreeBlocks();

			// 计算SDCard 总容量大小MB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

			// 计算 SDCard 剩余大小MB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
			if (nSDFreeSize <= 1) {
				result = true;
			}
		}// end of if
			// end of func
		return result;
	}

	/**
	 * 获取手机屏幕DisplayMetrics属性
	 * 
	 * @param activity
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Activity activity) {
		if (activity == null) {
			return null;
		}
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	/**
	 * 获取当前宽和高
	 * 
	 * @param activity
	 * @return
	 */
	public static String getWidthAndHeight(Activity activity) {
		DisplayMetrics displayMetrics = getDisplayMetrics(activity);
		if (displayMetrics == null) {
			return "";
		}
		int heightPixels = displayMetrics.heightPixels;
		int widthPixels = displayMetrics.widthPixels;
		String retString = heightPixels + "*" + widthPixels;
		return retString;
	}

}
