package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesLike;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnLike {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesLike iApiServicesLike;
    State state;


    public OperationsOnLike() {
        iApiServicesLike = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLike.class);
        state = State.getInstance();
    }

    //call like and update links state
    public void setLike(int linkId) {
        System.out.println("set like");
        //create body
        HashMap<String, Object> body = new HashMap<>();
        body.put("link",linkId);
        body.put("user",userId);

        // etablish the request
        Call<Object> call = iApiServicesLike.createLike(body);
        //fill the folder list
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                //update folder state, delete one like
                state.getFoldersList().addlike(state.getLinksList().getFolderId());
                //state.setFoldersState(RequestState.LOADING);
                state.getFoldersList().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void deleteLike(int userId,int linkId) {

        System.out.println("delete like");
        // etablish the request
        Call<Void> call = iApiServicesLike.deleteLike(userId,linkId);
        //fill the folder list
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                //update folder state, delete one like
                state.getFoldersList().deletelike(state.getLinksList().getFolderId());
                //state.setFoldersState(RequestState.LOADING);
                state.getFoldersList().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                //state.setLinksState(RequestState.FAILED);
            }
        });
    }

}
