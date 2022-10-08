//package com.ohuang.okhttp;
//
//import android.util.Log;
//
//import com.android.internal.os.ISystemSubCmdInit;
//import com.android.internal.os.ISystemSubLinkerPackage;
//import com.android.internal.os.SystemSubZygoteInit;
//import com.android.internal.os.XE_MethodLink;
//import com.android.internal.os.ZygoteInit$5;
//import com.android.internal.os.ZygoteInit$7;
//import com.android.internal.os.callbacks.XE_Package;
//
//import java.lang.reflect.Field;
//
///**
// * @author DX
// * 注意：该类不要自己写构造方法，否者可能会hook不成功
// * 开发Xposed模块完成以后，建议修改xposed_init文件，并将起指向这个类,以提升性能
// * 所以这个类需要implements IXposedHookLoadPackage,以防修改xposed_init文件后忘记
// * Created by DX on 2017/10/4.
// */
//
//public class HookLogic implements ISystemSubLinkerPackage, ISystemSubCmdInit, SystemSubZygoteInit {
//    private final static String modulePackageName = HookLogic.class.getPackage().getName();
//
//    private static String TAG = "HookLogic";
//
//
//
//
//    @Override
//    public void handleLoadPackage(XE_Package.LoadPackageParam lpparam) throws Throwable {
//
//        if ("com.ohuang.okhttp".equals(lpparam.packageName)) {
//            ZygoteInit$7.findAndLinkConstructor("okhttp3.OkHttpClient.Builder", lpparam.classLoader, new XE_MethodLink() {
//
//                @Override
//                protected void afterLinkedMethod(MethodLinkParam param) throws Throwable {
//                    super.afterLinkedMethod(param);
//                    Object thisObject = param.thisObject;
//                    Field[] declaredFields = thisObject.getClass().getDeclaredFields();
//
//                    Log.d(TAG, "afterHookedMethod: okhttp3.OkHttpClient.Builder" + "  " + lpparam.packageName);
//                }
//            });
//
//        }
//
//
//        ZygoteInit$7.findAndLinkMethod(ClassLoader.class.getName(), Class.class.getClassLoader(), "loadClass", String.class, new XE_MethodLink() {
//            @Override
//            protected void afterLinkedMethod(MethodLinkParam param) throws Throwable {
//                super.afterLinkedMethod(param);
//                String p = (String) param.args[0];
//                Log.d(TAG, "afterHookedMethod: " + lpparam.packageName + "  " + p);
//            }
//        });
//
//    }
//
//    @Override
//    public void initCmdApp(ISystemSubCmdInit.StartupParam startupParam) throws Throwable {
//        Log.d(TAG, "initCmdApp: modulePath="+startupParam.modulePath+" startClassName="+startupParam.startClassName);
//    }
//
//    @Override
//    public void initZygote(SystemSubZygoteInit.StartupParam startupParam) throws Throwable {
//        Log.d(TAG, "initZygote: modulePath="+startupParam.modulePath+" startsSystemServer="+startupParam.startsSystemServer);
//
//    }
//}
