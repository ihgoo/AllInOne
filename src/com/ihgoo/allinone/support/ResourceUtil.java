package com.ihgoo.allinone.support;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class ResourceUtil {

    /**
     * 根据名称返回字符资源的id
     *
     * @return 正确返回id值，异常返回－1
     */
    public static int getStringId(Context context,String fieldName) {
        try {
            return getResourceId(context, fieldName, "string");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getResourceId(Context context, String fieldName, String type) {
        try {
            return getResources(context).getIdentifier(fieldName, type, "com.guangzhi.weijianzhi");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static Resources resources;
    /**
     * 获取resource
     *
     * @return
     */
    public static synchronized Resources getResources(Context context) {
        if (resources == null) {
            resources = context.getResources();
        }
        return resources;
    }

}
