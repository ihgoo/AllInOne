package com.ihgoo.allinone.support;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class NullUtil {
    /**
     * @param object 判断对象是否为空，字符从为“”，long跟int是否为0
     * @return
     */
    public static boolean isEmptyObject(Object object) {
        boolean isEmpty = false;
        if (object == null) {
            isEmpty = true;
        } else {
            if (object.getClass().equals(String.class)) {
                if (object.toString().length() == 0) {
                    isEmpty = true;
                }
            } else if (object.getClass().equals(Integer.class) || object.getClass().equals(Long.class)) {
                if (object.equals(0)) {
                    isEmpty = true;
                }
            }
        }
        return isEmpty;
    }

    public static boolean isTrue(Object object) {
        return isTrue(object, true);
    }

    public static boolean isTrue(Object object, Object compareTo) {
        if (object == null) {
            return false;
        } else if (object.equals(compareTo)) {
            return true;
        } else {
            return false;
        }
    }
}
