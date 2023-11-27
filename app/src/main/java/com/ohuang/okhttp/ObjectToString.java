package com.ohuang.okhttp;

import android.app.Activity;
import android.view.View;

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

    public static String objectToString(Object o, int deep) {
        if (o == null) {
            return "\"null\"";
        }

        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"className\":").append("\""+aClass.getName()+"\"");
        stringBuilder.append(",").append("\"hashCode\":").append("\"").append(o.hashCode()).append("\"");
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {

                stringBuilder.append(",").append("\""+declaredField.getName()+"\"").append(":");

                if (declaredField.get(o) != null) {
                    Object o1 = declaredField.get(o);
                    Class clzz=o1.getClass();
                    String name = clzz.getName();

                    if (name.startsWith("java.lang")) {
                        stringBuilder.append("\""+declaredField.get(o)+"\"");
                    }else if(name.startsWith("android")||name.startsWith("java.util")||o1 instanceof View ||o1 instanceof Activity ){
                        stringBuilder.append("\""+name+"\"");
                    } else {
                        if (deep > 0) {
                            stringBuilder.append(objectToString(declaredField.get(o), deep - 1));
                        } else {
                            stringBuilder.append("\""+declaredField.get(o).getClass().getName()+"\"");
                        }
                    }
                } else {
                    stringBuilder.append("\"null\"");
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.append("}").toString();
    }
}
