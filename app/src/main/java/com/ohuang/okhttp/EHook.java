package com.ohuang.okhttp;

import android.content.Context;



import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.internal.os.ZygoteInit$5;
import com.android.internal.os.ZygoteInit$7;

public abstract class EHook extends com.android.internal.os.XE_MethodLink {


    protected static final Object OBJECT = new Object();
    private static final HashMap<String, Method> methodCache;

    public abstract String getClassName();

    private List<Unlink> Unlinks;
    private ClassLoader mClassLoader;
    private Class<?> mClass;

    protected static Context mContext;


    static {

        methodCache = new HashMap<String, Method>();
    }





    /**
     * 缓存 class 加速
     *
     * @return
     */
    protected Class<?> toClass() {
        if (mClass == null) {
            try {
                mClass = ZygoteInit$7.findClass(getClassName(), mClassLoader);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            if (mClass == null) {
                try {
                    mClass = Class.forName(getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return mClass;
    }

    public Class<?> findClass(String cls) {
        return ZygoteInit$7.findClass(cls, mClassLoader);
    }

    public Class<?> findClass(String cls, ClassLoader classLoader) {
        return ZygoteInit$7.findClass(cls, classLoader);
    }

    public void hook() {

    }

    public EHook setClassLoader(ClassLoader cl) {
        this.mClassLoader = cl;
        return this;
    }

    public ClassLoader getClassLoader() {
        return mClassLoader;
    }

    public void UnlinkAll() {
        if (Unlinks != null) {


            synchronized (this) {
                for (Unlink u : Unlinks)
                    u.unlink();
                Unlinks.clear();
            }
        }
    }

    @Override
    protected void beforeLinkedMethod(MethodLinkParam param) throws Throwable {
        boolean ret = before(param.method.getName(), param.args, param);
        if (ret && param.getResult() == null) { //如果返回 true 这里会导致结果覆盖
            param.setResult(OBJECT);
        }
    }

    @Deprecated
    protected void findAndLinkMethod(String methodName, Object... parameterTypesAndCallback) {
        linkMethod(methodName, parameterTypesAndCallback);
    }

    protected boolean linkMethod(String methodName, Object... parameterTypesAndCallback) {
        return linkMethod(toClass(), methodName, parameterTypesAndCallback);
    }

    private static String getParametersString(Class<?>... clazzes) {
        StringBuilder sb = new StringBuilder("(");
        boolean first = true;
        Class[] var3 = clazzes;
        int var4 = clazzes.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Class<?> clazz = var3[var5];
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }

            if (clazz != null) {
                sb.append(clazz.getCanonicalName());
            } else {
                sb.append("null");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    protected boolean linkMethod(Class<?> cls, String methodName, Object... parameterTypesAndCallback) {
        try {
            Method methodExact = null;

            try {
                if (parameterTypesAndCallback.length > 1) {
                    Class[] args = new Class[parameterTypesAndCallback.length - 1];
                    for (int i = 0; i < args.length; i++) {
                        args[i] = (Class) parameterTypesAndCallback[i];
                    }
                    String fullname = cls.getName() + '#' + methodName + getParametersString(args) + "#exact";
                    if (methodCache.containsKey(fullname)) {
                        methodExact = methodCache.get(fullname);
                    } else {
                        Throwable te = null;
                        try {
                            methodExact = cls.getDeclaredMethod(methodName, args);
                        } catch (Exception e) {
                            try {
                                methodExact = cls.getMethod(methodName, args);
                            } catch (Exception e2) {
                                te = e2;
                            }
                        }

                        if (methodExact == null)
                            methodExact = ZygoteInit$7.preparePointEend(cls, methodName, parameterTypesAndCallback);
                        if (methodExact == null) {

                        }

                        methodCache.put(fullname, methodExact);
                    }
                } else {
                    String fullname = cls.getName() + "#" + methodName + "#exact";
                    if (methodCache.containsKey(fullname)) {
                        methodExact = methodCache.get(fullname);
                    } else {
                        Throwable te = null;
                        try {
                            methodExact = cls.getDeclaredMethod(methodName);
                        } catch (Exception e) {
                            try {
                                methodExact = cls.getMethod(methodName);
                            } catch (Exception e2) {
                                te = e2;
                            }
                        }

                        if (methodExact == null)
                            methodExact = ZygoteInit$7.preparePointEend(cls, methodName, parameterTypesAndCallback);

                        if (methodExact == null) {

                        }

                        methodCache.put(fullname, methodExact);
                    }
                }
            } catch (Throwable e) {
            }

            if (methodExact != null)
                ZygoteInit$5.linkMethod(methodExact, (com.android.internal.os.XE_MethodLink) parameterTypesAndCallback[parameterTypesAndCallback.length - 1]);
            else {

                throw new NoSuchMethodException(cls.getName() + "#" + methodName);
            }

            return true;
        } catch (Throwable e) {
            if (cls != null && cls.getSuperclass() != null && cls.getSuperclass() != Object.class) {
                return linkMethod(cls.getSuperclass(), methodName, parameterTypesAndCallback);
            }

            ZygoteInit$5.log(e);

        }
        return false;
    }

    /**
     * 0 arguments
     *
     * @param methodName
     */
    protected void linkMethod0(String methodName) {
        linkMethod(methodName, this);
    }

    protected void linkMethod_r(String methodName, Object... parameterTypesAndCallback) {
        try {
            Unlink un = ZygoteInit$7.findAndLinkMethod(toClass(),
                    methodName, parameterTypesAndCallback);
            synchronized (this) {
                if (Unlinks == null)
                    Unlinks = new ArrayList<Unlink>();
                Unlinks.add(un);
            }
        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    @Deprecated
    protected void linkAllMethods(String name) {
        linkMethodAll(name);
    }


    protected void hookJavaAllMethods() {
        try {
            Method[] ms = toClass().getDeclaredMethods();
            for (Method m : ms) {
                if (!Modifier.isNative(m.getModifiers()))
                    linkMethod(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hook 名字为 name 的所有方法
     *
     * @param name
     */
    protected void linkMethodAll(String name) {
        try {
            ZygoteInit$5.linkAllMethods(toClass(), name, this);
        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    protected void linkMethodAll(Class<?> cls, String name, com.android.internal.os.XE_MethodLink args) {
        try {
            ZygoteInit$5.linkAllMethods(cls, name, args);
        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    protected void linkMethodAll(Class<?> cls, com.android.internal.os.XE_MethodLink args) {
        try {
            Method[] var7;
            int var6 = (var7 = cls.getDeclaredMethods()).length;

            for (int var5 = 0; var5 < var6; ++var5) {
                Member method = var7[var5];
                ZygoteInit$5.linkAllMethods(cls, method.getName(), args);
            }

        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    /**
     * hook 参数最多的一个
     *
     * @param name
     */
    protected void linkMethodLast(String name) {
        linkMethodLast(toClass(), name, true);
    }

    protected void linkMethodLess(String name) {
        linkMethodLast(toClass(), name, false);
    }

    /**
     * 查找并 hook 参数最多的方法
     *
     * @param clz
     * @param name
     * @param more true 参数最多的，否则参数最少的
     */
    protected void linkMethodLast(Class<?> clz, String name, boolean more) {
        if (clz == null) {

            return;
        }
        try {
            int cur = -1;
            Method linkMethod = null;

            for (Method method : clz.getDeclaredMethods()) {
                if (method.getName().equals(name)) {
                    int l = method.getParameterTypes().length;
                    if (cur == -1 || (more ? l > cur : l < cur)) {
                        cur = l;
                        linkMethod = method;
                    }
                }
            }

            if (linkMethod != null) {
                Class<?>[] classes = linkMethod.getParameterTypes();

                StringBuilder sb = new StringBuilder(64);
                sb.append(linkMethod.getName()).append('(');
                for (Class<?> cls : classes) {
                    sb.append(cls.getName()).append(", ");
                }


                linkMethod(linkMethod);
            } else {

            }
        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    protected void linkMethod(Method m) {
        ZygoteInit$5.linkMethod(m, this);
    }

    /**
     * hook 类的所有方法，慎用
     */
    protected void linkAllMethods() {
        try {
            Method[] ms = toClass().getDeclaredMethods();
            for (Method m : ms) {
                linkMethod(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void linkAllConstructors() {
        try {
            ZygoteInit$5.linkAllConstructors(toClass(), this);
        } catch (Exception e) {

            ZygoteInit$5.log(e);
        }
    }

    protected boolean hasMethod(String name, Class... params) {
        try {
            return ZygoteInit$7.preparePointBestMatch(toClass(), name, params) != null;
        } catch (Throwable e) {

        }
        return false;
    }

    /**
     * @return true 框架不调用原始版本；false 框架调用原始版本<br>
     * 也可以返回 false 通过 param.setResult 返回一个你需要的结果给系统，框架不再调用原始版本
     */
    protected boolean before(String name, Object[] args, MethodLinkParam param) {
        return false;
    }

    @Override
    protected void afterLinkedMethod(MethodLinkParam param) throws Throwable {
        afterThrow(param.method.getName(), param.args, param);
    }

    protected void after(String name, Object[] args, MethodLinkParam param) {
    }

    protected void afterThrow(String name, Object[] args, MethodLinkParam param) throws Throwable {
        after(name, args, param);
    }

    /**
     * @return 失败 -1
     */
    protected int findTypeIndex(Object[] args, Class<?> type) {
        for (int i = 0; i < args.length; ++i) {
            if (args[i] != null && type == args[i].getClass())
                return i;
        }

        return -1;
    }

    protected void printAfter(String tag, MethodLinkParam param) {
        StringBuilder sb = new StringBuilder(64);
        if (param.args != null) {
            for (Object arg : param.args) {
                sb.append(arg).append(',');
            }
        }

    }

    /**
     * 获取对象类名
     *
     * @param o
     * @return
     */
    protected String cname(Object o) {
        return o == null ? "" : o.getClass().getName();
    }
}
