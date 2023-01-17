package com.ohuang.okhttp;

import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;

import com.ohuang.okhttp.hook.ActivityHook;
import com.ohunag.xposedutil.Hook;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * @author a
 */
public class Tutorial implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    public static final String TAG = "Tutorial";
    public static String packageName = "null";
    public static String processName = "null";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        packageName = lpparam.packageName;
        processName = lpparam.processName;
        Log.e(TAG, "handleLoadPackage: pkg=" + packageName + " processName=" + packageName);
        if (packageName.equals("com.miui.contentcatcher") || packageName.equals("com.miui.catcherpatch") || packageName.equals("com.google.android.webview")) {
            return;
        }
//        if (
//                !packageName.equals("com.jiguangssp.addemo")) {
//            return;
//        }
        Log.e(TAG, "handleLoadPackage: hook pkg=" + packageName + " processName=" + packageName);

        new ActivityHook().hook();
        new Hook(Canvas.class.getName(), lpparam.classLoader) {

            @Override
            protected boolean beforeMethod(MethodHookParam param) {
//                if (param.method.getName().equals("setMatrix")){
//                    logStackTraceElement("setMatrixHook");
//                }
                return super.beforeMethod(param);
            }

            @Override
            public void hook() {
                hookAllMethod();
//                hookAllMethod("rotate");
//                hookAllMethod("setMatrix");
//                hookAllMethod("concat");
//                hookAllMethod("translate");
            }
        }.hook();

//        new Hook("com.douban.frodo.subject.archive.stack.VelocityViewPager",lpparam.classLoader){
//
//            @Override
//            protected boolean beforeMethod(MethodHookParam param) {
//                if (param.method.getName().equals("c")){
//
//                    logStackTraceElement("c_tag");
//                }
//                return super.beforeMethod(param);
//            }
//
//            @Override
//            protected boolean afterMethod(MethodHookParam param) {
//                if (param.method.getName().equals("c")) {
//                    Object result = param.getResult();
//                    Log.d("c_tag", "afterMethod: result="+ObjectToString.toString(result));
//                }
//                return super.afterMethod(param);
//            }
//
//            @Override
//            public void hook() {
//               hookMethod("c",int.class);
//            }
//        }.hook();

        new Hook("com.douban.frodo.subject.archive.stack.StackBundleView", lpparam.classLoader) {

            @Override
            protected boolean beforeMethod(MethodHookParam param) {
                log(ObjectToString.toString(param.thisObject));
                return super.beforeMethod(param);
            }

            @Override
            protected boolean afterMethod(MethodHookParam param) {

                return super.afterMethod(param);
            }

            @Override
            public void hook() {
                hookAllMethod("setDistanceFromCenter");
            }
        }.hook();

//
//        new Hook(SensorManager.class.getName(),lpparam.classLoader){
//
//
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//
//                Log.d("registerListener", "beforeHookedMethod: " + param.args[0]);
//                logStackTraceElement("registerListener");
//            }
//
//            @Override
//            public void hook() {
//                hookAllMethod("registerListener");
//            }
//        }.hook();


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