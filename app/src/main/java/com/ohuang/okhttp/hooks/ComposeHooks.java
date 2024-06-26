package com.ohuang.okhttp.hooks;

import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.Hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ComposeHooks implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        new Hook("androidx.compose.runtime.snapshots.Snapshot", lpparam.classLoader){
            @Override
            public void hook() {
               hookAllMethod("makeCurrent");
               hookAllMethod("restoreCurrent");
            }

            @Override
            public boolean beforeMethod(MethodHookParam param) {
                beforeLog(param);

                return super.beforeMethod(param);
            }
        }.hook();
    }
}
