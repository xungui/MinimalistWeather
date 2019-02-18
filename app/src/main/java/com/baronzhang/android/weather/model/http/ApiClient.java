package com.baronzhang.android.weather.model.http;

import com.baronzhang.android.weather.BuildConfig;
import com.baronzhang.android.weather.model.http.service.EnvironmentCloudWeatherService;
import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 * 16/2/25
 */
public final class ApiClient {
    static final String ENVIRONMENT_CLOUD_WEATHER_API_HOST = "http://service.envicloud.cn:8082/";
    private static EnvironmentCloudWeatherService environmentCloudWeatherService;

    public static EnvironmentCloudWeatherService getService() {
        String weatherApiHost = ENVIRONMENT_CLOUD_WEATHER_API_HOST;
        environmentCloudWeatherService = initWeatherService(weatherApiHost, EnvironmentCloudWeatherService.class);
        return environmentCloudWeatherService;
    }

    private static <T> T initWeatherService(String baseUrl, Class<T> clazz) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
//            builder.addNetworkInterceptor(new StethoInterceptor());
            BuildConfig.STETHO.addNetworkInterceptor(builder);
        }
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(clazz);
    }

}

