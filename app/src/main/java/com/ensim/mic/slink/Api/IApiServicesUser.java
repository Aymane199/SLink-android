package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.FolderOfUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiServicesUser {

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
    Call<List<FolderOfUser>> getUserAllFolders(@Path("id") int id,
                                               @Query("search") String search);

    @GET("user/{id}/folder")
    Call<List<FolderOfUser>> getUserFolders(@Path("id") int id,
                                            @Query("search") String search);

    @GET("user/{id}/share")
    Call<List<FolderOfUser>> getUserShare(@Path("id") int id ,
                                          @Query("search") String search );

    @GET("user/{id}/subscribe")
    Call<List<FolderOfUser>> getUserSubscribe(@Path("id") int id,
                                              @Query("search") String search );

    @GET("user/{id}/button_save")
    Call<List<FolderOfUser>> getUserSave(@Path("id") int id,
                                         @Query("search") String search);




}
