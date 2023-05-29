package com.util;


import com.generator.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PoUtil {
    private static final Logger log = LogManager.getLogger(PoUtil.class);



    public static void main(String[] args) {
      //  DataGenerator.getTestEntity();
        iteratePoSetterValue(DataGenerator.generateUsers().get(0));
        iteratePoGetterValue(DataGenerator.generateUsers().get(0));
    }

    public static void iteratePoGetterValue(Object object) {
        Class<? extends Object> c = object.getClass();
        Method[] m = c.getDeclaredMethods();

        for (Method method : m) {
            if (method.getName().contains("get")) {
                try {
                    System.out.println("Method：" + method.getName() + " Value：" + method.invoke(object));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void iteratePoSetterValue(Object object) {
        Class<?> c = object.getClass();
        Method[] m = c.getDeclaredMethods();
        for (Method method : m) {
            if (method.getName().contains("set")) {
                try {
                    System.out.println("Method：" + method.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public List<String> iteratePoByName(Object object, String name) {
        Class<?> c = object.getClass();
        Method[] m = c.getDeclaredMethods();
        List<String> methodList = new ArrayList<String>();
        for (int i = 0; i < m.length; i++) {
            if (m[i].getName().contains(name)) {
                try {
                    System.out.println("Method：" + m[i].getName());
                    methodList.add(m[i].getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return methodList;
    }

    // full path pkgName
    public static void getMethodInfo(String pkgName) {
        try {
            Class clazz = Class.forName(pkgName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                System.out.println("methodName:" + methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    String parameterName = clas.getName();
                    System.out.println("dataType:" + parameterName);
                }
                System.out.println("*****************************");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param methodName 方法名
     * @param object 传入的对像
     * @param value 传入的value
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void callMethodByName(String methodName, Object object, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //new Class[]{String.class} : String.class是参数类型(dataType)
        object.getClass().getMethod(methodName, new Class[]{String.class}).invoke(object, value);
    }
}
