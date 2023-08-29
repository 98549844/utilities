package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.*;

import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("unchecked")
public class StringUtil {
    private static final Logger log = LogManager.getLogger(StringUtil.class);

    private static String collectionToDelimitedString(Collection<?> coll, String denim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(denim);
            }
        }
        return sb.toString();
    }

    private static String collectionToDelimitedString(Collection<?> coll, String symbol) {
        return collectionToDelimitedString(coll, symbol, "", "");
    }

    /**
     * @param coll   list转String并用符号拼接在一起
     * @param symbol
     * @return
     */
    private static String appendCollectionWithSymbol(Collection<?> coll, String symbol) {
        if (NullUtil.isNull(symbol)) {
            symbol = ",";
            log.warn("symbol is NULL, default using comma");
        }
        return collectionToDelimitedString(coll, symbol);
    }

    /**
     * @param coll list转String并用逗号拼接在一起
     * @param ","
     * @return
     */
    private static String appendCollectionWithComma(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }


    /**
     * 根据字符串长度由短到长排列
     *
     * @param arr
     */
    public static void sortByLength(String[] arr) {
        Arrays.sort(arr, Comparator.comparing(String::length));
    }


    /**
     * 去除句子内的空格
     *
     * @param content
     * @return
     */
    public static String trimAll(String content) {
        return content.replace(" ", "");
    }

    /**
     * @param input
     * @param maxLength
     * @param symbol
     * @return
     */
    private static List<String> spiltStringBySymbol(String input, int maxLength, String symbol) {
        int idx;
        String tmpRemainingResult = input, tmpRow;
        List<String> result = new ArrayList<String>();
        while (tmpRemainingResult.length() > maxLength) {
            idx = tmpRemainingResult.substring(0, maxLength).lastIndexOf(symbol);//assuming > -1
            if (idx == -1) {
                break;
            } else {
                tmpRow = tmpRemainingResult.substring(0, idx);
                result.add(tmpRow);
                tmpRemainingResult = tmpRemainingResult.substring(idx + symbol.length());
            }
        }
        if (!tmpRemainingResult.isEmpty()) {
            result.add(tmpRemainingResult);
        }
        return result;
    }

    private static List<String> spiltStringByComma(String input, int maxLength) {
        int idx;
        String tmpRemainingResult = input, tmpRow;
        List<String> result = new ArrayList<String>();
        while (tmpRemainingResult.length() > maxLength) {
            idx = tmpRemainingResult.substring(0, maxLength).lastIndexOf(",");//assuming > -1
            if (idx == -1) {
                break;
            } else {
                tmpRow = tmpRemainingResult.substring(0, idx);
                result.add(tmpRow);
                tmpRemainingResult = tmpRemainingResult.substring(idx + 1);
            }
        }
        if (tmpRemainingResult.length() > 0) {
            result.add(tmpRemainingResult);
        }
        return result;
    }

    public static List<String> convertCollectionToStringList(Collection<?> collection, int maxLength, String symbol) {
        List<String> result = new ArrayList<String>();
        if (NullUtil.isNotNull(collection) && collection.size() > 0) {
            Iterator<?> it = collection.iterator();
            StringBuilder sb = new StringBuilder(it.next().toString());
            if (sb.length() > maxLength) {
                System.out.println("Collection length > maxLength, Please check");
                return result;
            }
            String tmpRemainingResult = appendCollectionWithSymbol(collection, symbol);
            result = spiltStringBySymbol(tmpRemainingResult, maxLength, symbol);
        }
        return result;
    }


    public static List<String> convertCollectionToStringList(Collection<?> collection, int maxLength) {
        List<String> result = new ArrayList<String>();
        if (NullUtil.isNotNull(collection) && collection.size() > 0) {
            Iterator<?> it = collection.iterator();
            StringBuilder sb = new StringBuilder(it.next().toString());
            if (sb.length() > maxLength) {
                System.out.println("Collection length > maxLength, Please check");
                return result;
            }

            String tmpRemainingResult = appendCollectionWithComma(collection);
            result = spiltStringByComma(tmpRemainingResult, maxLength);
        }
        return result;
    }


    //首字母大寫
    public static String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    /**
     * 比较句子
     *
     * @param a
     * @param b
     */
    public static void compareSentences(String a, String b) {
        if (NullUtil.isNull(a) || a.isEmpty() || NullUtil.isNull(b) || b.isEmpty()) {
            System.out.println("Compare data empty, please check !");
            return;
        }
        List<String> aList = stringToList(a);
        List<String> bList = stringToList(b);

        //compare string
        if (aList.size() == 1 && bList.size() == 1) {
            compareString(a, b);
            return;
        }

        //compare sentence
        int init;
        if (aList.size() >= bList.size()) {
            init = aList.size();
            System.out.println(b);
            int i = 0;
            try {
                for (i = 0; i < init; i++) {
                    if (aList.get(i).equals(bList.get(i))) {
                        Console.print(aList.get(i), Console.WHITE);
                    } else {
                        Console.print(aList.get(i), Console.RED);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                for (; i < init; i++) {
                    Console.print(aList.get(i), Console.RED);
                }
            }
        } else {
            init = bList.size();
            System.out.println(a);
            int i = 0;
            try {
                for (i = 0; i < init; i++) {
                    if (bList.get(i).equals(aList.get(i))) {
                        Console.print(bList.get(i), Console.WHITE);
                    } else {
                        Console.print(bList.get(i), Console.RED);
                    }
                    System.out.print(" ");
                }
            } catch (IndexOutOfBoundsException e) {
                for (; i < init; i++) {
                    Console.print(bList.get(i), Console.RED);
                    System.out.print(" ");
                }
            }
        }
    }

    /**
     * 比较单词
     *
     * @param a
     * @param b
     */
    public static void compareString(String a, String b) {
        if (NullUtil.isNull(a) || a.isEmpty() || NullUtil.isNull(b) || b.isEmpty()) {
            System.out.println("Compare data empty, please check !");
            return;
        }

        char[] as = a.toCharArray();
        char[] bs = b.toCharArray();

        if (as.length != bs.length) {
            Console.println(a, Console.RED);
            Console.println(b, Console.WHITE);
        } else {
            Console.println(a, Console.WHITE);
            for (int i = 0; i < bs.length; i++) {
                if (String.valueOf(as[i]).equals(String.valueOf(bs[i]))) {
                    Console.print(String.valueOf(bs[i]), Console.WHITE);
                } else {
                    Console.print(String.valueOf(bs[i]), Console.RED);
                }
            }
        }

    }


    public static String isCnOrEn(String n) {
        String type = null;
        char[] chars = n.toCharArray();
        for (char c : chars) {
            if (c >= 0x0391 && c <= 0xFFE5) {
                //中文字符
                type = "CN";
            }
            if (c >= 0x0000 && c <= 0x00FF) {
                //英文字符
                type = "EN";
            }
        }
        return type;
    }

    /**
     * @param source
     * @return
     */
    public static List stringToList(String source) {
        if (NullUtil.isNull(source) || source.isEmpty()) {
            System.out.println("String is null");
            return null;
        }

        String[] sourceArray = source.split(" ");

        List<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, sourceArray);
        return stringList;
    }


    /**
     * 将数据库col转换成Obj字段
     *
     * @param source
     */
    public static void reformatColToObjName(String source) {
        //print total char
        char[] chars = source.toCharArray();
        System.out.println("total char: " + chars.length);

        //key出现的次数
        source = source.toLowerCase();
        List<Integer> location = searchAllIndex(source, "_");
        System.out.println("key出现的次数: " + location.size() + "\n");

        StringBuilder sourceBuild = new StringBuilder(source);
        for (int i = 0; i < location.size(); i++) {
            Integer integer = location.get(i);
            String s = source.substring(integer + 1, integer + 2).toUpperCase();
            //charList.add(s);
            // StringBuilder sourceBuild = new StringBuilder(source);
            sourceBuild.replace(integer + 1, integer + 2, s);

        }
        String result = sourceBuild.toString();
        result = result.replace("_", "");
        System.out.println(result);
    }


    public static List<Integer> searchAllIndex(String str, String key) {
        List<Integer> location = new ArrayList<>();
        int a = str.indexOf(key);//*第一个出现的索引位置
        while (a != -1) {
            location.add(a);
            //System.out.print(a + "\t");
            a = str.indexOf(key, a + 1);//*从这个索引往后开始第一个出现的位置
        }
        return location;
    }


    /**
     * 判断字符串的内容是不是全是数字
     *
     * @param text
     * @return
     */
    public static boolean isNumeric(String text) {
        if (NullUtil.isEmpty(text)) {
            return false;
        }
        int size = text.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static char[] stringToCharset(String s) {
        return s.toCharArray();
    }

    public static String[] split(String s, String spliter) {
        String[] results;
        if (s.contains(".")) {
            results = s.split("\\.");
        } else if (s.contains("|")) {
            results = s.split("\\|");

        } else if (s.contains("*")) {
            results = s.split("\\*");

        } else if (s.contains("\\")) {
            results = s.split("\\\\");

        } else if (s.contains("[]")) {
            results = s.split("\\[\\]");

        } else if (s.contains("^")) {
            results = s.split("\\^");

        } /*else if (s.contains(":")) {
            results = s.split("\\:");

        } else if (s.contains(".")) {
            results = s.split("\\.");

        } else if (s.contains(".")) {
            results = s.split("\\.");

        }*/ else {
            results = s.split(spliter);
        }
        return results;
    }

    public static String bytesToString(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }


    /**
     * sorting 字符串版字组排序
     *
     * @param arr
     * @param asc
     * @param left  由左边第N位开始排序
     * @param right 到右边第M位停止排序
     */
    public static void sort(String[] arr, boolean asc, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, asc, left, right);
            sort(arr, asc, left, pivotIndex - 1);
            sort(arr, asc, pivotIndex + 1, right);
        }
    }

    private static int partition(String[] arr, boolean order, int left, int right) {
        double pivot = Double.parseDouble(arr[right]); // 将最后一个元素作为基准值
        int i = left - 1;
        for (int j = left; j <= right - 1; j++) {
            double current = Double.parseDouble(arr[j]);
            if (order) {
                if (current <= pivot) {
                    i++;
                    swap(arr, i, j);
                }
            } else {
                if (current >= pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }

        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static boolean equalsIgnoreSpace(String a, String b) {
        String x = a.replace(" ", "");
        String y = b.replace(" ", "");
        return x.equals(y);
    }

}
