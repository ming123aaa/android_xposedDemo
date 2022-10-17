package com.ohuang.okhttp;

import android.app.Activity;
import android.app.Service;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.util.Log;

import com.ohuang.okhttp.hook.ActivityHook;
import com.ohuang.okhttp.hook.ContentResolverHook;
import com.ohuang.okhttp.hook.ContextWrapperHook;
import com.ohuang.okhttp.hook.Hook;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
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
        if (packageName.equals("com.miui.contentcatcher") || packageName.equals("com.miui.catcherpatch")) {
            return;
        }
        if (
                !packageName.equals("com.jiguangssp.addemo")) {
            return;
        }
        Log.e(TAG, "handleLoadPackage: hook pkg=" + packageName + " processName=" + packageName);

//        new ActivityHook().hook();
//        new ContextWrapperHook().hook();
//        new ContentResolverHook().hook();
        Class<?> aClass1 = Class.forName("com.ohuang.adjiguang.MainAdjiguangActivity", false, lpparam.classLoader);
        Log.e(TAG, "handleLoadPackage: " + aClass1);


        new Hook("com.kwad.components.core.m.q",lpparam.classLoader) {

            @Override
            public void hook() {
                hookAllMethod("a");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Log.e(TAG, "afterHookedMethod: " + Arrays.asList(param.args));
                Log.e(TAG, "afterHookedMethod: "+param.getResult() );

            }
        }.hook();
    }


    public static String toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(); // 获取当前时间
        String format = sdf.format(date);
        return format;
    }

    public static void log(String s) {
        LogFile.addLog(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/com.ohuang.okhttp" + "/Tutorial.log", s);
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }
}