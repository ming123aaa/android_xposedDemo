package com.ohuang.okhttp;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookBroadcast implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    public static final String TAG = "HookBroadcast";
    public static String packageName = "null";
    public static String processName = "null";


    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }

    public static String toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(); // 获取当前时间
        String format = sdf.format(date);
        return format;
    }

    public static void log(String s) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/" + TAG + ".log", s);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        packageName = lpparam.packageName;
        processName = lpparam.processName;
        Log.e(TAG, "handleLoadPackage: pkg=" + packageName + " processName=" + packageName);
        XposedHelpers.findAndHookMethod(ContextWrapper.class, "sendBroadcast", Intent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Util.tryCatch(() -> {
                    Intent intent = (Intent) param.args[0];
                    Log.e(TAG, "obj=" + param.thisObject + " intent=" + intent);
                    log(toDate() + "   " + "obj=" + param.thisObject + " intent=" + intent);
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            String s = "Dump Stack" + i + ": " + stackElements[i]
                                    + "----" + stackElements[i].getFileName()
                                    + "----" + stackElements[i].getLineNumber()
                                    + "----" + stackElements[i].getMethodName();
                            log(s);
                        }
                    }
                });
            }
        });
        XposedHelpers.findAndHookMethod(ContextWrapper.class, "sendBroadcast", Intent.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Util.tryCatch(() -> {
                    Intent intent = (Intent) param.args[0];
                    Log.e(TAG, "obj=" + param.thisObject + " intent=" + intent);
                    log(toDate() + "   " + "obj=" + param.thisObject + " intent=" + intent);
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            String s = "Dump Stack" + i + ": " + stackElements[i]
                                    + "----" + stackElements[i].getFileName()
                                    + "----" + stackElements[i].getLineNumber()
                                    + "----" + stackElements[i].getMethodName();
                            log(s);
                        }
                    }
                });

            }
        });
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }


}
