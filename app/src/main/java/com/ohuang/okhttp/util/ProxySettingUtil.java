package com.ohuang.okhttp.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.ArrayMap;
import android.webkit.WebView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/* loaded from: classes2.dex
 * setProxy(WebView webView, String str, int i, String str2)  设置代理
 * revertProxyKKPlus(WebView )  取消代理
 * 
 */
public class ProxySettingUtil {

    private static final String APPLICATION_NAME = "android.app.Application";
    public static boolean setProxyKKPlus(Context context,String str, int i, int type) {

        Context applicationContext = context;
        if (type == 0) {
            System.setProperty("http.proxyHost", str);
            System.setProperty("http.proxyPort", i + "");
            System.setProperty("https.proxyHost", str);
            System.setProperty("https.proxyPort", i + "");
        } else {
            System.setProperty("socksProxySet", "true");
            System.setProperty("socksProxyHost", str);
            System.setProperty("socksProxyPort", i + "");
        }
        try {
            Field field = Class.forName(APPLICATION_NAME).getField("mLoadedApk");
            field.setAccessible(true);
            Object obj = field.get(applicationContext);
            Field declaredField = Class.forName("android.app.LoadedApk").getDeclaredField("mReceivers");
            declaredField.setAccessible(true);
            for (ArrayMap arrayMap : ((ArrayMap<Object, ArrayMap>) declaredField.get(obj)).values()) {
                for (Object obj2 : arrayMap.keySet()) {
                    Class<?> cls = obj2.getClass();
                    if (cls != null && cls.getName().contains("ProxyChangeListener")) {
                        cls.getDeclaredMethod("onReceive", Context.class, Intent.class).invoke(obj2, applicationContext, new Intent("android.intent.action.PROXY_CHANGE"));
                    }
                }
            }
            return true;
        } catch (ClassNotFoundException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            stringWriter.toString();
            return false;
        } catch (IllegalAccessException e2) {
            StringWriter stringWriter2 = new StringWriter();
            e2.printStackTrace(new PrintWriter(stringWriter2));
            stringWriter2.toString();
            return false;
        } catch (IllegalArgumentException e3) {
            StringWriter stringWriter3 = new StringWriter();
            e3.printStackTrace(new PrintWriter(stringWriter3));
            stringWriter3.toString();
            return false;
        } catch (NoSuchFieldException e4) {
            StringWriter stringWriter4 = new StringWriter();
            e4.printStackTrace(new PrintWriter(stringWriter4));
            stringWriter4.toString();
            return false;
        } catch (NoSuchMethodException e5) {
            StringWriter stringWriter5 = new StringWriter();
            e5.printStackTrace(new PrintWriter(stringWriter5));
            stringWriter5.toString();
            return false;
        } catch (InvocationTargetException e6) {
            StringWriter stringWriter6 = new StringWriter();
            e6.printStackTrace(new PrintWriter(stringWriter6));
            stringWriter6.toString();
            return false;
        }
    }



    public static boolean revertProxyKKPlus(WebView webView, int type) {
        Context applicationContext = webView.getContext().getApplicationContext();

        Properties properties = System.getProperties();
        if (type == 0) {
            properties.remove("http.proxyHost");
            properties.remove("http.proxyPort");
            properties.remove("https.proxyHost");
            properties.remove("https.proxyPort");
        } else {
            System.setProperty("socksProxySet", "false");
            properties.remove("socksProxyHost");
            properties.remove("socksProxyPort");
        }
        try {
            Field field = Class.forName(APPLICATION_NAME).getField("mLoadedApk");
            field.setAccessible(true);
            Object obj = field.get(applicationContext);
            Field declaredField = Class.forName("android.app.LoadedApk").getDeclaredField("mReceivers");
            declaredField.setAccessible(true);
            for (ArrayMap arrayMap : ((ArrayMap<Object, ArrayMap>) declaredField.get(obj)).values()) {
                for (Object obj2 : arrayMap.keySet()) {
                    Class<?> cls = obj2.getClass();
                    if (cls.getName().contains("ProxyChangeListener")) {
                        cls.getDeclaredMethod("onReceive", Context.class, Intent.class).invoke(obj2, applicationContext, new Intent("android.intent.action.PROXY_CHANGE"));
                    }
                }
            }
            return true;
        } catch (ClassNotFoundException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            stringWriter.toString();
            return false;
        } catch (IllegalAccessException e2) {
            StringWriter stringWriter2 = new StringWriter();
            e2.printStackTrace(new PrintWriter(stringWriter2));
            stringWriter2.toString();
            return false;
        } catch (IllegalArgumentException e3) {
            StringWriter stringWriter3 = new StringWriter();
            e3.printStackTrace(new PrintWriter(stringWriter3));
            stringWriter3.toString();
            return false;
        } catch (NoSuchFieldException e4) {
            StringWriter stringWriter4 = new StringWriter();
            e4.printStackTrace(new PrintWriter(stringWriter4));
            stringWriter4.toString();
            return false;
        } catch (NoSuchMethodException e5) {
            StringWriter stringWriter5 = new StringWriter();
            e5.printStackTrace(new PrintWriter(stringWriter5));
            stringWriter5.toString();
            return false;
        } catch (InvocationTargetException e6) {
            StringWriter stringWriter6 = new StringWriter();
            e6.printStackTrace(new PrintWriter(stringWriter6));
            stringWriter6.toString();
            return false;
        }
    }
}