package com.ohunag.xposedutil;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * hook  abstract method
 */
public abstract class AbstractHook extends Hook {

    Map<Class, Boolean> map = new HashMap<>();

    public AbstractHook(Class<?> mClass) {
        super(mClass);
    }
    public AbstractHook(String className, ClassLoader classLoader) {
        super(className, classLoader);
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        Class<?> aClass = param.thisObject.getClass();
        Log.e(className + "Hook", "beforeMethod: thisObject=" + param.thisObject + "  class=" + aClass);
        hookSuper(param, aClass);
        return false;
    }

    private void hookSuper(MethodHookParam param, Class<?> aClass) {
        Class thisClzz = aClass;
        while (aClass != null && !thisClzz.getName().equals(getClassName())) {
            addHook(aClass);
            thisClzz = aClass.getSuperclass();
        }
    }

    private void addHook(Class<?> aClass) {
        if (map.containsKey(aClass)) {
//            return super.beforeMethod(param);
        } else {
            Log.d(TAG, "addHook: class="+aClass);
            map.put(aClass, true);
            IHook iHook = hookAbstract(aClass, mClassLoader);
            iHook.hook();
        }
    }

    @Override
    public void hook() {
        hookAllConstructor();
    }

    public abstract IHook hookAbstract(Class aclass, ClassLoader classLoader);
}
