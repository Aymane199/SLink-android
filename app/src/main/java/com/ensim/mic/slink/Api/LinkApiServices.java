package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.UserFolder;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LinkApiServices {


    @POST("link")
    Call<Object> createLink(@Body HashMap<String, Object> body);

    /*@GET("link")
    Call<List<User>> getUsers();



    @GET("user/{id}")
    Call<User> getUser(@Path("id") int id);

    @PATCH("user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Path("id") int id);
*/




}
