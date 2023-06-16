package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Classname: IntegerUtil
 * @Date: 2023/6/16 下午 12:05
 * @Author: kalam_au
 * @Description:
 */


public class IntegerUtil {
    private static final Logger log = LogManager.getLogger(IntegerUtil.class.getName());


    /**
     * @param integers 该方法会直接修改原始数组, 而不是返回一个新的已排序数组
     *                 由小到大排列
     *                 所以不需要return
     */
    public static Integer[] sortAsc(Integer[] integers) {
        Arrays.sort(integers);
        return integers;
    }


    /**
     * @param integers 由大到小排列
     */
    public static Integer[] sortDesc(Integer[] integers) {
        Arrays.sort(integers, Collections.reverseOrder());
        return integers;
    }

    /**
     * @param integers
     * @param order     true = 升序 / false = 降序
     * @return
     */
    public static String getSortResult(Integer[] integers, boolean order) {
        Integer[] sortedResult;
        if (order) {
            sortedResult = sortAsc(integers);
        } else {
            sortedResult = sortDesc(integers);
        }
        return Arrays.toString(sortedResult);
    }

}

