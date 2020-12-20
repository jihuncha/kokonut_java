package com.huni.engineer.kokonutjava.proto.retrofit;

import com.huni.engineer.kokonutjava.proto.JSFoodInfo;
import com.huni.engineer.kokonutjava.proto.JSLoginInfo;
import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;
import com.huni.engineer.kokonutjava.proto.response.JSresponseLoginInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @POST("signin")
    Call<JSresponseLoginInfo> login(@Body JSLoginInfo temp); //body에 temp 전송

    @POST("signin")
    Call<JSLoginInfo> login(@Field("loginId") String loginId, @Field("loginPassword") String loginPassword);

    @Multipart
    @POST("{userId}/food/analyze")
    Call<JSresponseAnalyze> analyze(@Path("userId") String userId,
                                    @Header("Authentication") String header,
                                    @Part MultipartBody.Part image);

}