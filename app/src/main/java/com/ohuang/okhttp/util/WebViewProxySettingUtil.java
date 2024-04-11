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
public class WebViewProxySettingUtil {
    private static final String APPLICATION_NAME = "android.app.Application";

    /**
     * @param webView
     * @param str     serverAddress
     * @param i       serverPort
     * @param str2    可以为空
     * @param type    type=0 为http和https  其它值为socket代理
     * @return
     */
    public static boolean setProxy(WebView webView, String str, int i, String str2, int type) {
        if (Build.VERSION.SDK_INT <= 15) {
            return setProxyICS(webView, str, i);
        }
        if (Build.VERSION.SDK_INT <= 18) {
            return setProxyJB(webView, str, i);
        }
        if (str2 == null) {
            str2 = APPLICATION_NAME;
        }
        return setProxyKKPlus(webView, str, i, str2, type);
    }

    private static boolean setProxyICS(WebView webView, String str, int i) {
        try {
            Class.forName("android.webkit.JWebCoreJavaBridge").getDeclaredMethod("updateProxy", Class.forName("android.net.ProxyProperties")).invoke(getFieldValueSafely(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), getFieldValueSafely(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), getFieldValueSafely(Class.forName("android.webkit.WebView").getDeclaredField("mWebViewCore"), webView))), Class.forName("android.net.ProxyProperties").getConstructor(String.class, Integer.TYPE, String.class).newInstance(str, Integer.valueOf(i), null));
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean setProxyJB(WebView webView, String str, int i) {
        try {
            Class.forName("android.webkit.JWebCoreJavaBridge").getDeclaredMethod("updateProxy", Class.forName("android.net.ProxyProperties")).invoke(getFieldValueSafely(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), getFieldValueSafely(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), getFieldValueSafely(Class.forName("android.webkit.WebViewClassic").getDeclaredField("mWebViewCore"), Class.forName("android.webkit.WebViewClassic").getDeclaredMethod("fromWebView", Class.forName("android.webkit.WebView")).invoke(null, webView)))), Class.forName("android.net.ProxyProperties").getConstructor(String.class, Integer.TYPE, String.class).newInstance(str, Integer.valueOf(i), null));
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean setProxyKKPlus(WebView webView, String str, int i, String str2, int type) {

        Context applicationContext = webView.getContext().getApplicationContext();
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
            Field field = Class.forName(str2).getField("mLoadedApk");
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

    private static Object getFieldValueSafely(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Object obj2 = field.get(obj);
        field.setAccessible(isAccessible);
        return obj2;
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