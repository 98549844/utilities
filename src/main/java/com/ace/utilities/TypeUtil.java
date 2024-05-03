package com.ace.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class TypeUtil {
    static private final Log log = LogFactory.getLog(TypeUtil.class);

    public static void main(String[] args) {
        System.out.println(roundUpByDigit(0.2896959, 3));
    }

    /**
     * @param input 输入double数值
     * @param digit round长度
     * @return
     */
    public static double roundUpByDigit(double input, int digit) {
        return new BigDecimal(input).setScale(digit, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 方法名：formatToDouble
     * 功能：double类型数值保留两位小数
     * 描述：返回值为double
     */
    public static Double formatToDouble(Double number, Integer digit) {
        if (NullUtil.isNull(number)) {
            return null;
        }
        if (NullUtil.isNull(digit)) {
            digit = 2;
        }
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(digit, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 方法名：formatToString
     * 功能：double类型的数值保留两位小数
     * 描述：返回值为String
     */
    public static String doubleToString(Double number, Integer digit) {
        if (NullUtil.isNull(number)) {
            return null;
        }
        if (NullUtil.isNull(digit)) {
            digit = 2;
        }
        String place = "";
        for (int i = 0; i < digit; i++) {
            place = place + "0";
        }

        DecimalFormat df = new DecimalFormat("#." + place);
        return df.format(number);
    }


    //Integer To BigDecimal
    public static Integer integerToBigDecimal(BigDecimal b) {
        if (NullUtil.isNull(b)) {
            return null;
        }
        return b.intValue();
    }

    //bigDecimal To Integer
    public static BigDecimal bigDecimalToInteger(Integer i) {
        if (NullUtil.isNull(i)) {
            return null;
        }
        return BigDecimal.valueOf(i);
    }

    //String to Long
    public static Long stringToLong(String s) {
        Long l = null;
        try {
            if (NullUtil.isNull(s) || s.isEmpty()) {
                return null;
            }
            l = Long.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return l;
    }

    //Long to Integer
    public static Integer longToInteger(Long l) {
        Integer i = null;
        try {
            if (NullUtil.isNull(l)) {
                return null;
            }
            i = l.intValue();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return i;

    }

    //Long to String
    public static String longToString(Long l) {
        String s = null;
        try {
            if (NullUtil.isNull(l)) {
                return null;
            }
            s = String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    public static String integerToString(Integer l) {
        String s = null;
        try {
            if (NullUtil.isNull(l)) {
                return null;
            }
            s = String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    public static Integer stringToInteger(String s) {
        Integer l = null;
        try {
            if (NullUtil.isNull(s) || s.isEmpty()) {
                return null;
            }
            l = Integer.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return l;
    }

    public static Long integerToLong(Integer i) {
        Long l = null;
        try {
            if (NullUtil.isNull(i)) {
                return null;
            }
            l = i.longValue();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return l;
    }

    public static String booleanToString(boolean b) {
        return String.valueOf(b);
    }

    /**
     * 方法名：isNumeric
     * 功能：判断字符串是否是数字
     * 入参：str：字符串
     * 出参：return：true或false
     */
    public static boolean isNumeric(String str) {
        if (NullUtil.isNull(str) || str.length() == 0) {
            Console.println("parameter is null , please check");
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e1) {
                try {
                    Float.parseFloat(str);
                    return true;
                } catch (NumberFormatException e2) {
                    return false;
                }
            }
        }
    }


    //检查double是否整数
    public static boolean isIntegerForDouble(double obj) {
        System.out.println(obj);
        double eps = 1e-10;  // 精度范围
        System.out.println(eps);
        return obj - Math.floor(obj) < eps;
    }

    public static char[] stringToCharset(String s) {
        // char[] charArray = s.toCharArray();
        return s.toCharArray();
    }

    public static String getType(Object o) {
        String type = o.getClass().getName();
        log.info("Object type: " + type);
        return type;
    }

    public static String getSimpleType(Object o) {
        String type =  o.getClass().getSimpleName();
        log.info("Object type: " + type);
        return type;
    }
}
