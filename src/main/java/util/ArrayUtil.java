package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname: ArrayUtil
 * @Date: 31/7/2021 1:14 上午
 * @Author: garlam
 * @Description:
 */

public class ArrayUtil {
    private static Logger log = LogManager.getLogger(ArrayUtil.class.getName());

    //https://blog.csdn.net/qq_43390235/article/details/106592102
    //Stream流之List、Integer[]、int[]相互转化

    public static void main(String[] args) {
        List a = new ArrayList();
        a.add(4);
        a.add(34);
        a.add(44);
        a.add(47);

        Integer[] ss = listToIntegerArray(a);
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i]);
        }
    }

    public static int[] listToIntArray(List<Integer> ls) {
        int[] arr = ls.stream().mapToInt(Integer::valueOf).toArray();
        return arr;
    }

    public static Integer[] listToIntegerArray(List ls) {
        return (Integer[]) ls.toArray(new Integer[0]);
    }

    public static String[] stringToStringArray(String s) {
        char[] c = s.toCharArray();
        int size = c.length;
        String[] ss = new String[size];
        for (int i = 0; i < size; i++) {
            ss[i] = String.valueOf(c[i]);
        }
        return ss;
    }


}

