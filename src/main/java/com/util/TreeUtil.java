package com.util;

public class TreeUtil {
    public static void main(String[] args) {
        int key = 15;
        int[] datas = new int[]{8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for (int i = 0; i < datas.length; i++) {
            if (datas[i] == key) {
                System.out.println("找到了, 共查找" + (i + 1) + "次");
                break;
            }
        }
    }
}
