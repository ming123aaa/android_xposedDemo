package com.ohuang.okhttp;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.ohunag.xposedutil.Hook;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

//        new ActivityHook(lpparam.classLoader).hook();
//        new ContextWrapperHook(lpparam.classLoader).hook();

//        new WebViewHook(lpparam.classLoader).hook();

//        new TouchEventViewHook(lpparam.classLoader).hook();
//        new TouchEventViewGroupHook(lpparam.classLoader).hook();
//        new CookieManagerHook(lpparam.classLoader).hook();
//        new ReactInstanceManagerBuilderHook(lpparam.classLoader).hook();

//      new SettingsSecureHook(lpparam.classLoader).hook();
//      new SettingsSystemHook(lpparam.classLoader).hook();


        XposedHelpers.findAndHookMethod(View.class, "setOnClickListener", View.OnClickListener.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Object obargs = param.args[0];
                Object pClass = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Logger.d(TAG + ":  obj=" + obargs + " objectToString=" + objectToString(obargs, 5));
                        Logger.json(objectToString(obargs, 3));

                        return method.invoke(obargs, args);
                    }
                });
                param.args[0] = pClass;
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        });


        XposedHelpers.findAndHookMethod(View.class, "setOnTouchListener", View.OnTouchListener.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Object obargs = param.args[0];
                Object pClass = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{View.OnTouchListener.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Logger.d(TAG + ":  obj=" + obargs + " objectToString=" + objectToString(obargs, 5));
                        Logger.json(objectToString(obargs, 3));
                        return method.invoke(obargs, args);
                    }
                });
                param.args[0] = pClass;
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        });
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public String objectToString(Object o, int deep) {
        if (o == null) {
            return "\"null\"";
        }

        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"className\":").append("\""+aClass.getName()+"\"");
        stringBuilder.append(",").append("\"hashCode\":").append("\"").append(o.hashCode()).append("\"");
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {

                stringBuilder.append(",").append("\""+declaredField.getName()+"\"").append(":");

                if (declaredField.get(o) != null) {
                    Object o1 = declaredField.get(o);
                    Class clzz=o1.getClass();
                    String name = clzz.getName();

                    if (name.startsWith("java.lang")) {
                        stringBuilder.append("\""+declaredField.get(o)+"\"");
                    }else if(name.startsWith("android")||name.startsWith("java.util")||o1 instanceof View||o1 instanceof Activity||name.equals("com.xingin.capa.lib.video.entity.VideoTemplate")){
                        stringBuilder.append("\""+name+"\"");
                    } else {
                        if (deep > 0) {
                            stringBuilder.append(objectToString(declaredField.get(o), deep - 1));
                        } else {
                            stringBuilder.append("\""+declaredField.get(o).getClass().getName()+"\"");
                        }
                    }
                } else {
                    stringBuilder.append("\"null\"");
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.append("}").toString();
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