package com.ihgoo.allinone.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ihgoo.allinone.util.LogUtils;
import com.ihgoo.allinone.util.PathCommonDefines;

public class MiscUtils {
	private final static String TAG = MiscUtils.class.getSimpleName();

	public static boolean checkFsWritable() {
		// Create a temporary file to see whether a volume is really writeable.
		// It's important not to put it in the root directory which may have a
		// limit on the number of files.

		// Logger.d(TAG, "checkFsWritable directoryName ==   "
		// + PathCommonDefines.APP_FOLDER_ON_SD);

		File directory = new File(PathCommonDefines.APP_FOLDER_ON_SD);
		if (!directory.isDirectory()) {
			if (!directory.mkdirs()) {
				LogUtils.e("checkFsWritable directoryName 000  ");
				return false;
			}
		}
		File f = new File(PathCommonDefines.APP_FOLDER_ON_SD, ".probe");
		try {
			// Remove stale file if any
			if (f.exists()) {
				f.delete();
			}
			if (!f.createNewFile()) {
				return false;
			}
			f.delete();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	/**
	 * 计算listview的高度,子ListView的每个Item必须是LinearLayout
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight() + 5;
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static boolean hasStorage() {
		boolean hasStorage = false;
		String str = Environment.getExternalStorageState();

		if (str.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			hasStorage = checkFsWritable();
		}

		return hasStorage;
	}

	public static int freeSpaceOnSd() {
		int freeSize = 0;

		if (hasStorage()) {
			StatFs statFs = new StatFs(PathCommonDefines.APP_FOLDER_ON_SD);

			try {
				long nBlocSize = statFs.getBlockSize();
				long nAvailaBlock = statFs.getAvailableBlocks();
				freeSize = (int) (nBlocSize * nAvailaBlock / 1024 / 1024);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return freeSize;
	}

	public static void updateFileTime(String dir, String fileName) {
		File file = new File(dir, fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	public static boolean checkNet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return true;
		}
		return false;
	}

	public static String getAPN(Context context) {
		String apn = "";
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null) {
			if (ConnectivityManager.TYPE_WIFI == info.getType()) {
				apn = info.getTypeName();
				if (apn == null) {
					apn = "wifi";
				}
			} else {
				apn = info.getExtraInfo().toLowerCase();
				if (apn == null) {
					apn = "mobile";
				}
			}
		}
		return apn;
	}

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

	public static String getFirmware(Context context) {
		return Build.VERSION.RELEASE;
	}

	public static String getSDKVer() {
		return Integer.valueOf(Build.VERSION.SDK_INT).toString();
	}

	public static String getLanguage() {
		Locale locale = Locale.getDefault();
		String languageCode = locale.getLanguage();
		if (TextUtils.isEmpty(languageCode)) {
			languageCode = "";
		}
		return languageCode;
	}

	public static String getCountry() {
		Locale locale = Locale.getDefault();
		String countryCode = locale.getCountry();
		if (TextUtils.isEmpty(countryCode)) {
			countryCode = "";
		}
		return countryCode;
	}

	public static String getIMEI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		if (TextUtils.isEmpty(imei) || imei.equals("000000000000000")) {
			imei = "0";
		}

		return imei;
	}

	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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
		TelephonyManager phoneMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
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
			ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

			Bundle bundle = info.metaData;
			Object value = bundle.get(keyName);
			return value;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

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

	public static String getSerialNumber(Context context) {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
			if (serial == null || serial.trim().length() <= 0) {
				TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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
			File pathFile = android.os.Environment.getExternalStorageDirectory();
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
}
