package com.ohunag.xposedutil;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * hook  abstract method
 */
public abstract class AbstractHook extends Hook{

    Map<Class,Boolean> map=new HashMap<>();
    public AbstractHook(String className) {
        super(className);
    }

    public AbstractHook(String className, ClassLoader classLoader) {
        super(className, classLoader);
    }

    @Override
    protected boolean beforeMethod(MethodHookParam param) {
        Class<?> aClass = param.thisObject.getClass();
        if (map.containsKey(aClass)){
//            return super.beforeMethod(param);
        }else {
            Log.e(className + "Hook", "beforeMethod: thisObject=" + param.thisObject +"  class="+aClass);
            map.put(aClass,true);
            IHook iHook = hookAbstract(aClass,mClassLoader);
            iHook.hook();
        }
        return false;
    }

    @Override
    public void hook() {
        hookAllConstructor();
    }

    public abstract IHook hookAbstract(Class aclass,ClassLoader classLoader);
}
