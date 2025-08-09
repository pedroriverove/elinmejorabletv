package com.elinmejorabletv.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "dQNwRa/HfDBNK0oar9bV6m1MU+m34RvOTAbGkIDpvHM=")
            .build()

        return chain.proceed(newRequest)
    }
}