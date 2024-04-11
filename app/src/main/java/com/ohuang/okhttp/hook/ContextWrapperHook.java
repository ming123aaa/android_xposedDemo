package com.ohuang.okhttp.hook;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public class ContextWrapperHook extends IHook {
    public static final String TAG = "ContextWrapperHook";

    public ContextWrapperHook(ClassLoader classLoader) {
        super(classLoader);
    }


    @Override
    public String getClassName() {
        return "android.content.ContextWrapper";
    }

    @Override
    public void hook() {
        hookAllMethod("startActivity");
//        hookAllMethod("getPackageManager");
//        hookAllMethod("sendBroadcast");
        hookAllMethod("startService");
        hookAllMethod("startForegroundService");
//        hookAllMethod("sendBroadcastAsUser");
//        hookAllMethod("sendOrderedBroadcastAsUser");
//        hookAllMethod("sendOrderedBroadcast");
//        hookAllMethod("sendStickyBroadcast");
//        hookAllMethod("sendStickyBroadcastAsUser");
//        hookAllMethod("sendBroadcastWithMultiplePermissions");
//        hookAllMethod("sendStickyOrderedBroadcastAsUser");
//        hookAllMethod("sendStickyOrderedBroadcast");
//        hookAllMethod("getResources");
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
           Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method+" param="+ Arrays.toString(param.args));



        if (method.getName().equals("startActivity")) {
            Log.e(TAG, "beforeMethod: intent=" + param.args[0]);
            Intent arg = (Intent) param.args[0];
            Uri data = arg.getData();
            Log.e(TAG, "beforeMethod: data=" + data);
            if (data != null) {
                String scheme = data.getScheme();
                Log.e(TAG, "beforeMethod: scheme=" + scheme);
            }
            Log.d(TAG, "beforeMethod: "+arg.getExtras().toString());

        }
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
