package com.ohuang.okhttp;

import android.util.Log;
import android.webkit.CookieManager;

import java.lang.reflect.Field;

public class Util {

    public static void tryCatch(Block block) {
        try {
            block.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印堆栈信息
     * @param tag
     */
    public static void logStackTraceElement(String tag) {
        StackTraceElement[] stackTraceElement = new Throwable().getStackTrace();
        Log.d(tag, "logStackTraceElement: ----------------start------------");
        for (int i = 0; i < stackTraceElement.length; i++) {
            Log.d(tag, "logStackTraceElement: " + stackTraceElement[i].toString());
        }
        Log.d(tag, "logStackTraceElement: ----------------end------------------");
    }

    /**
     * toString  反射获取所有参数
     * @param o
     * @return
     */
    public static String objectToString(Object o){
        if (o==null){
            return "";
        }
        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(aClass.getName()).append(":[");
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                stringBuilder.append(declaredField.getName()).append(":").append(declaredField.get(o)).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.append("]").toString();
    }






}
