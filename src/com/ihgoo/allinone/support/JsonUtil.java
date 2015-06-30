package com.ihgoo.allinone.support;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class JsonUtil {
    public static final <T> T J2O(JSONObject object, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            final String name = field.getName();
            field.setAccessible(true);
            if (object.has(name)) {
                try {
                    field.set(result, object.getString(name));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return result;
    }
}
