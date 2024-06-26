package com.ohuang.okhttp.hooks;

import android.util.Log;

import com.ohunag.xposedutil.Hook;

import java.lang.reflect.Member;
import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class GameSdkHooks implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        new Hook("com.ohuang.basesdk.GameSdkBridge", lpparam.classLoader) {
            @Override
            public boolean beforeMethod(MethodHookParam param) {
                beforeLog(param);
//                if (param.method.getName().equals("singlePlayerGamesCheck")) {
//                    Object arg = param.args[0];
//                    Util.tryCatch(() -> {
//                        arg.getClass().getDeclaredMethod("onSuccess", Object.class).invoke(arg, true);
//                    });
//                    param.setResult(null);
//                }

                return super.beforeMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "afterHookedMethod: " + param.method.getName() + " result=" + param.getResult());
                super.afterHookedMethod(param);
            }

            @Override
            public void hook() {
                hookAllMethod();
            }
        }.hook();
        new Hook("com.ohuang.basesdk.SdkManager", lpparam.classLoader) {


            @Override
            public boolean beforeMethod(MethodHookParam param) {
                beforeLog(param);
                return super.beforeMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "afterHookedMethod: " + param.method.getName() + " result=" + param.getResult());
                super.afterHookedMethod(param);
            }

            @Override
            public void hook() {
                hookAllMethod();
            }
        }.hook();

        new Hook("com.fengxia.fengxiamesdk.FengxiaMESDK", lpparam.classLoader) {

            @Override
            public void hook() {
                try {
                    hookAllMethod();
                } catch (Exception e) {
                }
            }

            @Override
            public boolean beforeMethod(MethodHookParam param) {
                Member method = param.method;
                Object thisObject = param.thisObject;
                log("beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" + Arrays.toString(param.args));
                return super.beforeMethod(param);
            }
        }.hook();


    }
}
