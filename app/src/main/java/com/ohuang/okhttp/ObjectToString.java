package com.ohuang.okhttp;

import java.lang.reflect.Field;

public class ObjectToString {

    public static String toString(Object o){
        StringBuilder stringBuilder=new StringBuilder();
        Class<?> aClass = o.getClass();
        stringBuilder.append("obj=").append(aClass.getName()).append("@").append(o.hashCode()).append("{");
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                Object o1 = declaredField.get(o);
                stringBuilder.append(declaredField.getName()).append("=").append(o1).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return stringBuilder.toString();
    }
}
