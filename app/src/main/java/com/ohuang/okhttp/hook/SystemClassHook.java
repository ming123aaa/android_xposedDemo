package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohunag.xposedutil.IHook;

import java.util.Arrays;

public class SystemClassHook extends IHook {
    public static final String TAG="SystemClassHook";
    public SystemClassHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return System.class.getName();
    }

    @Override
    public void hook() {
        hookAllMethod("setProperty");
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        String name = param.method.getName();
        if ("setProperty".equals(name)){
            Log.d(TAG, "beforeMethod: setProperty("+ Arrays.toString(param.args)+")");
        }
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
