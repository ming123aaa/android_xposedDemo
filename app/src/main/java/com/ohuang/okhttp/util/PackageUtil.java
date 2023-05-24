package com.ohuang.okhttp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.ohuang.okhttp.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class PackageUtil {
    public static final String TAG = "PackageUtil";
    public static byte[] bytes = new byte[]{48, -126, 2, -55, 48, -126, 1, -79, -96, 3, 2, 1, 2, 2, 4, 88, 30, -24, 43, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 48, 21, 49, 19, 48, 17, 6, 3, 85, 4, 3, 19, 10, 122, 104, 117, 104, 111, 110, 103, 108, 101, 105, 48, 30, 23, 13, 50, 49, 48, 52, 50, 54, 49, 51, 51, 49, 50, 54, 90, 23, 13, 52, 54, 48, 52, 50, 48, 49, 51, 51, 49, 50, 54, 90, 48, 21, 49, 19, 48, 17, 6, 3, 85, 4, 3, 19, 10, 122, 104, 117, 104, 111, 110, 103, 108, 101, 105, 48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -95, -37, -45, -54, -9, 76, -121, -7, 115, -57, -73, -121, 33, -83, 19, -79, 115, -43, -9, 70, 74, -116, 55, 42, 45, -51, 58, -55, 83, 99, 5, -19, -18, -111, -47, -100, -67, 73, -74, -61, -10, -89, -86, 16, -73, 77, -103, -11, 74, 19, -115, 108, 62, 58, -50, -107, 97, 18, 24, -96, -100, 5, 100, -75, 24, -24, 25, -105, 119, 62, -110, -48, 58, -105, 15, -104, 2, 123, -88, 45, -33, 94, 6, 25, -33, 127, 59, -123, 0, 115, 54, -60, -59, 97, 53, -93, -76, -58, -126, -71, 117, 26, 126, 0, 100, -98, 106, 108, -8, 4, -90, 30, -7, -7, -92, -89, 93, -78, -19, -125, 33, 76, 20, -1, 100, -110, 122, -107, 30, -40, -112, -105, 15, 68, 3, -95, -84, -53, 33, 46, -51, -86, 14, 67, -41, 68, 9, 39, 102, -88, 1, -104, -114, -67, -89, 13, -76, 88, 115, -49, -11, 102, 88, -109, 76, 80, -4, -116, -127, -43, 40, 114, 41, -4, -26, 118, -34, 57, 70, 10, 120, 120, 50, -27, -94, -45, 38, 86, 97, 9, -37, -26, 71, 13, -82, 60, -25, 22, -100, 10, -90, -23, -42, -122, -30, -54, 88, 67, 11, -65, 22, -52, 97, -46, 75, -95, 58, -62, 57, 10, -61, 19, -22, 123, -19, 25, 67, 81, 4, -106, 88, 4, -107, -102, -114, -17, -47, 110, 74, 125, -1, 15, -52, 67, 34, -96, -89, 117, 18, 55, 6, 55, 0, -76, -77, 77, 2, 3, 1, 0, 1, -93, 33, 48, 31, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, -74, 75, -51, -21, 18, 51, -56, -93, 6, 122, -53, 103, -19, -18, 4, 41, -90, 84, 85, 17, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 11, 5, 0, 3, -126, 1, 1, 0, 116, 34, 3, -5, -60, -36, 20, -114, -8, -22, 46, 51, 70, -107, 4, 32, 46, 6, 26, -57, 104, 40, 117, -98, -90, -31, -28, 72, -13, 56, 55, 22, -78, -78, 96, 6, 77, -103, -81, 8, 66, 97, 60, 12, -37, 27, 123, -121, -69, -51, -57, -50, -27, 121, 92, -55, -49, -45, 61, -73, 24, -50, -33, 49, 7, 63, 63, -90, -99, 71, 67, -49, 59, 25, -59, 82, 41, -121, 125, -50, 48, 125, -74, -10, -69, 88, 96, 51, -11, 31, -13, -63, -39, -28, 103, 108, 83, -17, -105, 77, 31, 77, -21, 25, 91, 87, -110, 38, 56, -126, 66, 45, -50, -19, -101, -83, -36, 119, 92, 28, -118, 120, 117, -5, 108, -46, 18, -59, 75, 104, 80, 97, 93, 42, -125, 74, -21, 7, 68, -80, -73, 27, -55, -79, -125, 67, 126, 14, -58, -48, -58, -123, 5, 28, -119, -4, -123, -16, -48, -49, -92, 70, -32, 62, 67, -75, 34, -53, -91, -51, 117, -103, -97, -33, -50, -3, 28, 74, -50, -24, 9, -95, 103, -63, -28, -18, 9, -93, -125, 112, -88, 127, 7, -72, -102, -122, 95, 35, 94, 86, 24, 69, 17, -88, -89, 74, -72, -35, -128, 116, 46, -112, -46, 75, 86, 7, 57, -89, 3, -50, 85, -27, 95, -87, 3, 116, 8, -57, -26, 67, -21, -81, -85, -39, -91, 83, 124, -6, 100, 87, 1, -44, -26, -108, -108, 73, 9, 106, 9, 107, -59, 106, 67, -49, -17, 83};

    public static String hookPackName="com.xxx.yyy";

    public static String thisPackName="";
    public static boolean shouldHook=true;

    private static PackageInfo shouldHookSign(String packName,PackageInfo packageInfo){
        if (shouldHook){
            if (packName.equals(thisPackName)||packName.equals(hookPackName)){
//            if (packName.equals(hookPackName)){
                if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                    Signature[] signatures = packageInfo.signatures;

                    for (int i = 0; i < signatures.length; i++) {
                        signatures[i]=new Signature(bytes);

                        Log.e(TAG, "invoke: set.signatures[" + i + "]=" + Arrays.toString(signatures[i].toByteArray()));
                    }
                }
            }
        }
        return packageInfo;
    }
    public static void hookIPackManager(Context context) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        thisPackName=context.getPackageName();
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
                if ("getPackageInfo".equals(method.getName())) {
                    String packName = (String) objArr[0];

                    PackageInfo packageInfo = (PackageInfo) method.invoke(obj, objArr);
                    if (!Integer.valueOf(64).equals(objArr[1])){
                        return packageInfo;
                    }
                    Log.e(TAG, "invoke: getPackageInfo  p=" + Arrays.toString(objArr));
                    if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        Signature[] signatures = packageInfo.signatures;
                        for (int i = 0; i < signatures.length; i++) {
                            Log.e(TAG, "invoke: packageInfo.signatures[" + i + "]=" + Arrays.toString(signatures[i].toByteArray()));
                        }
                    }
                    return shouldHookSign(packName,packageInfo);
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
