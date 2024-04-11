package com.ohuang.okhttp.hook;

import android.util.Log;
import android.webkit.WebView;

import com.ohuang.okhttp.LogFile;
import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.Hook;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WebViewHook extends IHook {
    public static final String TAG = "WebViewHook";
    public Set<String> javascriptInterfaces = new HashSet<>();

    public
    WebViewHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return WebView.class.getName();
    }

    @Override
    public void hook() {
        hookAllMethod("setWebContentsDebuggingEnabled");
        hookAllMethod("loadUrl");
        hookAllMethod("evaluateJavascript");
        hookMethod("addJavascriptInterface", Object.class, String.class);
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        if ("setWebContentsDebuggingEnabled".equals(param.method.getName())) {
            param.args[0] = true;
        } else if ("addJavascriptInterface".equals(param.method.getName())) {
            Object arg = param.args[0];
            Object name = param.args[1];
            if (arg != null) {
                String className = arg.getClass().getName();
                if (!javascriptInterfaces.contains(className)) {
                    javascriptInterfaces.add(className);
                    new Hook(className, mClassLoader) {

                        @Override
                        public void hook() {
                            TAG = WebViewHook.TAG + "/jsname:" + name + "/" + TAG;
                            hookAllMethod();
                        }
                    }.hook();
                }
            }
        } else if ("evaluateJavascript".equals(param.method.getName())) {
            Util.logStackTraceElement(TAG + "-evaluateJavascript");
//            LogFile.logDate(TAG, param.args[0].toString());
        } else if ("loadUrl".equals(param.method.getName())) {
            LogFile.logDate(TAG, "loadUrl="+param.args[0].toString());
        }
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" + Arrays.toString(param.args));
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
