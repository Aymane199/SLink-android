package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.LinkOfFolder;

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

public interface IApiServicesFolder {


    @GET("folder")
    Call<List<Folder>> getFolders();


    @POST("folder")
    Call<Folder> createFolder(
            @Body HashMap<String, Object> body
            );

    @GET("folder/{id}")
    Call<Folder> getFolder(@Path("id") int id);

    @PATCH("folder/{id}")
    Call<Folder> updateFolder(@Path("id") int id,@Body HashMap<String, Object> body);

    @DELETE("folder/{id}")
    Call<Folder> deleteFolder(@Path("id") int id);

    //folder/6/links?iduser=3&search=co
    @GET("folder/{id}/links")
    Call<List<LinkOfFolder>> getFolderLinks(@Path("id") String id,
                                            @Query("iduser") String idUser,
                                            @Query("search") String search);



}
