package com.ohunag.xposedutil;

import de.robv.android.xposed.XC_MethodHook;

public interface HookCallBack {
      boolean beforeMethod(XC_MethodHook.MethodHookParam param);

      void beforeMethodEnd(XC_MethodHook.MethodHookParam param);

      boolean afterMethod(XC_MethodHook.MethodHookParam param);
      void afterMethodEnd(XC_MethodHook.MethodHookParam param);
}
