package com.ohuang.okhttp;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import com.ohuang.okhttp.hook.AbstractHook;
import com.ohuang.okhttp.hook.ActivityHook;
import com.ohuang.okhttp.hook.ContentResolverHook;
import com.ohuang.okhttp.hook.ContextWrapperHook;
import com.ohuang.okhttp.hook.Hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import dalvik.system.BaseDexClassLoader;
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
//        if (
//                !packageName.equals("com.jiguangssp.addemo")) {
//            return;
//        }
        Log.e(TAG, "handleLoadPackage: hook pkg=" + packageName + " processName=" + packageName);

        new ActivityHook().hook();
        new ContextWrapperHook().hook();

//            new Hook(SensorManager.class.getName()){
//
//                @Override
//                public void hook() {
//                       hookAllMethod("registerListener");
//                       hookAllMethod("unregisterListener");
//                }
//
//                @Override
//                protected boolean beforeMethod(MethodHookParam param) {
//                    logStackTraceElement("registerListener");
//                    return super.beforeMethod(param);
//                }
//            }.hook();

//            new Hook(Dialog.class.getName()){
//                @Override
//                public void hook() {
//                    hookAllMethod("show");
//                }
//
//                @Override
//                protected boolean beforeMethod(MethodHookParam param) {
//                    logStackTraceElement("Dialog");
//                    return super.beforeMethod(param);
//
//                }
//            }.hook();
        new Hook(PathClassLoader.class.getName()) {
            @Override
            public void hook() {
//                hookAllMethodForSuper("findClass");
                hookMethodForSuper("loadClass", String.class);
            }
        }.hook();


        new AbstractHook(PackageManager.class.getName(),lpparam.classLoader) {


            @Override
            public IHook hookAbstract(Class hook,ClassLoader classLoader) {
                return new Hook(hook.getName(),classLoader) {


                    @Override
                    public void hook() {
                        hookAllMethod("getPackageInfo");
                    }
                };
            }

        }.hook();



//        new Hook(Application.class.getName()){
//            @Override
//            public void hook() {
//                hookAllMethodForSuper("onCreate");
//            }
//
//            @Override
//            protected boolean beforeMethod(MethodHookParam param) {
//                if (!isHook){
//                    try {
//                        hookIPackManager((Context) param.thisObject);
//                    } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
//
//                        isHook=true;
//                    }
//                }
//                return super.beforeMethod(param);
//            }
//        }.hook();
    }

    byte[] bytes = {48, -126, 4, 67, 48, -126, 3, 43, -96, 3, 2, 1, 2, 2, 9, 0, -62, -32, -121, 70, 100, 74, 48, -115, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 4, 5, 0, 48, 116, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 85, 83, 49, 19, 48, 17, 6, 3, 85, 4, 8, 19, 10, 67, 97, 108, 105, 102, 111, 114, 110, 105, 97, 49, 22, 48, 20, 6, 3, 85, 4, 7, 19, 13, 77, 111, 117, 110, 116, 97, 105, 110, 32, 86, 105, 101, 119, 49, 20, 48, 18, 6, 3, 85, 4, 10, 19, 11, 71, 111, 111, 103, 108, 101, 32, 73, 110, 99, 46, 49, 16, 48, 14, 6, 3, 85, 4, 11, 19, 7, 65, 110, 100, 114, 111, 105, 100, 49, 16, 48, 14, 6, 3, 85, 4, 3, 19, 7, 65, 110, 100, 114, 111, 105, 100, 48, 30, 23, 13, 48, 56, 48, 56, 50, 49, 50, 51, 49, 51, 51, 52, 90, 23, 13, 51, 54, 48, 49, 48, 55, 50, 51, 49, 51, 51, 52, 90, 48, 116, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 85, 83, 49, 19, 48, 17, 6, 3, 85, 4, 8, 19, 10, 67, 97, 108, 105, 102, 111, 114, 110, 105, 97, 49, 22, 48, 20, 6, 3, 85, 4, 7, 19, 13, 77, 111, 117, 110, 116, 97, 105, 110, 32, 86, 105, 101, 119, 49, 20, 48, 18, 6, 3, 85, 4, 10, 19, 11, 71, 111, 111, 103, 108, 101, 32, 73, 110, 99, 46, 49, 16, 48, 14, 6, 3, 85, 4, 11, 19, 7, 65, 110, 100, 114, 111, 105, 100, 49, 16, 48, 14, 6, 3, 85, 4, 3, 19, 7, 65, 110, 100, 114, 111, 105, 100, 48, -126, 1, 32, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 13, 0, 48, -126, 1, 8, 2, -126, 1, 1, 0, -85, 86, 46, 0, -40, 59, -94, 8, -82, 10, -106, 111, 18, 78, 41, -38, 17, -14, -85, 86, -48, -113, 88, -30, -52, -87, 19, 3, -23, -73, 84, -45, 114, -10, 64, -89, 27, 29, -53, 19, 9, 103, 98, 78, 70, 86, -89, 119, 106, -110, 25, 61, -78, -27, -65, -73, 36, -87, 30, 119, 24, -117, 14, 106, 71, -92, 59, 51, -39, 96, -101, 119, 24, 49, 69, -52, -33, 123, 46, 88, 102, 116, -55, -31, 86, 91, 31, 76, 106, 89, 85, -65, -14, 81, -90, 61, -85, -7, -59, 92, 39, 34, 34, 82, -24, 117, -28, -8, 21, 74, 100, 95, -119, 113, 104, -64, -79, -65, -58, 18, -22, -65, 120, 87, 105, -69, 52, -86, 121, -124, -36, 126, 46, -94, 118, 76, -82, -125, 7, -40, -63, 113, 84, -41, -18, 95, 100, -91, 26, 68, -90, 2, -62, 73, 5, 65, 87, -36, 2, -51, 95, 92, 14, 85, -5, -17, -123, 25, -5, -29, 39, -16, -79, 81, 22, -110, -59, -96, 111, 25, -47, -125, -123, -11, -60, -37, -62, -42, -71, 63, 104, -52, 41, 121, -57, 14, 24, -85, -109, -122, 107, 59, -43, -37, -119, -103, 85, 42, 14, 59, 76, -103, -33, 88, -5, -111, -117, -19, -63, -126, -70, 53, -32, 3, -63, -76, -79, 13, -46, 68, -88, -18, 36, -1, -3, 51, 56, 114, -85, 82, 33, -104, 94, -38, -80, -4, 13, 11, 20, 91, 106, -95, -110, -123, -114, 121, 2, 1, 3, -93, -127, -39, 48, -127, -42, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, -57, 125, -116, -62, 33, 23, 86, 37, -102, 127, -45, -126, -33, 107, -29, -104, -28, -41, -122, -91, 48, -127, -90, 6, 3, 85, 29, 35, 4, -127, -98, 48, -127, -101, -128, 20, -57, 125, -116, -62, 33, 23, 86, 37, -102, 127, -45, -126, -33, 107, -29, -104, -28, -41, -122, -91, -95, 120, -92, 118, 48, 116, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 85, 83, 49, 19, 48, 17, 6, 3, 85, 4, 8, 19, 10, 67, 97, 108, 105, 102, 111, 114, 110, 105, 97, 49, 22, 48, 20, 6, 3, 85, 4, 7, 19, 13, 77, 111, 117, 110, 116, 97, 105, 110, 32, 86, 105, 101, 119, 49, 20, 48, 18, 6, 3, 85, 4, 10, 19, 11, 71, 111, 111, 103, 108, 101, 32, 73, 110, 99, 46, 49, 16, 48, 14, 6, 3, 85, 4, 11, 19, 7, 65, 110, 100, 114, 111, 105, 100, 49, 16, 48, 14, 6, 3, 85, 4, 3, 19, 7, 65, 110, 100, 114, 111, 105, 100, -126, 9, 0, -62, -32, -121, 70, 100, 74, 48, -115, 48, 12, 6, 3, 85, 29, 19, 4, 5, 48, 3, 1, 1, -1, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 4, 5, 0, 3, -126, 1, 1, 0, 109, -46, 82, -50, -17, -123, 48, 44, 54, 10, -86, -50, -109, -101, -49, -14, -52, -87, 4, -69, 93, 122, 22, 97, -8, -82, 70, -78, -103, 66, 4, -48, -1, 74, 104, -57, -19, 26, 83, 30, -60, 89, 90, 98, 60, -26, 7, 99, -79, 103, 41, 122, 122, -29, 87, 18, -60, 7, -14, 8, -16, -53, 16, -108, 41, 18, 77, 123, 16, 98, 25, -64, -124, -54, 62, -77, -7, -83, 95, -72, 113, -17, -110, 38, -102, -117, -30, -117, -15, 109, 68, -56, -39, -96, -114, 108, -78, -16, 5, -69, 63, -30, -53, -106, 68, 126, -122, -114, 115, 16, 118, -83, 69, -77, 63, 96, 9, -22, 25, -63, 97, -26, 38, 65, -86, -103, 39, 29, -3, 82, 40, -59, -59, -121, -121, 93, -37, 127, 69, 39, 88, -42, 97, -10, -52, 12, -52, -73, 53, 46, 66, 76, -60, 54, 92, 82, 53, 50, -9, 50, 81, 55, 89, 60, 74, -29, 65, -12, -37, 65, -19, -38, 13, 11, 16, 113, -89, -60, 64, -16, -2, -98, -96, 28, -74, 39, -54, 103, 67, 105, -48, -124, -67, 47, -39, 17, -1, 6, -51, -65, 44, -6, 16, -36, 15, -119, 58, -29, 87, 98, -111, -112, 72, -57, -17, -58, 76, 113, 68, 23, -125, 66, -9, 5, -127, -55, -34, 87, 58, -11, 91, 57, 13, -41, -3, -71, 65, -122, 49, -119, 93, 95, 117, -97, 48, 17, 38, -121, -1, 98, 20, 16, -64, 105, 48, -118};
    public static boolean isHook = false;

    private void hookIPackManager(Context context) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class<?> cls = Class.forName("android.app.ActivityThread");
        Object invoke = cls.getDeclaredMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]);
        Field declaredField = cls.getDeclaredField("sPackageManager");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(invoke);
        Class<?> cls2 = Class.forName("android.content.pm.IPackageManager");
        String appPkgName = context.getPackageName();
        Object newProxyInstance = Proxy.newProxyInstance(cls2.getClassLoader(), new Class[]{cls2}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] objArr) throws Throwable {
                if ("getPackageInfo".equals(method.getName())) {
                    String str = (String) objArr[0];

                    PackageInfo packageInfo = (PackageInfo) method.invoke(obj, objArr);
                    if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        Signature[] signatures = packageInfo.signatures;
                        for (int i = 0; i < signatures.length; i++) {
                            log("invoke: packageInfo.signatures[" + i + "]=" + Arrays.toString(signatures[i].toByteArray()));
                            Log.d(TAG, "invoke: packageInfo.signatures[" + i + "]=" + Arrays.toString(signatures[i].toByteArray()));
                        }
                    }
//
//                    Signature[] signatures = new Signature[1];
//                    Signature signature=new Signature(bytes);
//                    signatures[0]=signature;
//                    packageInfo.signatures = signatures;
                    return packageInfo;
                }

                return method.invoke(obj, objArr);
            }
        });
        declaredField.set(invoke, newProxyInstance);
        PackageManager packageManager = context.getPackageManager();
        Field declaredField2 = packageManager.getClass().getDeclaredField("mPM");
        declaredField2.setAccessible(true);
        declaredField2.set(packageManager, newProxyInstance);

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

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }
}