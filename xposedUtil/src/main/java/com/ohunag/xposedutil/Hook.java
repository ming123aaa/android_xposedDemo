package com.ohunag.xposedutil;

import android.util.Log;

import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public abstract class Hook extends IHook {

    public String className;
    public String TAG="hook";

    public Hook(Class<?> mClass) {
        super(mClass);
    }

    public Hook(String className,ClassLoader classLoader){
        super(classLoader);
        this.className = className;
        TAG=className + "Hook";
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
//        Member method = param.method;
//        Object thisObject = param.thisObject;
//        log( "beforeMethod: thisObject=" + thisObject + "  method=" + method+" param="+ Arrays.toString(param.args));

        return false;
    }

    public void log(String s){
        Log.e(TAG, s);

    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
