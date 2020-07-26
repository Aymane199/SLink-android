package com.ensim.mic.slink.Operations;

import android.app.Activity;
import android.widget.Toast;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesLink;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.State.StateListLinks;
import com.ensim.mic.slink.State.StateListSavedLinks;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnLink {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesUser iApiServicesUser;
    IApiServicesFolder iApiServicesFolder;
    IApiServicesLink iApiServicesLink;
    State state;


    public OperationsOnLink() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        state = State.getInstance();
    }

    public void displayLinks(String searchText, final String idFolder, String idUser) {
        state.setLinksState(RequestState.LOADING);

        // etablish the request
        Call<List<LinkOfFolder>> call = iApiServicesFolder.getFolderLinks(idFolder, idUser, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<LinkOfFolder>>() {

            @Override
            public void onResponse(Call<List<LinkOfFolder>> call, Response<List<LinkOfFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setLinksState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.setLinks(new StateListLinks(Integer.parseInt(idFolder),response.body(), RequestState.SUCCESSFUL));
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.setLinksState(RequestState.FAILED);
            }
        });
    }
    public void displaySavedLinks(String searchText, String idUser) {
        state.setSavedLinksState(RequestState.LOADING);

        // etablish the request
        Call<List<LinkOfFolder>> call = iApiServicesUser.getFolderSaved(idUser, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<LinkOfFolder>>() {

            @Override
            public void onResponse(Call<List<LinkOfFolder>> call, Response<List<LinkOfFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setSavedLinksState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.setSavedLinks(new StateListSavedLinks(response.body(), RequestState.SUCCESSFUL));
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.setSavedLinksState(RequestState.FAILED);
            }
        });
    }



    public void deleteLink(final LinkOfFolder link){
        state.setLinksState(RequestState.LOADING);
        final int linkid = Integer.parseInt(link.getId());
        Call<Object> call = iApiServicesLink.deleteLink(linkid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setLinksState(RequestState.FAILED);
                    return;
                }
                List<LinkOfFolder> links = state.getLinks().getListLinks();
                links.remove(link);
                state.setLinksList(links);
                //update folder state, delete one link
                state.getFolders().deletelink(state.getLinks().getFolderId());
                state.setLinksState(RequestState.SUCCESSFUL);
                state.setFoldersState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                state.setLinksState(RequestState.FAILED);
            }
        });
    }


}
