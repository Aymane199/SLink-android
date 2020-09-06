package com.ensim.mic.slink.Retrofit;

import com.ensim.mic.slink.Table.Link;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiServicesLink {


    @GET("link/{id}")
    Call<Link> showLink(@Path("id") int id);

    @POST("link")
    Call<Object> createLink(@Body HashMap<String, Object> body);

    @DELETE("link/{id}")
    Call<Object> deleteLink(@Path("id") int id);



}
