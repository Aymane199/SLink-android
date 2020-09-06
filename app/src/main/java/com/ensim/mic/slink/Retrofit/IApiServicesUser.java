package com.ensim.mic.slink.Retrofit;

import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.Table.User;

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

public interface IApiServicesUser {

    @GET("user")
    Call<List<User>> getUsers();

    @POST("user")
    Call<User> createUser(@Body HashMap<String, Object> body    );

    @GET("user/{id}")
    Call<User> getUser(@Path("id") int id);

    /**
     * route to get user using userName or Gmail
     *
     * @return
     */
    @GET("user/search")
    Call<User> getUserByUserName(@Query("userName") String search);

    @GET("user/search")
    Call<User> getUserByGmail(@Query("Gmail") String search);

    @PATCH("user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body HashMap<String, Object> body);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @GET("user/{id}/allfolders")
    Call<List<FolderWithoutUser>> getUserAllFolders(@Path("id") int id,
                                                    @Query("search") String search);

    @GET("user/{id}/folder")
    Call<List<FolderWithoutUser>> getUserFolders(@Path("id") int id,
                                                 @Query("search") String search);

    @GET("user/{id}/share")
    Call<List<FolderWithoutUser>> getUserShare(@Path("id") int id,
                                               @Query("search") String search);

    @GET("user/{id}/subscribe")
    Call<List<FolderWithoutUser>> getUserSubscribe(@Path("id") int id,
                                                   @Query("search") String search);

    @GET("user/{id}/save")
    Call<List<Link>> getFolderSaved(@Path("id") String idUser,
                                    @Query("search") String searchText);
}
