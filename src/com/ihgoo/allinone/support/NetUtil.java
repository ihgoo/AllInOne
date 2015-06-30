package com.ihgoo.allinone.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	public enum NetType {
		None(0), 
		Mobile(1),
		Wifi(2), 
		Other(3);
		NetType(int value) {
			this.value = value;
		}
		public int value;
	}
	
	
	private static ConnectivityManager getSystemConnectivityManager(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager;
	}
	
	 /**
     * 检查是否连接WIFI(可传输数据)
     *
     * @return 连接wifi返回true，否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = getSystemConnectivityManager(context);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    
	 /**
     * 检查是否连接移动蜂窝网络(可传输数据)
     *
     * @return 连接移动蜂窝网络返回true，否则返回false
     */
    public static boolean isMoblieConnected(Context context) {
        ConnectivityManager connectivityManager = getSystemConnectivityManager(context);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    
    /**
	 * 判断网络连接是否有效（此时可传输数据）。
	 * @param context
	 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo net = getSystemConnectivityManager(context).getActiveNetworkInfo();
		return net != null && net.isConnected();
	}
	
	
	public static NetType getConnectedType(Context context) {
		NetworkInfo net = getSystemConnectivityManager(context).getActiveNetworkInfo();
		if (null != net) {
			switch (net.getType()) {
				case ConnectivityManager.TYPE_WIFI :
					return NetType.Wifi;
				case ConnectivityManager.TYPE_MOBILE :
					return NetType.Mobile;
				default :
					return NetType.Other;
			}
		}
		return NetType.None;
	}

    

    /**
     * 检查是否连接网络（此时不确定可不可以传输数据）
     *
     * @return 连接返回true，否则返回false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = getSystemConnectivityManager(context);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * Check all NetworkInfo state which has been connected.
     * If not find the one is connected, always return false.
     *
     * @return
     */
    public boolean isNetworkOnline(Context context) {
        boolean status = false;
        try {
            ConnectivityManager connectivityManager = getSystemConnectivityManager(context);
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            for (int index = 0; index < networkInfos.length; index++) {
                NetworkInfo netInfo = networkInfos[index];
                if ((netInfo != null) && netInfo.isConnected()) {
                    status = true;
                }
            }
        } catch (Exception e) {
            // Only print the exception message and stack trace,
            // because this method must return a value to caller.
            e.printStackTrace();
        }
        return status;
    }
    

}
