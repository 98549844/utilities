package util;


import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MapUtil {

	public static Map<Object, List<Map.Entry<String, String>>> groupElement(Map<String, String> m) {
		Map<Object, List<Map.Entry<String, String>>> result = m.entrySet().stream().collect(Collectors.groupingBy(c -> c.getValue()));
		return result;
	}


	public static Map getDuplicatedMap(List list) {
		List temp1 = new ArrayList();
		List temp2 = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (!temp1.contains(list.get(i))) {
				temp1.add(list.get(i));
			} else {
				temp2.add(list.get(i));
			}
		}
		Map map = new HashMap();
		map.put("nonDuplicate", temp1);
		map.put("duplicate", temp2);
		return map;
	}


	/**
	 * 历遍所有map元素
	 *
	 * @param map
	 */
	public static void iterateMapKeyset(Map map) {
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.println("Key: " + key.toString() + "   " + "value: " + val.toString());
		}
		Console.println("The Map size is : " + map.size(), Console.MAGENTA);
		Console.println("keyset : " + map.keySet(), Console.BLUE);
	}

	public void printKeyValuesOnMap(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			//System.out.println("Key: " + k + "   " + "value: " + v.toString());
			System.out.print("Key: ");
			Console.print(k, Console.BOLD, Console.BLUE);
			System.out.print("   ");
			System.out.print("value: ");
			Console.println(v.toString(), Console.BOLD, Console.BLUE);
		}
	}

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (NullUtil.isNull(map)) return null;
		Object obj = beanClass.getDeclaredConstructor().newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

	public static Map<?, ?> objectToMap(Object obj) {
		if (NullUtil.isNull(obj)) {
			return null;
		}
		return new org.apache.commons.beanutils.BeanMap(obj);
	}

	public static <T extends Object> T flushObject(T t, Map<String, Object> params) {
		if (NullUtil.isNull(params) || NullUtil.isNull(t)) {
			return t;
		}

		Class<?> clazz = t.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					// 获取属性的名字
					String name = fields[i].getName();
					Object value = params.get(name);
					if (NullUtil.isNotNull(value) && !"".equals(value)) {
						// 注意下面这句，不设置true的话，不能修改private类型变量的值
						fields[i].setAccessible(true);
						fields[i].set(t, value);
					}
				}
			} catch (Exception e) {
			}
		}
		return t;
	}

}
