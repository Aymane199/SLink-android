package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.UserFolder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserApiServices {

    @GET("user")
    Call<List<User>> getUsers();

    @POST("user")
    Call<User> createUser(@Body User user);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") int id);

    @PATCH("user/{id}")
    Call<User> updateUser(@Path("id") int id,@Body User user);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @GET("user/{id}/allfolders")
    Call<List<UserFolder>> getUserAllFolders(@Path("id") int id,
                                             @Query("search") String search);

    @GET("user/{id}/folder")
    Call<List<UserFolder>> getUserFolders(@Path("id") int id,
                                          @Query("search") String search);

    @GET("user/{id}/share")
    Call<List<UserFolder>> getUserShare(@Path("id") int id ,
                                        @Query("search") String search );

    @GET("user/{id}/subscribe")
    Call<List<UserFolder>> getUserSubscribe(@Path("id") int id,
                                            @Query("search") String search );

    @GET("user/{id}/save")
    Call<List<UserFolder>> getUserSave(@Path("id") int id,
                                       @Query("search") String search);




}
