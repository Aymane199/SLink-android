package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.SharePersonne;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiServicesShare {

    @POST("share")
    Call<SharePersonne> createShare(@Body HashMap<String, Object> body);

    @DELETE("share/{id}")
Call<Object> deleteShare(@Path("id")int id);

    @DELETE("share")
    Call<Void> deleteShare(
            @Query("user") int idUser,
            @Query("folder") int idfolder);
}
