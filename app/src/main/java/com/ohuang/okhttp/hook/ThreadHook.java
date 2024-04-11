package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.Hook;

public class ThreadHook extends Hook {
    public ThreadHook() {
        super(Thread.class);
    }

    @Override
    public void hook() {
        hookAllMethod("setUncaughtExceptionHandler");
        hookAllMethod("setDefaultUncaughtExceptionHandler");
        hookAllMethod("setUncaughtExceptionPreHandler");
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        beforeLog(param);
        if (param.method.getName().equals("setDefaultUncaughtExceptionHandler")
                ||param.method.getName().equals("setUncaughtExceptionPreHandler")
                ||param.method.getName().equals("setUncaughtExceptionHandler")) {
            Object arg = param.args[0];
            param.args[0] = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {


                    Log.d(TAG, "uncaughtException: ");
                    Util.logStackTraceElement(TAG, e.getStackTrace());

                    ((Thread.UncaughtExceptionHandler) arg).uncaughtException(t, e);
                }
            };

        }
        return super.beforeMethod(param);

    }
}
