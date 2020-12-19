package com.huni.engineer.kokonutjava.proto.retrofit;

import com.huni.engineer.kokonutjava.proto.JSLoginInfo;
import com.huni.engineer.kokonutjava.proto.response.JSresponseLoginInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface ApiService {
    @POST("signin")
    Call<JSresponseLoginInfo> login(@Body JSLoginInfo temp); //body에 temp 전송

    @POST("signin")
    Call<JSLoginInfo> login(@Field("loginId") String loginId, @Field("loginPassword") String loginPassword);

}