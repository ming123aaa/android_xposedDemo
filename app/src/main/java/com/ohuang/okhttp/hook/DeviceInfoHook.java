package com.ohuang.okhttp.hook;

import android.content.ContentResolver;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DeviceInfoHook {

    public static final String TAG="DeviceInfoHook";

    public void hook(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod(Settings.Secure.class.getName(), classLoader, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                String args = "";
                for (Object arg : param.args) {
                    args = arg + "-";
                }
                if (args.startsWith("android_id")){
                    param.setResult("123456");
                }

                Log.d(TAG,  "Settings Secure getString args:" + args + ",result:" + result);
            }
        });
        XposedHelpers.findAndHookMethod(Settings.System.class.getName(), classLoader, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                String args = "";
                for (Object arg : param.args) {
                    args = arg + "-";
                }
                if (args.startsWith("android_id")){
                    param.setResult("123456");
                }

                Log.d(TAG,  "Settings System getString args:" + args + ",result:" + result);
            }
        });


        ////////////////////////////////////////////////InetAddress
        XposedHelpers.findAndHookMethod(InetAddress.class.getName(), classLoader, "getHostAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getHostAddress :" + result);
            }
        });
        ////////////////////////////////////////////////NetworkInterface
        XposedHelpers.findAndHookMethod(NetworkInterface.class.getName(), classLoader, "getHardwareAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getHardwareAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(NetworkInterface.class.getName(), classLoader, "getInetAddresses", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getInetAddresses :" + result);
            }
        });
        ////////////////////////////////////////////////WifiManager
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), classLoader, "getIpAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "WifiInfo getIpAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), classLoader, "getMacAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "WifiInfo getMacAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), classLoader, "getSSID", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "WifiInfo getSSID :" + result);
            }
        });

        ////////////////////////////////////////////////TelephonyManager
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getDeviceId", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getDeviceId :" + result);
                //trowLog(hookMethodTagPrefix + "getDeviceId :" + result);
            }
        });


        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getDeviceId", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        Log.d(TAG,   "getDeviceId(int) :" + result);
                        //trowLog(hookMethodTagPrefix + "getDeviceId(int) :" + result);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getImei", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getImei :" + result);
            }
        });

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getMeid", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getMeid :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getNai", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getNai :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getSimSerialNumber", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                Log.d(TAG,   "getSimSerialNumber :" + result);
            }
        });

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getSubscriberId", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        Log.d(TAG,   "getSubscriberId获取了imsi :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getLine1Number", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        Log.d(TAG,   "getLine1Number :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getNetworkOperator", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        Log.d(TAG,   "getNetworkOperator :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), classLoader, "getNetworkOperatorName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        Log.d(TAG,   "getNetworkOperatorName :" + result);
                    }
                }
        );
    }
}
