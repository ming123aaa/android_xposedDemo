package com.ohuang.okhttp;

import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void tryCatch(Block block) {
        try {
            block.invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印堆栈信息
     *
     * @param tag
     */
    public static void logStackTraceElement(String tag) {
        StackTraceElement[] stackTraceElement = new Throwable().getStackTrace();
        Log.w(tag, "logStackTraceElement: ----------------start------------");
        for (int i = 0; i < stackTraceElement.length; i++) {
            Log.w(tag, "logStackTraceElement: " + stackTraceElement[i].toString());
        }
        Log.w(tag, "logStackTraceElement: ----------------end------------------");
    }

    public static String getLogStackTraceElement(String tag) {
        StackTraceElement[] stackTraceElement = new Throwable().getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append(tag).append(":").append("logStackTraceElement: ----------------start------------");
        for (int i = 0; i < stackTraceElement.length; i++) {
            stringBuilder.append("\n").append(tag).append(":").append("logStackTraceElement: ").append(stackTraceElement[i].toString());
        }
        stringBuilder.append("\n").append(tag).append(":").append("logStackTraceElement: ----------------end------------------");
        return stringBuilder.toString();
    }

    public static void logStackTraceElement(String tag, StackTraceElement[] stackTraceElement) {
        Log.w(tag, "logStackTraceElement: ----------------start------------");
        for (int i = 0; i < stackTraceElement.length; i++) {
            Log.w(tag, "logStackTraceElement: " + stackTraceElement[i].toString());
        }
        Log.w(tag, "logStackTraceElement: ----------------end------------------");
    }

    /**
     * toString  反射获取所有参数
     *
     * @param o
     * @return
     */
    public static String objectToString(Object o) {
        if (o == null) {
            return "";
        }
        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
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


    public static boolean superClassStartWith(Class clazz, String start) {
        Class zz = clazz;
        while (zz != null && zz != Object.class) {
            if (zz.getName().startsWith(start)) {
                return true;
            }
            zz = zz.getSuperclass();
        }
        return false;
    }


    public static String toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(); // 获取当前时间
        String format = sdf.format(date);
        return format;
    }


    public static void log(String s) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/Tutorial.log", s);
    }
    public static void log(String s,String fileName) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/"+fileName, s);
    }


}
