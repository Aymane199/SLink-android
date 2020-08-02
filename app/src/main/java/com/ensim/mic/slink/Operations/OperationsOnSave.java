package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesSave;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnSave {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesSave iApiServicesSave;
    State state;


    public OperationsOnSave() {
        iApiServicesSave = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesSave.class);
        state = State.getInstance();
    }

    //call like and update links state
    public void setSave(int linkId) {

        System.out.println("set Save");
        //create body
        HashMap<String, Object> body = new HashMap<>();
        body.put("link",linkId);
        body.put("user",userId);

        // etablish the request
        Call<Object> call = iApiServicesSave.createSave(body);
        //fill the folder list
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    //state.setLinksState(RequestState.FAILED);
                    return;
                }
                System.out.println("saved successfully");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void deleteSave(int userId,int linkId) {

        System.out.println("delete Save");

        // etablish the request
        Call<Void> call = iApiServicesSave.deleteSave(3,userId,linkId);
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
                //
                System.out.println("saved successfully");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
