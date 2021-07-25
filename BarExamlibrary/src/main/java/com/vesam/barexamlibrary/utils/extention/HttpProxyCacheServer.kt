package com.vesam.barexamlibrary.utils.extention

import android.content.Context
import com.danikula.videocache.HttpProxyCacheServer

fun getProxy(context: Context): HttpProxyCacheServer {
    return HttpProxyCacheServer.Builder(context)
        .maxCacheSize((1024 * 1024 * 1024).toLong()) // 1 Gb for cache
        .build()
}
