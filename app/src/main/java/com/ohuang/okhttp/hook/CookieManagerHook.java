package com.ohuang.okhttp.hook;

import android.util.Log;
import android.webkit.CookieManager;

import com.ohuang.okhttp.Util;
import com.ohunag.xposedutil.AbstractHook;
import com.ohunag.xposedutil.Hook;
import com.ohunag.xposedutil.IHook;

import java.lang.reflect.Member;
import java.util.Arrays;


public class CookieManagerHook extends AbstractHook {
    public static final String TAG = "CookieManagerHook";

    public CookieManagerHook(ClassLoader classLoader) {
        super(CookieManager.class.getName(), classLoader);
    }


    @Override
    public IHook hookAbstract(Class aclass, ClassLoader classLoader) {
        return new Hook(aclass) {
            @Override
            public void hook() {
                TAG = "CookieManagerHook/" + TAG;
                hookAllMethodForSuper("getCookie");
                hookAllMethodForSuper("setCookie");
            }

            @Override
            public boolean afterMethod(MethodHookParam param) {
                Member method = param.method;
                Object thisObject = param.thisObject;
                Object result = param.getResult();
                log("beforeMethod: thisObject=" + thisObject + "  method=" + method + " param=" + Arrays.toString(param.args) + "  result=" + result);
//                Util.logStackTraceElement(TAG);
                return super.afterMethod(param);
            }
        };
    }


}
