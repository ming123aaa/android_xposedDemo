package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.LogFile;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public class ReactInstanceManagerBuilderHook extends IHook {
    public static final String TAG = "ReactInstanceManagerBuilderHook";

    public ReactInstanceManagerBuilderHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return "com.facebook.react.ReactInstanceManagerBuilder";
    }

    @Override
    public void hook() {
        hookAllMethod("setBundleAssetName");
        hookAllMethod("setJSMainModulePath");
        hookAllMethod("setJSBundleFile");
    }

    public void log(String s) {
        Log.e(TAG, s);
//        LogFile.logDate(s);
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        log("beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" + Arrays.toString(param.args));

        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
