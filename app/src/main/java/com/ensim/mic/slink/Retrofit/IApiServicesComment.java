package com.ensim.mic.slink.Retrofit;

import com.ensim.mic.slink.Table.Comment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiServicesComment {

    @GET("comment")
    Call<List<Comment>> showComments();

    @POST("comment")
    Call<Comment> createComment(@Body HashMap<String, Object> body);

    @DELETE("comment/{id}")
    Call<Void> deleteComment(@Path("id") int id);


}
