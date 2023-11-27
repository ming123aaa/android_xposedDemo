package com.ohuang.okhttp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.ohuang.okhttp.StringUtil;
import com.ohuang.okhttp.Util;
import com.ohuang.okhttp.util.hookSignature.BiliGameSignature;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 无需xp hook签名
 */
public class PackageUtil {
    public static final String TAG = "PackageUtil";

    private static HashMap<String, PackageSignature> keys = new HashMap<>();


    public static String thisPackName = "";
    public static boolean shouldHook = true;

    private static void addPackageSignature(PackageSignature packageSignature) {
        keys.put(packageSignature.packageName(), packageSignature);
    }

    private static void init() { //用于初始化 hook签名配置
        addPackageSignature(new BiliGameSignature());
    }

    private static PackageInfo shouldHookSign(String packName, PackageInfo packageInfo) {
        if (shouldHook) {
            if (keys.containsKey(packName)) {
                if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                    Signature[] signatures = packageInfo.signatures;
                    for (int i = 0; i < signatures.length; i++) {
                        PackageSignature packageSignature = keys.get(packName);
                        byte[] signature = packageSignature.getSignature(i);
                        if (signature != null) {
                            signatures[i] = new Signature(signature);
                            Log.e(TAG, "invoke: set.signatures[" + i + "]");
                        }
                    }
                }
            }
        }
        return packageInfo;
    }

    public static void hookIPackManager(Context context) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        init();
        Log.d(TAG, "hookIPackManager: ");
        thisPackName = context.getPackageName();
        Class<?> cls = Class.forName("android.app.ActivityThread");
        Object invoke = cls.getDeclaredMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]);
        Field declaredField = cls.getDeclaredField("sPackageManager");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(invoke);
        Class<?> cls2 = Class.forName("android.content.pm.IPackageManager");
        String appPkgName = context.getPackageName();
        Object newProxyInstance = Proxy.newProxyInstance(cls2.getClassLoader(), new Class[]{cls2}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] objArr) throws Throwable {
                Log.d(TAG, "invoke: " + method);
                if ("getPackageInfo".equals(method.getName())) {
                    String packName = (String) objArr[0];

                    PackageInfo packageInfo = (PackageInfo) method.invoke(obj, objArr);
                    if (!Integer.valueOf(64).equals(objArr[1]) && !Integer.valueOf(0x08000000).equals(objArr[1])) {
                        return packageInfo;
                    }
                    Log.e(TAG, "invoke: getPackageInfo  p=" + Arrays.toString(objArr));
                    if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        Signature[] signatures = packageInfo.signatures;
                        for (int i = 0; i < signatures.length; i++) {
                            Log.e(TAG, "invoke: packageInfo.signatures[" + i + "]");

                        }
                    }
                    return shouldHookSign(packName, packageInfo);
//
//                    Signature[] signatures = new Signature[1];
//                    Signature signature=new Signature(bytes);
//                    signatures[0]=signature;
//                    packageInfo.signatures = signatures;
//                    return packageInfo;
                }

                return method.invoke(obj, objArr);
            }
        });
        declaredField.set(invoke, newProxyInstance);
        PackageManager packageManager = context.getPackageManager();
        Field declaredField2 = packageManager.getClass().getDeclaredField("mPM");
        declaredField2.setAccessible(true);
        declaredField2.set(packageManager, newProxyInstance);
    }
}
