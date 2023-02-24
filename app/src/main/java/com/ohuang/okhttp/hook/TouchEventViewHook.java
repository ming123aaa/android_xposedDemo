package com.ohuang.okhttp.hook;

import android.util.Log;
import android.view.View;

import com.ohunag.xposedutil.IHook;

public class TouchEventViewHook extends IHook {
    public static final String TAG = "TouchEventViewHook";

    public TouchEventViewHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getClassName() {
        return View.class.getName();
    }

    @Override
    public void hook() {
        hookAllMethod("dispatchTouchEvent");
    }

    @Override
    public boolean beforeMethod(MethodHookParam param) {
        return false;
    }

    @Override
    public boolean afterMethod(MethodHookParam param) {
        boolean result = (boolean) param.getResult();
        if (result){
            Log.e(TAG, "afterMethod: "+param.thisObject.toString());
        }
        return false;
    }
}
