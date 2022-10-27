package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public abstract class Hook extends IHook {

    public String className;

    public Hook(String className) {
        this.className = className;
    }

    public Hook(String className,ClassLoader classLoader){
        super(classLoader);
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
        Log.e(className + "Hook", "beforeMethod: thisObject=" + thisObject + "  method=" + method+" param="+ Arrays.toString(param.args));
        return false;
    }

    @Override
    protected boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
