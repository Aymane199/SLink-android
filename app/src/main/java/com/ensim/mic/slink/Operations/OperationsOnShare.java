package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesShare;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnShare {

    private IApiServicesFolder iApiServicesFolder;
    private IApiServicesShare iApiServicesShare;
    private State state;


    public OperationsOnShare() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesShare = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesShare.class);
        state = State.getInstance();
    }

    //call user folder and update the state
    public void dispalySharePersonnes(int idFolder) {

        state.getSharePeople().setState(RequestState.LOADING);
        System.out.println("display Share Personnes");
        // etablish the request
        Call<List<SharePersonne>> call;
        //set filter
        call = iApiServicesFolder.getSharePersonnes(idFolder);

        //fill the list
        call.enqueue(new Callback<List<SharePersonne>>() {
            @Override
            public void onResponse(Call<List<SharePersonne>> call, Response<List<SharePersonne>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getSharePeople().setState(RequestState.FAILED);
                    return;
                }
                //update state
                state.getSharePeople().setContent(response.body());
                state.getSharePeople().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<List<SharePersonne>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSharePeople().setState(RequestState.FAILED);
            }
        });
    }

    public void addPersonne(int idFolder, int userId){
        state.getSharePeople().setState(RequestState.LOADING);
        System.out.println("add Personne");
        // etablish the request
        Call<SharePersonne> call;
        HashMap<String, Object> body = new HashMap<>();
        body.put("folder",idFolder);
        body.put("user",userId);
        //body.put("access_right","edit");
        call = iApiServicesShare.createShare(body);

        //fill the list
        call.enqueue(new Callback<SharePersonne>() {
            @Override
            public void onResponse(Call<SharePersonne> call, Response<SharePersonne> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getSharePeople().setState(RequestState.FAILED);
                    return;
                }
                //update state
                SharePersonne personne = response.body();
                assert personne != null;
                personne.setUserName(personne.getUser().getUserName());
                personne.setGmail(personne.getUser().getGmail());
                List<SharePersonne> listPersonnes = state.getSharePeople().getContent();
                listPersonnes.add(personne);
                state.getSharePeople().setContent(listPersonnes);
                state.getSharePeople().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<SharePersonne> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSharePeople().setState(RequestState.FAILED);
            }
        });
    }

    public void deleteShare(final SharePersonne sharePersonne){
        state.getSharePeople().setState(RequestState.LOADING);
        final int id = Integer.parseInt(sharePersonne.getId());
        Call<Object> call = iApiServicesShare.deleteShare(id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getSharePeople().setState(RequestState.FAILED);
                    return;
                }
                List<SharePersonne> content = state.getSharePeople().getContent();
                content.remove(sharePersonne);
                state.getSharePeople().setContent(content);
                state.getSharePeople().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                state.getSharePeople().setState(RequestState.FAILED);
            }
        });
    }

}
