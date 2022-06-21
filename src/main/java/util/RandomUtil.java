package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    static private Log log = LogFactory.getLog(RandomUtil.class);

    /**
     * @param start
     * @param end
     * @return no duplicate integer
     */
    public static List<Integer> getRangeList(Integer start, Integer end) {
        int range = getRange(start, end);
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i <= range; i++) {
            ls.add(i + start);
        }
        Collections.shuffle(ls);
        return ls;
    }


    public static List<Integer> getDuplicateRangeList(Integer start, Integer end) {
        int range = getRange(start, end);
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i <= range; i++) {
            ls.add(getRangeInt(start, end));
        }
        return ls;
    }

    private static int getRange(Integer start, Integer end) {
        if (!validate(start, end)) {
            return 0;
        }

        List<Integer> s = switchMinMax(start, end);
        start = s.get(0);
        end = s.get(1);

        int range = end - start;
        return range;
    }

    private static boolean validate(Integer start, Integer end) {
        if (NullUtil.isNull(start) || NullUtil.isNull(end) || start.equals(end)) {
            return false;
        }
        return true;
    }

    private static List<Integer> switchMinMax(Integer start, Integer end) {
        List<Integer> r = new ArrayList<>();
        if (start > end) {
            int t = end;
            end = start;
            start = t;
            r.add(start);
            r.add(end);
        } else {
            r.add(start);
            r.add(end);
        }
        return r;
    }

    public static int getRangeInt(int start, int end) {
        if (!validate(start, end)) {
            return 0;
        }
        start = switchMinMax(start, end).get(0);
        end = switchMinMax(start, end).get(1);

        // 包含max，所以要加1
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }

    /**
     * @param i
     * @return 0~i integer
     */
    public static int getInt(int i) {
        return (int) (Math.random() * i);
    }

    public static void main(String[] args) {
        System.out.println(RandomUtil.getInt(100));
    }
}
