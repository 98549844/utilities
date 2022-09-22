package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Classname: ArrayUtil
 * @Date: 31/7/2021 1:14 上午
 * @Author: garlam
 * @Description:
 */

@SuppressWarnings("unchecked")
public class ArrayUtil {
	private static final Logger log = LogManager.getLogger(ArrayUtil.class.getName());

	//https://blog.csdn.net/qq_43390235/article/details/106592102
	//Stream流之List、Integer[]、int[]相互转化

    public static Boolean isArray(Object obj) {
        if (NullUtil.isNull(obj)) {
            return null;
        }
		return obj.getClass().isArray();
    }

	public static Set arrayIntToHashSet(int[] nums) {
		return Arrays.stream(nums).boxed().collect(Collectors.toSet());
	}

	public static int[] listToIntArray(List<Integer> ls) {
		return ls.stream().mapToInt(Integer::valueOf).toArray();
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

