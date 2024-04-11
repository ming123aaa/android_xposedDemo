package com.ohunag.xposedutil;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class IHook extends XC_MethodHook implements HookCallBack {


    public IHook(Class<?> mClass) {
        this.mClass = mClass;
    }

    public IHook(ClassLoader classLoader) {
        mClassLoader = classLoader;
    }

    public abstract String getClassName();


    private Class<?> mClass;
    public ClassLoader mClassLoader = ClassLoader.getSystemClassLoader();

    public List<Unhook> unhookList = new ArrayList<>();


    /**
     * 缓存 class 加速
     *
     * @return
     */
    protected Class<?> toClass() {
        if (mClass == null) {
            try {

                mClass = XposedHelpers.findClass(getClassName(), mClassLoader);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (mClass == null) {
                try {
                    mClass = mClassLoader.loadClass(getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (mClass == null) {
                try {
                    mClass = Class.forName(getClassName(), false, mClassLoader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return mClass;
    }

    public abstract void hook();

    public abstract boolean beforeMethod(MethodHookParam param);

    public void beforeMethodEnd(MethodHookParam param) {
    }

    public abstract boolean afterMethod(MethodHookParam param);

    public void afterMethodEnd(MethodHookParam param) {
    }


    public void unHook() {
        for (int i = 0; i < unhookList.size(); i++) {
            if (unhookList.get(i) != null) {
                unhookList.get(i).unhook();
            }
        }
        unhookList.clear();
    }

    protected void hookAllMethodForSuper() {
        hookAllMethodForSuper(this);
    }

    protected void hookAllMethodForSuper(XC_MethodHook xc_methodHook) {
        Class<?> aClass = toClass();
        while (aClass != null) {
            Method[] methods = aClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] objects = new Object[parameterTypes.length + 1];
                for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                    objects[i1] = parameterTypes[i1];
                }
                objects[parameterTypes.length] = xc_methodHook;
                try {
                    Unhook andHookMethod = XposedHelpers.findAndHookMethod(aClass, method.getName(), objects);
                    unhookList.add(andHookMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    protected void hookAllMethod() {
        hookAllMethod(this);
    }

    protected void hookAllMethod(XC_MethodHook xc_methodHook) {
        if (toClass() == null) {
            return;
        }
        Method[] methods = toClass().getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] objects = new Object[parameterTypes.length + 1];
            for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                objects[i1] = parameterTypes[i1];
            }
            objects[parameterTypes.length] = xc_methodHook;
            try {
                Unhook andHookMethod = XposedHelpers.findAndHookMethod(toClass(), method.getName(), objects);
                unhookList.add(andHookMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    protected void hookAllMethodForBridge(String name) {
        hookAllMethodForBridge(name, this);
    }

    protected void hookAllMethodForBridge(String name, XC_MethodHook xc_methodHook) {
        if (toClass() == null) {
            return;
        }
        Set<Unhook> unhooks = XposedBridge.hookAllMethods(toClass(), name, xc_methodHook);
        unhookList.addAll(unhooks);
    }

    protected void hookAllConstructorsForBridge() {
        hookAllConstructorsForBridge(this);
    }

    protected void hookAllConstructorsForBridge(XC_MethodHook xc_methodHook) {
        Set<Unhook> unhooks = XposedBridge.hookAllConstructors(toClass(), xc_methodHook);
        unhookList.addAll(unhooks);
    }

    protected void hookAllMethodForSuper(String name) {
        hookAllMethodForSuper(name, this);

    }

    protected void hookAllMethodForSuper(String name, XC_MethodHook xc_methodHook) {
        Class<?> aClass = toClass();
        while (aClass != null) {
            Method[] methods = aClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (name.equals(method.getName())) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Object[] objects = new Object[parameterTypes.length + 1];
                    for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                        objects[i1] = parameterTypes[i1];
                    }
                    objects[parameterTypes.length] = xc_methodHook;
                    try {
                        Unhook andHookMethod = XposedHelpers.findAndHookMethod(aClass, name, objects);
                        unhookList.add(andHookMethod);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    protected void hookAllMethod(String name) {
        hookAllMethod(name, this);
    }

    protected void hookAllMethod(String name, XC_MethodHook xc_methodHook) {
        if (toClass() == null) {
            return;
        }
        Method[] methods = toClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (name.equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] objects = new Object[parameterTypes.length + 1];
                for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                    objects[i1] = parameterTypes[i1];
                }
                objects[parameterTypes.length] = xc_methodHook;
                try {
                    Unhook unhook = XposedHelpers.findAndHookMethod(toClass(), name, objects);
                    unhookList.add(unhook);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Deprecated
    protected void hookMethodForSuper(String name, Class<?>... parameterTypes) {
        hookMethodForSuper(name, this, parameterTypes);
    }

    /**
     * hook方法包括父类的
     *
     * @param name
     * @param parameterTypes
     */
    @Deprecated
    protected void hookMethodForSuper(String name, XC_MethodHook xc_methodHook, Class<?>... parameterTypes) {
        Class<?> aClass = toClass();
        while (aClass != null) {
            Method[] methods = aClass.getDeclaredMethods();
            a:
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    Class<?>[] methodParameterTypes = method.getParameterTypes();
                    if (methodParameterTypes.length != parameterTypes.length) {
                        continue;
                    }
                    Object[] objects = new Object[parameterTypes.length + 1];
                    for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                        if (methodParameterTypes[i1] == parameterTypes[i1]) {
                            objects[i1] = parameterTypes[i1];
                        } else {
                            continue a;
                        }
                    }
                    objects[parameterTypes.length] = xc_methodHook;
                    try {
                        Unhook andHookMethod = XposedHelpers.findAndHookMethod(aClass, name, objects);
                        unhookList.add(andHookMethod);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    protected void hookMethod(String name, Class<?>... parameterTypes) {
        hookMethod(name, this, parameterTypes);
    }

    protected void hookMethod(String name, XC_MethodHook xc_methodHook, Class<?>... parameterTypes) {
        if (toClass() == null) {
            return;
        }
        Object[] objects = new Object[parameterTypes.length + 1];
        for (int i1 = 0; i1 < parameterTypes.length; i1++) {
            objects[i1] = parameterTypes[i1];
        }
        objects[parameterTypes.length] = xc_methodHook;
        try {
            Unhook andHookMethod = XposedHelpers.findAndHookMethod(toClass(), name, objects);
            unhookList.add(andHookMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void hookAllConstructor() {
        hookAllConstructor(this);
    }

    protected void hookAllConstructor(XC_MethodHook xc_methodHook) {
        if (toClass() == null) {
            return;
        }
        Constructor[] methods = toClass().getConstructors();
        for (int i = 0; i < methods.length; i++) {
            Constructor method = methods[i];

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] objects = new Object[parameterTypes.length + 1];
            for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                objects[i1] = parameterTypes[i1];
            }
            objects[parameterTypes.length] = xc_methodHook;
            try {
                Unhook andHookConstructor = XposedHelpers.findAndHookConstructor(toClass(), objects);
                unhookList.add(andHookConstructor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void hookConstructor(Class<?>... parameterTypes) {
        hookConstructor(this, parameterTypes);
    }

    protected void hookConstructor(XC_MethodHook xc_methodHook, Class<?>... parameterTypes) {
        if (toClass() == null) {
            return;
        }
        Object[] objects = new Object[parameterTypes.length + 1];
        for (int i1 = 0; i1 < parameterTypes.length; i1++) {
            objects[i1] = parameterTypes[i1];
        }
        objects[parameterTypes.length] = xc_methodHook;
        try {
            Unhook andHookConstructor = XposedHelpers.findAndHookConstructor(toClass(), objects);
            unhookList.add(andHookConstructor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        try {
            if (!beforeMethod(param)) {
                super.beforeHookedMethod(param);
            }
            beforeMethodEnd(param);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        try {
            if (!afterMethod(param)) {
                super.afterHookedMethod(param);
            }
            afterMethodEnd(param);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected void beforeLog(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        String p ="" ;
        try {
            p= Arrays.toString(param.args);
        }catch (Exception e){
            e.printStackTrace();
        }
        log("beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" +p);
    }
    protected void afterLog(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        String p ="" ;
        try {
            p=Arrays.toString(param.args);
        }catch (Exception e){
            e.printStackTrace();
        }
        log("afterMethod: thisObject=" + thisObject + "  method=" + method + " param=" +p);
    }

    protected void log(String text) {
        Log.d(getClassName()+"Hook", text);
    }
}
