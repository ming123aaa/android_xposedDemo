package com.ohuang.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("LogInterceptor", "intercept:request host="+request.url.host+" header="+request.headers)
        val proceed = chain.proceed(request)
        Log.d("LogInterceptor", "intercept:response header="+proceed.headers+" body="+proceed.body?.string())
        return proceed
    }
}