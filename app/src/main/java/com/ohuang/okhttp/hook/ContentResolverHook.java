package com.ohuang.okhttp.hook;

import android.content.ContentResolver;
import android.util.Log;

import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;

public class ContentResolverHook extends IHook {
    public static final String TAG = "ContentResolverHook";

    public ContentResolverHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return ContentResolver.class.getName();
    }

    @Override
    public void hook() {
        hookAllMethod("call");
        hookAllMethod("delete");
        hookAllMethod("insert");
        hookAllMethod("query");
        hookAllMethod("update");
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        Member method = param.method;
        Object thisObject = param.thisObject;
        Log.e(TAG, "beforeMethod: thisObject=" + thisObject + "  method=" + method);
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        return false;
    }
}
