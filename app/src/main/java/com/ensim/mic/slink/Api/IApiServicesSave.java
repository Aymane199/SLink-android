package com.ensim.mic.slink.Api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiServicesSave {


    @POST("save")
    Call<Object> createSave(@Body HashMap<String, Object> body);

    @DELETE("save/{id}")
    Call<Void> deleteSave(@Path("id") int id,@Query("idUser") int idUser,@Query("idLink") String idLink);


}
