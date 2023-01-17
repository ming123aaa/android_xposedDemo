package com.ohunag.xposedutil;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class IHook extends XC_MethodHook {

    public IHook() {

    }

    public IHook(ClassLoader classLoader) {
        mClassLoader = classLoader;
    }

    public abstract String getClassName();


    private Class<?> mClass;
    public ClassLoader mClassLoader = ClassLoader.getSystemClassLoader();


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
                    mClass = Class.forName(getClassName(), false, mClassLoader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return mClass;
    }

    public abstract void hook();

    protected abstract boolean beforeMethod(MethodHookParam param);

    protected  void beforeMethodEnd(MethodHookParam param){}

    protected abstract boolean afterMethod(MethodHookParam param);
    protected  void afterMethodEnd(MethodHookParam param){}

    public void hookAllMethodForSuper() {
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
                objects[parameterTypes.length] = this;
                try {
                    XposedHelpers.findAndHookMethod(aClass, method.getName(), objects);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    public void hookAllMethod() {
        Method[] methods = toClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] objects = new Object[parameterTypes.length + 1];
            for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                objects[i1] = parameterTypes[i1];
            }
            objects[parameterTypes.length] = this;
            try {
                XposedHelpers.findAndHookMethod(toClass(), method.getName(), objects);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void hookAllMethodForBridge(String name){
        XposedBridge.hookAllMethods(toClass(),name,this);
    }

    public void hookAllConstructorsForBridge(){
        XposedBridge.hookAllConstructors(toClass(),this);
    }

    public void hookAllMethodForSuper(String name) {
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
                    objects[parameterTypes.length] = this;
                    try {
                        XposedHelpers.findAndHookMethod(aClass, name, objects);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            aClass = aClass.getSuperclass();
        }
    }

    public void hookAllMethod(String name) {
        Method[] methods = toClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (name.equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] objects = new Object[parameterTypes.length + 1];
                for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                    objects[i1] = parameterTypes[i1];
                }
                objects[parameterTypes.length] = this;
                try {
                    XposedHelpers.findAndHookMethod(toClass(), name, objects);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void hookMethodForSuper(String name, Class<?>... parameterTypes) {
        Class<?> aClass = toClass();
        while (aClass != null) {
            Method[] methods = aClass.getDeclaredMethods();
            a:
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
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
                    objects[parameterTypes.length] = this;
                    try {
                        XposedHelpers.findAndHookMethod(aClass, name, objects);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            aClass = aClass.getSuperclass();
        }
    }


    public void hookMethod(String name, Class<?>... parameterTypes) {
        Object[] objects = new Object[parameterTypes.length + 1];
        for (int i1 = 0; i1 < parameterTypes.length; i1++) {
            objects[i1] = parameterTypes[i1];
        }
        objects[parameterTypes.length] = this;
        try {
            XposedHelpers.findAndHookMethod(toClass(), name, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookAllConstructor() {
        Constructor[] methods = toClass().getConstructors();
        for (int i = 0; i < methods.length; i++) {
            Constructor method = methods[i];

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] objects = new Object[parameterTypes.length + 1];
            for (int i1 = 0; i1 < parameterTypes.length; i1++) {
                objects[i1] = parameterTypes[i1];
            }
            objects[parameterTypes.length] = this;
            try {
                XposedHelpers.findAndHookConstructor(toClass(), objects);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hookConstructor(Class<?>... parameterTypes) {
        Object[] objects = new Object[parameterTypes.length + 1];
        for (int i1 = 0; i1 < parameterTypes.length; i1++) {
            objects[i1] = parameterTypes[i1];
        }
        objects[parameterTypes.length] = this;
        try {
            XposedHelpers.findAndHookConstructor(toClass(), objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        if (!beforeMethod(param)) {
            super.beforeHookedMethod(param);
        }
        beforeMethodEnd(param);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        if (!afterMethod(param)) {
            super.afterHookedMethod(param);
        }
        afterMethodEnd(param);
    }
}
