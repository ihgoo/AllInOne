package com.ihgoo.allinone.support;

import android.text.TextUtils;

import org.json.JSONObject;

import com.ihgoo.allinone.exception.MathException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class MathUtil {
    /**
     * DecimalFormat("0.00")会四舍五入
     * 该方法是截取小数点后两位
     * @param number 数据
     */
    public static String dotSubTwo(double number) {
        DecimalFormat df = new DecimalFormat("0.000");
        if (Double.isNaN(number)) {
			throw new MathException("The parameter is not a number,plese check it.");
		}
        String str = df.format(number);
        if (str.contains(".")) {
        	return str.substring(0, str.length()-1);
		}
        return null;
    }
    

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 检查一个字符串是否是整数。该函数将避免NumberFormat异常。
     *
     * @param num 需要检查的字符串
     * @return 返回true表示是一个整数;如果字符串为为空，或者不符合规则返回false.
     * @see {@link NumberFormatException}
     */
    public static final boolean checkNumberFormat(String num) {
        if (TextUtils.isEmpty(num))
            return false;
        return num.matches("-[0-9]+|[0-9]+");
    }

    /**
     * 求一个数的模数,该函数通常用来循环获得0-length的索引.
     * <p/>
     * <code>for example:</br>
     * <pre>
     * final int divisor = 4;
     * for(int dividend = -9; dividend < 10; dividend ++)
     * {
     *     System.out.println(computeRemainder(dividend, divisor));
     * }
     * </pre></code>
     *
     * 你将只能获得0-3的数。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 模数
     */
    public static final int computeRemainder(int dividend, int divisor) {
        final int modulus = dividend % divisor;
        if (modulus < 0)
            return modulus + divisor;
        else
            return modulus;
    }

    /**
     * 计算方差
     *
     * @param nums
     * @return
     */
    public static double calD(ArrayList<Integer> nums) {
        double sum = 0;
        double n = nums.size();
        double d = 0;
        if (n > 0) {
            // 求和
            for (double num : nums) {
                sum += num;
            }
            double u = (double) sum / (double) nums.size();

            double temp = 0;
            for (double num : nums) {
                temp += (num - u) * (num - u);
            }

            d = Math.sqrt((double) temp / (double) n) / u;

        }

        return d;
    }

    public static boolean isInt(Field field) {
        return field.getType().getSimpleName().equals("int");
    }

    public static int getInt(Object object, int defaultValue) {

        String str = "";
        if (object != null) {
            str = object.toString();

        }
        return parseInt(str, defaultValue);
    }

    public static long getLong(Object object, long defaultValue) {
        String str = "";
        if (object != null) {
            str = object.toString();

        }
        return parseLong(str, defaultValue);
    }

    public static long getLong(JSONObject jsonObject, String name, long defaultValue) {
        long result = defaultValue;
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getLong(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int parseInt(String object, int defalult) {
        return (int) parseDouble(object, defalult);
    }

    public static float parseFloat(String object, float defalult) {
        return (float) parseDouble(object, defalult);
    }

    public static long parseLong(String object, long defalult) {
        return (long) parseDouble(object, defalult);
    }

    public static double parseDouble(String object, double defalult) {
        double result = defalult;
        if (object != null && object.length() > 0) {
            try {
                result = Double.parseDouble(object);
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static int getInt(JSONObject jsonObject, String name, int defaultValue) {
        int result = defaultValue;
        try {
            if (jsonObject != null && jsonObject.has(name)) {
                result = jsonObject.getInt(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * @param week calendar.day
     * @return 星期几
     */
    public static String getFriendlyWeekStr(int week) {
        String result = "";
        switch (week) {
            case Calendar.MONDAY:
                result = "一";
                break;
            case Calendar.TUESDAY:
                result = "二";
                break;
            case Calendar.WEDNESDAY:
                result = "三";
                break;
            case Calendar.THURSDAY:
                result = "四";
                break;
            case Calendar.FRIDAY:
                result = "五";
                break;
            case Calendar.SATURDAY:
                result = "六";
                break;
            case Calendar.SUNDAY:
                result = "日";
                break;

        }

        return result;

    }

    /**
     * 从数组里随机的获取一个
     *
     * @param <T>
     * @param list
     * @return
     */
    public static <T> T getRandOne(T[] list) {
        T result = null;
        if (list != null && list.length > 0) {
            int count = list.length;
            // int index = count - 1;
            int index = (int) (Math.random() * count);
            result = list[index];
        }
        return result;
    }

    public static <T> T getRandOne(List<T> arrayList) {
        T result = null;
        if (arrayList != null && arrayList.size() > 0) {
            int count = arrayList.size();
            int index = count - 1;
            index = (int) (Math.random() * index);
            result = arrayList.get(index);
        }
        return result;
    }

    public static <T> T getRandOne(Set<T> sets) {
        T result = null;
        if (sets != null) {
            int size = sets.size();
            int random = new Random().nextInt(size);
            int i = 0;
            for (T item : sets) {
                if (i == random) {
                    result = item;
                    break;
                }
                i = i + 1;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> getRands(ArrayList<T> arrayList, int num) {
        ArrayList<T> result = new ArrayList<T>();
        if (arrayList != null && arrayList.size() > 0) {
            num = Math.min(num, arrayList.size());
            arrayList = (ArrayList<T>) arrayList.clone();

            for (int i = 0; i < num; i++) {
                int randIndex = (int) (Math.random() * (arrayList.size() - 1));
                result.add(arrayList.get(randIndex));
                arrayList.remove(randIndex);
            }
        }
        return result;
    }

    public static String getSign(int num) {
        String result = "";
        if (num > 0) {
            result = "+";
        } else if (num == 0) {
            result = "";
        } else {
            result = "";
        }
        return result;
    }


    /**
     * 返回下一个元素，如果是最后一个，返回最后一个
     *
     * @param <T>
     * @param lists
     * @param cur
     * @return
     */
    public static <T> T getNextCycle(List<T> lists, int cur) {
        int count = lists.size();
        T result;

        int next = cur + 1;

        if (next == count) {
            next = 0;
        }

        result = lists.get(next);

        return result;

    }

    public static Float max(Float... nums) {

        Float result = null;
        if (nums.length > 0) {
            result = nums[0];
            for (Float num : nums) {
                if (num > result) {
                    result = num;
                }
            }
        }
        return result;
    }

    public static Float min(Float... nums) {

        Float result = null;
        if (nums.length > 0) {
            result = nums[0];
            for (Float num : nums) {
                if (num < result) {
                    result = num;
                }
            }
        }
        return result;
    }

    public static Float sum(Float... nums) {

        Float result = 0f;
        for (Float num : nums) {
            result += num;
        }
        return result;
    }


    public static <T> ArrayList<T> toArrayList(T[] objects) {
        ArrayList<T> result = new ArrayList<T>();
        if (objects != null) {
            for (T object : objects) {
                result.add(object);
            }
        }
        return result;
    }

}
