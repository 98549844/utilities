package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings(("unchecked"))
public class SetUtil {
    private static final Logger log = LogManager.getLogger(SetUtil.class.getName());
    
    
    	public static Set arrayIntToHashSet(int[] nums) {
		Set set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
		return set;
	}

    public static List setToList(Set set) {
        List ls = new ArrayList();
        if (NullUtil.isNull(set)) {
            log.error("Set is null");
            return ls;
        }
        for (Object o : set) {
            ls.add(o);
        }
        return ls;
    }

    public static <T extends Number> void iterateSet(Set<T> tSet) {
        Iterator<T> iterator = tSet.iterator();
        while (iterator.hasNext()) {
            log.info("1: " + iterator.next());
        }
    }

    public static Object getSetFirstValue(Set set) {
        if (NullUtil.isNull(set)) {
            log.error("Set is null");
            return null;
        }
        return set.iterator().next();
    }

    public static boolean compareSet(Set<T> set1, Set<T> set2) {
        return Arrays.deepEquals(new Set[]{set1}, new Set[]{set2});
    }
}
