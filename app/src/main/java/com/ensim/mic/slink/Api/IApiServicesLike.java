package com.ensim.mic.slink.Api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiServicesLike {


    @POST("like")
    Call<Object> createLike(@Body HashMap<String, Object> body);

    @DELETE("like")
    Call<Void> deleteLike(@Query("idUser") int idUser,@Query("idLink") int idLink);


}
