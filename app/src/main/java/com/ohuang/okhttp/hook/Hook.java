package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.IHook;

import java.lang.reflect.Member;

public abstract class Hook extends IHook {

    public String className;

    public Hook(String className) {
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    protected boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(className + "Hook", "beforeMethod: thisObject=" + thisObject + "  method=" + method);
        return false;
    }

    @Override
    protected boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
