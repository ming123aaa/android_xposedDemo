package com.ohuang.okhttp.hook;

import android.provider.Settings;
import android.util.Log;

import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;

public class SettingsSecureHook extends IHook {
    public static final String TAG="SettingsSecureHook";
    public SettingsSecureHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {

        return Settings.Secure.class.getName();
    }

    @Override
    public void hook() {
        hookAllMethod();
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method+" param="+ Arrays.toString(param.args)+" return="+param.getResult());
        return false;
    }
}
