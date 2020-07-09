package com.ensim.mic.slink.Api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IApiServicesLink {


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
