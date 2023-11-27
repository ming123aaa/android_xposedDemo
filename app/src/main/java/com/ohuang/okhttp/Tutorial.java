package com.ohuang.okhttp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ohuang.okhttp.hook.ActivityHook;
import com.ohuang.okhttp.hook.ContextWrapperHook;
import com.ohuang.okhttp.util.PackageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        new ContextWrapperHook(lpparam.classLoader).hook();

//        new WebViewHook(lpparam.classLoader).hook();

//        new TouchEventViewHook(lpparam.classLoader).hook();
//        new TouchEventViewGroupHook(lpparam.classLoader).hook();
//        new CookieManagerHook(lpparam.classLoader).hook();
//        new ReactInstanceManagerBuilderHook(lpparam.classLoader).hook();

//      new SettingsSecureHook(lpparam.classLoader).hook();
//      new SettingsSystemHook(lpparam.classLoader).hook();

//       new  DeviceInfoHook().hook(lpparam.classLoader);

        XposedHelpers.findAndHookMethod(Application.class,"onCreate",new XC_MethodHook(){

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "application onCreate");
                PackageUtil.hookIPackManager((Context) param.thisObject);
            }
        });
    }



    public boolean superClassStartWith(Class clazz,String start){
        Class zz=clazz;
        while (zz!=null&&zz!=Object.class){
            if (zz.getName().startsWith(start)){
                return true;
            }
            zz=zz.getSuperclass();
        }
        return false;
    }


    public static String toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(); // 获取当前时间
        String format = sdf.format(date);
        return format;
    }

    public void logStackTraceElement(String tag) {
        StackTraceElement[] stackTraceElement = getStackTraceElement();
        Log.d(tag, "logStackTraceElement: ----------------start------------");
        for (int i = 0; i < stackTraceElement.length; i++) {
            Log.d(tag, "logStackTraceElement: " + stackTraceElement[i].toString());
        }
        Log.d(tag, "logStackTraceElement: ----------------end------------------");
    }

    public StackTraceElement[] getStackTraceElement() {

        return new Throwable().getStackTrace();
    }

    public static void log(String s) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/Tutorial.log", s);
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