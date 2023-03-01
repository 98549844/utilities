package com.util;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.generator.DataGenerator;
import com.util.entity.Users;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ListUtil {
    static private final Log log = LogFactory.getLog(ListUtil.class);

    public final static String LIST_1 = "LIST_1";
    public final static String LIST_2 = "LIST_2";


    public static List arrayIntToList(int[] nums) {
        return Arrays.asList(ArrayUtils.toObject(nums));
    }

    public static int getMax(List<Integer> integerList) {
        if (NullUtil.isNull(integerList)) {
            return 0;
        }
        return Collections.max(integerList);
    }

    public static int getMin(List<Integer> integerList) {
        if (NullUtil.isNull(integerList)) {
            return 0;
        }
        return Collections.min(integerList);
    }


    public static List intArrayToList(int[] i) {
        return Arrays.stream(i).boxed().collect(Collectors.toList());
    }

    /**
     * 检查元素有没有重复
     *
     * @param ls
     * @return
     */
    public static boolean duplicateElement(List ls) {
        HashSet<Integer> hashSet = new HashSet<>(ls);
        boolean duplicate = false;

        if (ls.size() != hashSet.size()) {
            duplicate = true;
        } else {
            duplicate = false;
        }
        return duplicate;
    }


    /**
     * 比较两个list是否完全相同
     *
     * @param
     * @return
     */
/*
    public static boolean compareList(List ls1, List ls2) {
        HashSet<Integer> hashSet1 = new HashSet<>(ls1);
        HashSet<Integer> hashSet2 = new HashSet<>(ls2);
        boolean duplicate = false;

        if(){
            未完成
        }

        return duplicate;
    }
*/


    //根据长度把list拆分
    public static List<List<T>> splitList(List<T> list, int len) {
        if (NullUtil.isNull(list) || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    public static List<T> removeDuplicate(List<T> list) {
        if (NullUtil.isNull(list)) {
            return null;
        }
        List<T> listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    public static List deduplicate(List list) {
        if (NullUtil.isNull(list)) {
            return null;
        }
        LinkedHashSet<T> hashSet = new LinkedHashSet<T>(list);
        ArrayList<T> listWithoutDuplicates = new ArrayList<>(hashSet);
        return listWithoutDuplicates;
    }


    public static void main(String[] args) {
        List<String> ls1 = new ArrayList<>();
        List<String> ls2 = new ArrayList<>();
        List<String> ls3 = new ArrayList<>();

        ls1.add("aaa");
        ls1.add("bbb");
        ls1.add("ccc");

        ls2.add("aaa");
        ls2.add("xxx");
        ls2.add("ttt");
        ls2.add("xxx");
        ls2.add("bbb");
        ls2.add("aaa");

        Map map = getDeduplicateElements(ls1, ls2);
        System.out.println("map:   " + map);

    }


    /**
     * 比较两个list, 把不相同的找出来
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static Map getDeduplicateElements(List ls1, List ls2) {
        Map map = new HashMap();
        List ls = new ArrayList();
        for (Object obj : ls1) {
            if (!ls2.contains(obj)) {
                log.info("List1 独立元素: " + obj.toString());
                ls.add(obj);
            }
        }
        if (ls.size() > 0) {
            map.put(ListUtil.LIST_1, ls);
        }


        ls = new ArrayList();
        for (Object obj : ls2) {
            if (!ls1.contains(obj)) {
                log.info("List2 独立元素: " + obj.toString());
                ls.add(obj);
            }
        }
        if (ls.size() > 0) {
            map.put(ListUtil.LIST_2, ls);
        }

        if (map.size() > 0) {
            log.info("Compare result: NOT EQUAL !");
        } else {
            log.info("Compare result: EQUAL !");
        }

        return map;
    }


    /**
     * @param list
     * @return 重复元素list
     */
    public static List getDuplicated(List list) {
        Set<T> listSet = new HashSet<>(list);
        Collection<T> sub = CollectionUtils.subtract(list, listSet);
        HashSet<T> hSet = new HashSet<>(sub);
        List ls = new ArrayList<T>(hSet);
        return ls;
    }




/*    public static List deduplicate(List list) {
        List listTemp = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }*/

    public static Integer getMaxByList(List<Integer> list) {
        int i = 0;
        try {
            if (NullUtil.isNull(list)) {
                return i;
            }
            i = Collections.max(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 由小到大排序
     *
     * @param list
     * @return
     */
    public static List sortAsc(List list) {
        Collections.sort(list);
        return list;
    }


    /**
     * 由大到小排序
     *
     * @param list
     * @return
     */
    public static List sortDesc(List list) {
        Collections.sort(list);
        int x = list.size() - 1;
        List reverse = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            reverse.add(list.get(x - i));
        }
        return reverse;
    }


    /**
     * 随机选取list的元素(重覆)
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List getRandomListByPercent(List ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }
        Integer percentItems = ls.size() * percentage / 100;

        List resultLs = new ArrayList();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < percentItems; i++) {
            int n = random.nextInt(ls.size());
            resultLs.add(ls.get(n));
        }
        return resultLs;
    }

    /**
     * 随机选取list的元素(不重覆)
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List getRandomListByPercentNonRepeatable(List ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }

        Integer percentItems = ls.size() * percentage / 100;

        List resultLs = new ArrayList();
        SecureRandom random = new SecureRandom();
        int i = 0;
        while (resultLs.size() < percentItems) {
            int n = random.nextInt(ls.size());
            if (!resultLs.contains(ls.get(n))) {
                resultLs.add(ls.get(n));
            }
        }

        return resultLs;
    }


    /**
     * just a sample
     * need override
     */
    public static void sortListByGuava(List<Users> ls) {
        if (ls == null) {
            ls = DataGenerator.generateUsers();
        }
        int size = ls.size();
        System.out.println("********before sorting");
        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + " ; ");
            } else {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("********after sorting");

        Ordering<Users> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Users, Integer>() {
            @Override
            public Integer apply(Users user) {
                return Math.toIntExact(user.getUserId());
            }
        });
        ls.sort(ordering);

        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + " ; ");
            } else {
                System.out.println();
            }
        }
    }

    /**
     * just a sample
     * need override
     */
    public static void sortListByCollections(List<Users> ls) {
        if (ls == null) {
            ls = DataGenerator.generateUsers();
        }
        int size = ls.size();
        System.out.println("********before sorting");
        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + "(" + ls.get(i).getUsername() + ")" + " ; ");
            } else {
                System.out.println();
            }
        }
        System.out.println("");
        System.out.println("********after sorting");


        //compare函数的返回值-1, 1, 0
        //-1表示两个数位置交换，1表示不交换，0岂不是没有什么存在意义
        Collections.sort(ls, new Comparator<Users>() {
            @Override
            public int compare(Users o1, Users o2) {
                int i = 0;
                if (i == 0) {
                    i = o1.getUsername().compareTo(o2.getUsername());
                }

                if (i == 0) {
                    i = o1.getUserId().compareTo(o2.getUserId());
                }
                return i;
            }
        });

        for (int i = 0; i < size; i++) {
            if (i % 10 != 0) {
                System.out.print(ls.get(i).getUserId() + "(" + ls.get(i).getUsername() + ")" + " ; ");
            } else {
                System.out.println();
            }
        }
    }

    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        String result = list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
        System.out.println(result);
        return result;
    }

    public static List<T> removeElementByIterator(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        }
        Iterator<T> iterator = ls.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(removalVal)) {
                iterator.remove();
            }
        }
        return ls;
    }

    public static List<T> removeElementByForloop(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        } else {
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).equals(removalVal)) {
                    ls.remove(i);
                    i = i - 1;
                }
            }
        }
        return ls;
    }

    public static List<T> removeElementByLoop(List<T> ls, T removalVal) {
        if (NullUtil.isNull(ls)) {
            log.error("List is null");
        } else {
            for (int i = ls.size() - 1; i >= 0; i--) {
                if (ls.get(i).equals(removalVal)) {
                    ls.remove(i);
                }
            }
        }
        return ls;
    }


    public static List stringArrayToList(String[] array) {
        List<String> list = Arrays.asList(array);
        return list;
    }


    public static void printListObjectSet(List list) {
        if (NullUtil.isNull(list)) {
            log.error("List is null");
            return;
        }
        List<Object[]> ls = list;
        log.info("List<Object[]> printing ...");
        for (Object[] objs : ls) {
            for (Object o : objs) {
                if (o instanceof String) {
                    String result = (String) o;
                    log.info("type: String ; value: " + result);
                } else if (o instanceof Long) {
                    Long result = (Long) o;
                    log.info("type: Long ; value: " + result);
                } else if (o instanceof BigInteger) {
                    BigInteger result = (BigInteger) o;
                    log.info("type: BigInteger ; value: " + result);
                } else if (o instanceof Decimal) {
                    Decimal result = (Decimal) o;
                    log.info("type: Decimal ; value: " + result);
                } else if (o instanceof Integer) {
                    Integer result = (Integer) o;
                    log.info("type: Integer ; value: " + result);
                } else if (o instanceof Double) {
                    Double result = (Double) o;
                    log.info("type: Double ; value: " + result);
                } else if (o instanceof Float) {
                    Float result = (Float) o;
                    log.info("type: Float ; value: " + result);
                } else if (o instanceof Boolean) {
                    Boolean result = (Boolean) o;
                    log.info("type: Boolean ; value: " + result);
                } else if (o instanceof Date) {
                    Date result = (Date) o;
                    log.info("type: Date ; value: " + result);
                } else if (o instanceof LocalDate) {
                    LocalDate result = (LocalDate) o;
                    log.info("type: LocalDate ; value: " + result);
                } else if (o instanceof LocalDateTime) {
                    LocalDateTime result = (LocalDateTime) o;
                    log.info("type: LocalDateTime ; value: " + result);
                } else if (o == null) {
                    log.info("Object value is NULL !");
                } else {
                    log.info("type: UNKNOWN ; value: " + o);
                }
            }
            log.info(Console.LINE);
        }
        log.info("List<Object[]> print completed !");
    }

    public static List removeElement(List source, Object target) {
        source.removeIf(o -> o.equals(target));
        return source;
    }
}


