package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class NullUtil {
    private static final Logger log = LogManager.getLogger(NullUtil.class);

    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof Collection) {
            return isEmpty((Collection<?>) object);
        } else if (object instanceof Map) {
            return isEmpty((Map<?, ?>) object);
        }
        return isNull(object) || "".equals(object);
    }

    public static boolean isEmpty(Object[] object) {
        return isNull(object) || object.length < 1;
    }

    public static boolean isEmpty(String text) {
        return isNull(text) || text.trim().isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(Object[] object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            String s = (String) object;
            if (s.isEmpty()) {
                return true;
            }
        } else if (object instanceof List) {
            List ls = (List) object;
            return ls.isEmpty();
        } else if (object instanceof Map) {
            Map m = (Map) object;
            return m.keySet().isEmpty();
        }

        return false;
    }

    public static boolean isNonNull(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof String) {
            String s = (String) object;
            if (s.isEmpty()) {
                return false;
            }
        } else if (object instanceof List) {
            List ls = (List) object;
            if (ls.isEmpty()) {
                return false;
            }
        } else if (object instanceof Map) {
            Map m = (Map) object;
            if (m.keySet().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasLength(String text) {
        return text != null && text.length() > 0;
    }

    public static boolean hasText(String text) {
        if (!hasLength(text)) {
            return false;
        }
        int strLen = text.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContain(String textToSearch, String substring) {
        if (hasLength(textToSearch) && hasLength(substring) && textToSearch.contains(substring)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAssignable(Class<?> superType, Class<?> subType) {
        if (isNonNull(superType) && isNonNull(subType) && superType.isAssignableFrom(subType)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInstanceOf(Class<?> type, Object object) {
        if (isNonNull(type) && type.isInstance(object)) {
            return true;
        } else {
            return false;
        }
    }


}
