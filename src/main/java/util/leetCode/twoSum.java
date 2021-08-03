package util.leetCode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname: twoSum
 * @Date: 31/7/2021 1:41 上午
 * @Author: garlam
 * @Description:
 */


public class twoSum {
    private static Logger log = LogManager.getLogger(twoSum.class.getName());

    //https://leetcode-cn.com/problems/two-sum/

    public static void main1(String[] args) {
        int[] nums = {2, 3, 4};
        int target = 6;
        List<int[]> a = twoSum(nums, target);
        int[] aa = modelAnswer(nums, target);
        log.info("answer.int[{},{}]", aa[0], aa[1]);

        for (int[] ints : a) {
            //      log.info("twoSum.int[{},{}]", ints[0], ints[1]);
        }
    }

    public static void main(String[] args) {
        //prepare data
        int size = RandomUtil.getInt(20);
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = RandomUtil.getRangeInt(1, 20);
            System.out.print(nums[i] + " , ");
        }

        System.out.println();
        int target = RandomUtil.getInt(20);
        System.out.println("target: " + target);

        List<int[]> ls = twoSum(nums, target);
        for (int[] l : ls) {
            log.info("int[{},{}]", l[0], l[1]);
        }
    }

    public static List<int[]> twoSum(int[] nums, int target) {

        int duplicate = 0;
        List<Integer> ls = new ArrayList<>();
        int size = nums.length;
        for (int j = 0; j < size; j++) {
            if (nums[j] <= target) {
                ls.add(nums[j]);
                if (ls.contains(nums[j])) {
                    duplicate++;
                }
            }
        }

        size = ls.size();
        List<int[]> result = new ArrayList();
        for (int j = 0; j < size; j++) {
            int[] arr = {0, 0};
            if (duplicate > 0) {
                if (ls.contains(target - ls.get(j))) {
                    arr[0] = ls.get(j);
                    arr[1] = target - ls.get(j);
                    result.add(arr);
                }
            } else {
                if (ls.get(j) != (target - ls.get(j)) && ls.contains(target - ls.get(j))) {
                    arr[0] = ls.get(j);
                    arr[1] = target - ls.get(j);
                    result.add(arr);
                }
            }
        }
        return result;
    }


    public static int[] answerLow(int[] nums, int target) {
        List<Integer> ls = Arrays.stream(nums).boxed().collect(Collectors.toList());
        HashSet<Integer> hashSet = new HashSet<>(ls);
        int[] arr = {0, 0};
        int size = ls.size();
        for (int i = 0; i < size; i++) {
            if (ls.size() != hashSet.size()) {
                if (ls.get(i) == (target - ls.get(i))) {
                    arr[0] = i;
                    //arr[1] = i;
                    for (int j = i; j < size; j++) {
                        if (ls.contains(target - ls.get(j))) {
                            arr[1] = j;
                        }
                    }
                    break;
                }
            } else {
                if (ls.get(i) != (target - ls.get(i)) && ls.contains(target - ls.get(i))) {
                    arr[0] = i;
                    for (int j = i; j < size; j++) {
                        if (ls.contains(target - ls.get(j))) {
                            arr[1] = j;
                        }
                    }
                    break;
                }
            }
        }
        return arr;
    }

    public static int[] modelAnswer(int[] nums, int target) {
        int[] index = new int[2];
        // 建立k-v ，一一对应的 hash map
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                index[0] = i;
                index[1] = hash.get(nums[i]);
                return index;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target - nums[i], i);
        }
        return index;
    }
}

