package com.ohuang.okhttp.hook;

import android.util.Log;

import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public class ContextWrapperHook extends IHook {
    public static final String TAG = "ContextWrapperHook";



    @Override
    public String getClassName() {
        return "android.content.ContextWrapper";
    }

    @Override
    public void hook() {
//        hookAllMethod("startActivity");
//        hookAllMethod("sendBroadcast");
//        hookAllMethod("startService");
//        hookAllMethod("startForegroundService");
//        hookAllMethod("sendBroadcastAsUser");
//        hookAllMethod("sendOrderedBroadcastAsUser");
//        hookAllMethod("sendOrderedBroadcast");
//        hookAllMethod("sendStickyBroadcast");
//        hookAllMethod("sendStickyBroadcastAsUser");
//        hookAllMethod("sendBroadcastWithMultiplePermissions");
//        hookAllMethod("sendStickyOrderedBroadcastAsUser");
//        hookAllMethod("sendStickyOrderedBroadcast");
        hookAllMethod("getResources");
    }

    @Override
    protected boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method+" param="+ Arrays.toString(param.args));

//        if (method.getName().equals("startActivity")) {
//            Log.e(TAG, "beforeMethod: intent=" + param.args[0]);
//            Intent arg = (Intent) param.args[0];
//            Uri data = arg.getData();
//            Log.e(TAG, "beforeMethod: data=" + data);
//            if (data != null) {
//                String scheme = data.getScheme();
//                Log.e(TAG, "beforeMethod: scheme=" + scheme);
//                if (data.getScheme().equals("tmast")) {
//                    param.setResult(0);
//                    return true;
//                }
//            }
//
//        }
        return false;
    }

    @Override
    protected boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
