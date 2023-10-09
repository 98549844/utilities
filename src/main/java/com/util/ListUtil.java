package com.util;

import com.generator.DataGenerator;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
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


    public static int getMax(List<Integer> integerList) {
        return NullUtil.isNull(integerList) ? 0 : Collections.max(integerList);
    }

    public static int getMin(List<Integer> integerList) {
        return NullUtil.isNull(integerList) ? 0 : Collections.min(integerList);
    }

    /**
     * int set convert to list
     *
     * @param nums
     * @return
     */
    public static List arrayIntToList(int[] nums) {
        return Arrays.asList(ArrayUtils.toObject(nums));
    }

    /**
     * int set convert to list
     *
     * @param i
     * @return
     */
    public static List intArrayToList(int[] i) {
        return Arrays.stream(i).boxed().collect(Collectors.toList());
    }


    /**
     * 检查元素有没有重复
     *
     * @param ls
     * @return
     */
    public static boolean isDuplicate(List ls) {
        HashSet<Integer> hashSet = new HashSet<>(ls);
        return ls.size() != hashSet.size();
    }


    /**
     * 比较两个list是否完全相同, 兼容位置不一样
     *
     * @param
     * @return
     */
    public static boolean compareList(List ls1, List ls2) {
        boolean result = false;
        int size1 = ls1.size();
        int size2 = ls2.size();

        Collections.sort(ls1); //升序排序
        Collections.sort(ls2); //升序排序

        if (size1 == size2) {
            for (int i = 0; i < size1; i++) {
                if (ls1.get(i).equals(ls2.get(i))) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }


    //根据长度把list拆分
    public static List<List<T>> splitList(List<T> list, int len) {
        if (NullUtil.isNull(list) || list.isEmpty() || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }


    /**
     * 去除list里重复元素
     *
     * @param list
     * @return
     */
    public static List<T> removeDuplicate(List<T> list) {
        if (NullUtil.isNull(list)) {
            log.error("list is empty !");
            return null;
        }
        Set<T> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }


    public static List getResultList(List<String> ls, String criteria, boolean include) {
        List result = new ArrayList();
        if (include) {
            // list 内容含有criteria
            if (!ls.contains(criteria)) {
                return result;
            }
            for (String s : ls) {
                if (s.contains(criteria)) {
                    result.add(s);
                }
            }
        } else {
            // criteria含有list 内容
            for (String s : ls) {
                if (criteria.contains(s)) {
                    result.add(s);
                }
            }
        }
        return result;
    }

    /**
     * 比较两个list, 把不相同的找出来
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static Map getNonDeduplicateElements(List ls1, List ls2) {
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
        if (!ls.isEmpty()) {
            map.put(ListUtil.LIST_2, ls);
        }

        if (!map.isEmpty()) {
            // log.info("Compare result: NOT EQUAL !");
            System.out.println("Compare result: NOT EQUAL !");
        } else {
            // log.info("Compare result: EQUAL !");
            System.out.println("Compare result: EQUAL !");
        }

        return map;
    }


    /**
     * 比较两个list, 把不相同的找出来, 忽略空格" "
     *
     * @param ls1
     * @param ls2
     * @return
     */
    public static Map getNonDeduplicateElementsIgnoreSpace(List ls1, List ls2) {

        List list1 = new ArrayList();
        List list2 = new ArrayList();

        for (int i = 0; i < ls1.size(); i++) {
            list1.add(ls1.get(i).toString().replace(" ", ""));
        }

        for (int i = 0; i < ls2.size(); i++) {
            list2.add(ls2.get(i).toString().replace(" ", ""));
        }

        return getNonDeduplicateElements(list1, list2);
    }


    /**
     * @param list
     * @return 重复元素list
     */
    public static List getDuplicated(List list) {
        Set<T> listSet = new HashSet<>(list);
        Collection<T> sub = CollectionUtils.subtract(list, listSet);
        HashSet<T> hSet = new HashSet<>(sub);
        List ls = new ArrayList<>(hSet);
        return ls;
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
        Collections.reverse(list);
        return list;
    }

    /**
     * 将list转换成线性安全
     *
     * @param ls
     * @return
     */
    public static List getSynchronizedList(List ls) {
        return Collections.synchronizedList(ls);
    }

    /**
     * 将list转换成不可以修改list
     *
     * @param ls
     * @return
     */
    public static List getUnmodifiableList(List ls) {
        return Collections.unmodifiableList(ls);
    }

    public static void main(String[] args) {
        List<Integer> ls1 = new ArrayList<>();
        List ls2 = new ArrayList<>();
        // List<String> ls3 = new ArrayList<>();

        ls1.add(2);
        ls1.add(4);
        ls1.add(9);
        ls1.add(5);
        Collections.sort(ls1);

        ls2.add(4);
        ls2.add(9);
        ls2.add(9);
        ls2.add(9);
        ls2.add(2);
        ls2.add(5);
        ls2.add(5);

        List ls3 = getDuplicated(ls2);

        for (int i = 0; i < ls3.size(); i++) {
            System.out.println(ls3.get(i));
        }


    }

    /**
     * 随机选取list的元素(重覆)
     * percentage => 0% ~ 100%
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List getRandomListByPercent(List ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }
        int percentItems = ls.size() * percentage / 100;

        List resultLs = new ArrayList();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < percentItems; i++) {
            int n = random.nextInt(ls.size());
            resultLs.add(ls.get(n));
        }
        return resultLs;
    }

    /**
     * 随机选取list里面的元素(不重覆)
     * percentage => 0% ~ 100%
     *
     * @param ls
     * @param percentage
     * @return
     */
    public static List getRandomListByPercentNonRepeatable(List ls, Integer percentage) {
        if (NullUtil.isNull(percentage)) {
            return null;
        }

        int percentItems = ls.size() * percentage / 100;

        List resultLs = new ArrayList();
        SecureRandom random = new SecureRandom();
        while (resultLs.size() < percentItems) {
            int n = random.nextInt(ls.size());
            if (!resultLs.contains(ls.get(n))) {
                resultLs.add(ls.get(n));
            }
        }
        return resultLs;
    }


    //--------------------------------- 下面方法没有double check -----------------------------------------------


    /**
     * just a sample
     * need override
     */
    public static void sortList(List<Users> ls) {
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
        String result = list.isEmpty() ? "" : sb.substring(0, sb.toString().length() - 1);
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

    public static List<T> removeElementByLooping(List<T> ls, T removalVal) {
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

    /** Listb删除target的object并返回删除后的list
     * @param source
     * @param target
     * @return
     */
    public static List removeElement(List source, Object target) {
        source.removeIf(o -> o.equals(target));
        return source;
    }
}


