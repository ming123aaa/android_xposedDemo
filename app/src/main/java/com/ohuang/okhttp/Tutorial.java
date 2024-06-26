package com.ohuang.okhttp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.ohuang.okhttp.hook.ActivityHook;
import com.ohuang.okhttp.hook.ContextWrapperHook;
import com.ohuang.okhttp.hook.CookieManagerHook;
import com.ohuang.okhttp.hook.SystemClassHook;
import com.ohuang.okhttp.hook.ThreadHook;
import com.ohuang.okhttp.hook.WebViewHook;
import com.ohuang.okhttp.hooks.ComposeHooks;
import com.ohuang.okhttp.util.PackageUtil;
import com.ohuang.okhttp.util.ProxySettingUtil;
import com.ohunag.xposedutil.AbstractHook;
import com.ohunag.xposedutil.Hook;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * @author a
 */
public class Tutorial implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    public static final String TAG = "TutorialHook";
    public static String packageName = "null";
    public static String processName = "null";
    public static boolean isInit = false;

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        packageName = lpparam.packageName;
        processName = lpparam.processName;
        Log.e(TAG, "handleLoadPackage: pkg=" + packageName + " processName=" + packageName);

        if (isInit) {
            return;
        }
        isInit = true;
        Log.e(TAG, "handleLoadPackage: hook pkg=" + packageName + " processName=" + packageName);

        new ActivityHook(lpparam.classLoader).hook();


        XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "beforeHookedMethod: application=" + param.thisObject);
                WebView.setWebContentsDebuggingEnabled(true);
//                    ProxySettingUtil.setProxyKKPlus((Context) param.thisObject, "113.219.239.172", 29115, 0);
            }
        });
        new ComposeHooks().handleLoadPackage(lpparam);

    }


    private static final int DEBUG_ENABLE_DEBUGGER = 0x1;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {


//        XposedBridge.hookAllMethods(Process.class,"start", new XC_MethodHook(){
//
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("-- beforeHookedMethod :" + param.args[1]);
//                int id = 5;
//                int flags = (Integer) param.args[id];
//// 修改类android.os.Process的start函数的第6个传入参数
//                if ((flags & DEBUG_ENABLE_DEBUGGER) == 0) {
//// 增加开启Android调试选项的标志
//                    flags |= DEBUG_ENABLE_DEBUGGER;
//                }
//                param.args[id] = flags;
//                if (BuildConfig.DEBUG) {
//                    XposedBridge.log("-- app debugable flags to 1 :" + param.args[1]);
//                }
//            }
//        });

    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }
}