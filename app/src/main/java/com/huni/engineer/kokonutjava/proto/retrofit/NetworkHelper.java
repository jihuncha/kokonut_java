package com.huni.engineer.kokonutjava.proto.retrofit;

import android.content.Context;

import com.huni.engineer.kokonutjava.proto.ProtoDefine;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    private Context mContext = null;

    private Retrofit retrofit;
    private ApiService apiService;

    private volatile static NetworkHelper sInstance;

    //TODO 이게맞나..?
    private NetworkHelper(Context context) {
        mContext  = context;

        //Log 찍기 위함.
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ProtoDefine.TB_SERVER_REAL) //api의 baseURL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class); //실제 api Method들이선언된 Interface객체 선언
    }

    public static NetworkHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (NetworkHelper.class) {
                if (sInstance == null) {
                    sInstance = new NetworkHelper(context);
                }
            }
        }
        return sInstance;
    }

    //로깅
    private HttpLoggingInterceptor httpLoggingInterceptor(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                android.util.Log.e("Http : ", message + "");
            }
        });

        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public ApiService getApiService() { // API Interface 객체 얻는 용
        return apiService;
    }

}
