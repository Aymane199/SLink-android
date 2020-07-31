package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesShare;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.State.StateListFolders;
import com.ensim.mic.slink.State.StateListSharePersonnes;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.utils.FolderFilter;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnShare {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesFolder iApiServicesFolder;
    IApiServicesShare iApiServicesShare;
    State state;


    public OperationsOnShare() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesShare = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesShare.class);
        state = State.getInstance();
    }

    //call user folder and update the state
    public void dispalySharePersonnes(int idFolder) {

        state.getSharePersonnesList().setState(RequestState.LOADING);
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
                    state.getSharePersonnesList().setState(RequestState.FAILED);
                    return;
                }
                //update state
                state.getSharePersonnesList().setObject(response.body());
                state.getSharePersonnesList().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<List<SharePersonne>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSharePersonnesList().setState(RequestState.FAILED);
            }
        });
    }

    public void addPersonne(int idFolder, int userId){
        state.getSharePersonnesList().setState(RequestState.LOADING);
        System.out.println("add Personne");
        // etablish the request
        Call<Object> call;
        HashMap<String, Object> body = new HashMap<>();
        body.put("folder",idFolder);
        body.put("user",userId);
        //body.put("access_right","edit");
        call = iApiServicesShare.createShare(body);

        //fill the list
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getSharePersonnesList().setState(RequestState.FAILED);
                    return;
                }
                //update state
                //List<Object> listPersonnes = state.getSharePersonnes().getListPersonnes();
                //listPersonnes.add(response.body());
                System.out.println(response.body());
                state.getSharePersonnesList().setObject(new ArrayList<SharePersonne>());
                state.getSharePersonnesList().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSharePersonnesList().setState(RequestState.FAILED);
            }
        });
    }

}
