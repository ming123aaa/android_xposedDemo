package com.ohuang.okhttp;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class IHook extends XC_MethodHook {

    protected static final Object OBJECT = new Object();
    private static final HashMap<String, Method> methodCache;

    public abstract String getClassName();


    private ClassLoader mClassLoader;
    private Class<?> mClass;

    protected static Context mContext;


    static {

        methodCache = new HashMap<String, Method>();
    }

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
                    mClass = Class.forName(getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return mClass;
    }

    public abstract void hook();

    protected abstract boolean beforeMethod(MethodHookParam param);

    protected abstract boolean afterMethod(MethodHookParam param);


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

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        if (!beforeMethod(param)) {
            super.beforeHookedMethod(param);
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        if (!afterMethod(param)) {
            super.afterHookedMethod(param);
        }
    }
}