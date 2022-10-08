package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.IHook;

import java.lang.reflect.Member;

public class ActivityHook extends IHook {
    public static final String TAG = "ActivityHook";

    @Override
    public String getClassName() {
        return "android.app.Activity";
    }

    @Override
    public void hook() {
        hookAllMethod("startActivityForResult");
    }

    @Override
    protected boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method);
        return false;
    }

    @Override
    protected boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
