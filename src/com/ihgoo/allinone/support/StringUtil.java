package com.ihgoo.allinone.support;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class StringUtil {

    public static String quoteString(Object object, String left, String right) {
        return left.concat(object.toString()).concat(right);
    }

    public static String concat(Object... objects) {

        String str = new String();
        if (objects != null) {
            for (Object object : objects) {
                if (object != null) {
                    try {
                        str = str.concat(object.toString());
                    } catch (Exception e) {
                    }
                }
            }
        }
        return str;
    }

    public static <T> String join(Set<T> list) {
        return join(list.toArray(), ",");
    }

    public static String join(Object[] objects) {
        return join(objects, ",");
    }

    public static String join(Object[] objects, String glue) {
        int k = objects.length;
        if (k == 0)
            return "";
        StringBuilder out = new StringBuilder();
        out.append(objects[0]);
        for (int x = 1; x < k; ++x)
            out.append(glue).append(objects[x]);
        return out.toString();
    }
    public static String toString(Object object) {
        String result = "错误：null";
        if (object != null) {
            result = object.toString();
        }
        return result;
    }
    /**
     * @param string string以逗号分隔返回string的hashset
     * @return
     */
    public static HashSet<String> parseKeys(String string) {
        HashSet<String> keywords = new HashSet<String>();
        if (string != null) {
            for (String key : string.split(",")) {
                keywords.add(key);
            }
        }
        return keywords;
    }

    /**
     * @param objects
     * @param glue
     * @param left
     * @param right
     * @return
     */
    public static String joinWithQuote(Object[] objects, String glue, String left, String right) {
        int k = objects.length;
        if (k == 0)
            return null;
        StringBuilder out = new StringBuilder();
        out.append(quoteString(objects[0], left, right));
        for (int x = 1; x < k; ++x)
            out.append(glue).append(quoteString(objects[x], left, right));
        return out.toString();
    }
    public static String getStr(JSONObject jsonObject, String name) {
        String result = "";
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
