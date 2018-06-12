package com.lwlizhe.novelvideoapp.common.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.lwlizhe.basemodule.base.ActivityManager;
import com.lwlizhe.basemodule.di.module.AppModule;
import com.lwlizhe.basemodule.di.module.ClientModule;
import com.lwlizhe.basemodule.di.module.EventModule;
import com.lwlizhe.basemodule.di.module.GlobeConfigModule;
import com.lwlizhe.basemodule.di.module.ImageModule;

import com.lwlizhe.basemodule.event.RxEventBus;
import com.lwlizhe.basemodule.imageloader.glide.GlideImageLoaderStrategy;
import com.lwlizhe.novelvideoapp.common.di.module.cache.CacheModule;
import com.lwlizhe.novelvideoapp.common.di.module.service.ServiceModule;
import com.lwlizhe.novelvideoapp.common.cache.manager.CacheManager;
import com.lwlizhe.novelvideoapp.common.service.manager.ServiceManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ImageModule.class, ClientModule.class, ServiceModule.class, CacheModule.class,GlobeConfigModule.class, EventModule.class})
public interface AppComponent {
    Application Application();

    OkHttpClient okHttpClient();

    ServiceManager serviceManager();

    CacheManager cacheManager();

    GlideImageLoaderStrategy mGlideImageLoader();

    RxEventBus eventBus();

    //gson
    Gson gson();

    //用于管理所有activity
    ActivityManager appManager();
}