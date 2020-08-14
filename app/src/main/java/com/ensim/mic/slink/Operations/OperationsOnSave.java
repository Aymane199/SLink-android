package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesSave;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnSave {

    //user state
    private int userId;

    private IApiServicesSave iApiServicesSave;
    private State state;


    public OperationsOnSave() {
        iApiServicesSave = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesSave.class);
        state = State.getInstance();
        userId = State.getInstance().getCurrentUser().getContent().getId();
    }

    //call like and update links state
    public void setSave(final LinkOfFolder link) {

        State.getInstance().getSavedLinks().setState(RequestState.LOADING);
        System.out.println("set Save");
        //create body
        HashMap<String, Object> body = new HashMap<>();
        body.put("link",link.getId());
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
                    State.getInstance().getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                List<LinkOfFolder> content = State.getInstance().getSavedLinks().getContent();
                content.add(0,link);
                State.getInstance().getSavedLinks().setContent(content);
                State.getInstance().getSavedLinks().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                State.getInstance().getSavedLinks().setState(RequestState.FAILED);

            }
        });
    }

    public void deleteSave(final LinkOfFolder link) {
        State.getInstance().getSavedLinks().setState(RequestState.LOADING);

        System.out.println("delete Save");

        // etablish the request
        Call<Void> call = iApiServicesSave.deleteSave(0,userId,link.getId());
        //fill the folder list
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    State.getInstance().getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                //
                System.out.println("saved successfully");
                List<LinkOfFolder> content = State.getInstance().getSavedLinks().getContent();
                content.remove(link);
                State.getInstance().getSavedLinks().setContent(content);
                State.getInstance().getSavedLinks().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                State.getInstance().getSavedLinks().setState(RequestState.FAILED);

            }
        });
    }

}
