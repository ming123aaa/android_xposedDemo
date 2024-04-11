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
//        new ContextWrapperHook(lpparam.classLoader).hook();

//        new WebViewHook(lpparam.classLoader).hook();

//        new TouchEventViewHook(lpparam.classLoader).hook();
//        new TouchEventViewGroupHook(lpparam.classLoader).hook();
//        new CookieManagerHook(lpparam.classLoader).hook();
//        new ReactInstanceManagerBuilderHook(lpparam.classLoader).hook();

//      new SettingsSecureHook(lpparam.classLoader).hook();
//      new SettingsSystemHook(lpparam.classLoader).hook();

//       new  DeviceInfoHook().hook(lpparam.classLoader);
        if (packageName.equals("tv.danmaku.bili")) {
            XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.i(TAG, "application onCreate");
                    PackageUtil.hookIPackManager((Context) param.thisObject);
                }
            });
        } else {
            XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d(TAG, "beforeHookedMethod: application=" + param.thisObject);
                    WebView.setWebContentsDebuggingEnabled(true);
//                    ProxySettingUtil.setProxyKKPlus((Context) param.thisObject, "113.219.239.172", 29115, 0);
                }
            });
        }

       new ThreadHook().hook();


        new Hook(android.os.Process.class) {
            @Override
            public void hook() {
                hookAllMethod("killProcess");
            }

            @Override
            public boolean beforeMethod(MethodHookParam param) {
                beforeLog(param);
                Util.logStackTraceElement(TAG);
                return super.beforeMethod(param);
            }
        }.hook();

        new Hook(System.class){

            @Override
            public void hook() {
               hookAllMethod("exit");
            }

            @Override
            public boolean beforeMethod(MethodHookParam param) {
                beforeLog(param);
                Util.logStackTraceElement(TAG);
                return super.beforeMethod(param);
            }
        }.hook();



        new Hook("com.ohuang.basesdk.GameSdkBridge", lpparam.classLoader) {
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

        new AbstractHook("com.fengxia.fengxiamesdk.FengxiaMESDK", lpparam.classLoader) {
            @Override
            public IHook hookAbstract(Class aclass, ClassLoader classLoader) {
                return new Hook(aclass) {
                    @Override
                    public void hook() {
                        hookAllMethod();
                    }

                    @Override
                    public boolean beforeMethod(MethodHookParam param) {
                        Member method = param.method;
                        Object thisObject = param.thisObject;
                        log("beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" + Arrays.toString(param.args));
                        return super.beforeMethod(param);
                    }
                };
            }

            @Override
            public void hook() {
                super.hook();
            }
        }.hook();

//        new SystemClassHook(lpparam.classLoader).hook();
//        new CookieManagerHook(lpparam.classLoader).hook();
//        new WebViewHook(lpparam.classLoader).hook();

//        new Hook("dalvik.system.BaseDexClassLoader", lpparam.classLoader){
//
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//
//                if (param.method.getName().equals("findClass")){
//                    String arg = (String) param.args[0];
//
//                    if (arg.startsWith("net.ali213.catrepair.R")){
//                        Log.e(TAG, "findClass: "+arg);
//                        logStackTraceElement(TAG);
//                    }
//                    if (arg.startsWith("com.ohuang")){
//                        Log.d(TAG, "findClass: "+arg);
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void hook() {
//                hookMethod("findClass", String.class);
//            }
//        }.hook();
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