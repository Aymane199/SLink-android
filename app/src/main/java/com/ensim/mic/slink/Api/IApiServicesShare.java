package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.Table.SharePersonne;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiServicesShare {

    @POST("share")
    Call<Object> createShare(@Body HashMap<String, Object> body);



}
