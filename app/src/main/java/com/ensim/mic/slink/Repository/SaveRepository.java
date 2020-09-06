package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesSave;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveRepository {

    //user state
    private int userId;

    private IApiServicesSave iApiServicesSave;
    private Model model;


    public SaveRepository() {
        iApiServicesSave = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesSave.class);
        model = Model.getInstance();
        userId = Model.getInstance().getCurrentUser().getContent().getId();
    }

    //call like and update links state
    public void setSave(final Link link) {

        Model.getInstance().getSavedLinks().setState(RequestState.LOADING);
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
                    Model.getInstance().getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                List<Link> content = Model.getInstance().getSavedLinks().getContent();
                content.add(0,link);
                Model.getInstance().getSavedLinks().setContent(content);
                Model.getInstance().getSavedLinks().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                Model.getInstance().getSavedLinks().setState(RequestState.FAILED);

            }
        });
    }

    public void deleteSave(final Link link) {
        Model.getInstance().getSavedLinks().setState(RequestState.LOADING);

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
                    Model.getInstance().getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                //
                System.out.println("saved successfully");
                List<Link> content = Model.getInstance().getSavedLinks().getContent();
                content.remove(link);
                Model.getInstance().getSavedLinks().setContent(content);
                Model.getInstance().getSavedLinks().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                Model.getInstance().getSavedLinks().setState(RequestState.FAILED);

            }
        });
    }

}
