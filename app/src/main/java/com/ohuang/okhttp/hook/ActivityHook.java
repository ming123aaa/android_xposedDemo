package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohuang.okhttp.LogFile;
import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public class ActivityHook extends IHook {
    public static final String TAG = "ActivityHook";

    public ActivityHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return "android.app.Activity";
    }

    @Override
    public void hook() {
//        hookAllMethod();
        hookAllMethod("startActivityForResult");
        hookAllMethod("startService");
        hookAllMethod("startForegroundService");
        hookAllMethod("onActivityResult");
        hookAllMethod("onCreate");
        hookAllMethod("onStart");
        hookAllMethod("onResume");
        hookAllMethod("onPause");
        hookAllMethod("onStop");
        hookAllMethod("onDestroy");
        hookAllMethod("onRestart");
        hookAllMethod("onNewIntent");
        hookAllMethod("onSaveInstanceState");
        hookAllMethod("onWindowFocusChanged");
        hookAllMethod("onConfigurationChanged");
        hookAllMethod("onUserLeaveHint");
        hookAllMethod("onUserInteraction");


    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
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
