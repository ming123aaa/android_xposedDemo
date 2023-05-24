package com.ohunag.xposedutil;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public List<Unhook> unhookList=new ArrayList<>();


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

    public  void beforeMethodEnd(MethodHookParam param){}

    public abstract boolean afterMethod(MethodHookParam param);
    public   void afterMethodEnd(MethodHookParam param){}


    public void unHook(){
        for (int i = 0; i < unhookList.size(); i++) {
            if (unhookList.get(i)!=null){
                unhookList.get(i).unhook();
            }
        }
        unhookList.clear();
    }

    public void hookAllMethodForSuper(){
        hookAllMethodForSuper(this);
    }

    public void hookAllMethodForSuper(XC_MethodHook xc_methodHook) {
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
    public void hookAllMethod(){
        hookAllMethod(this);
    }
    public void hookAllMethod(XC_MethodHook xc_methodHook) {
        if (toClass()==null){
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
    public void hookAllMethodForBridge(String name){
        hookAllMethodForBridge(name,this);
    }

    public void hookAllMethodForBridge(String name,XC_MethodHook xc_methodHook){
        if (toClass()==null){
            return;
        }
        Set<Unhook> unhooks = XposedBridge.hookAllMethods(toClass(), name, xc_methodHook);
        unhookList.addAll(unhooks);
    }

    public void hookAllConstructorsForBridge(){
        hookAllConstructorsForBridge(this);
    }
    public void hookAllConstructorsForBridge(XC_MethodHook xc_methodHook){
        Set<Unhook> unhooks = XposedBridge.hookAllConstructors(toClass(), xc_methodHook);
        unhookList.addAll(unhooks);
    }

    public void hookAllMethodForSuper(String name){
        hookAllMethodForSuper(name,this);

    }
    public void hookAllMethodForSuper(String name,XC_MethodHook xc_methodHook) {
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
    public void hookAllMethod(String name) {
        hookAllMethod(name,this);
    }
    public void hookAllMethod(String name,XC_MethodHook xc_methodHook) {
        if (toClass()==null){
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
    public void hookMethodForSuper(String name, Class<?>... parameterTypes){
        hookMethodForSuper(name,this,parameterTypes);
    }
    /**
     * hook方法包括父类的
     * @param name
     * @param parameterTypes
     */
    @Deprecated
    public void hookMethodForSuper(String name,XC_MethodHook xc_methodHook, Class<?>... parameterTypes) {
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

    public void hookMethod(String name,Class<?>... parameterTypes){
        hookMethod(name,this,parameterTypes);
    }
    public void hookMethod(String name, XC_MethodHook xc_methodHook,Class<?>... parameterTypes) {
        if (toClass()==null){
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


    public void hookAllConstructor(){
        hookAllConstructor(this);
    }
    public void hookAllConstructor(XC_MethodHook xc_methodHook) {
        if (toClass()==null){
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
    public void hookConstructor(Class<?>... parameterTypes){
        hookConstructor(this,parameterTypes);
    }
    public void hookConstructor(XC_MethodHook xc_methodHook,Class<?>... parameterTypes) {
        if (toClass()==null){
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
