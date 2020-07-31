package com.ensim.mic.slink.Operations;

import android.app.Activity;
import android.widget.Toast;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesLink;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.LinksState;
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
        state.getLinksList().setState(RequestState.LOADING);

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
                    state.getLinksList().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.getLinksList().setObject(response.body());
                state.getLinksList().setFolderId(Integer.parseInt(idFolder));
                state.getLinksList().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getLinksList().setState(RequestState.FAILED);
            }
        });
    }
    public void displaySavedLinks(String searchText, String idUser) {
        state.getSavedLinksList().setState(RequestState.LOADING);

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
                    state.getSavedLinksList().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.getSavedLinksList().setObject(response.body());
                state.getSavedLinksList().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSavedLinksList().setState(RequestState.FAILED);
            }
        });
    }



    public void deleteLink(final LinkOfFolder link){
        state.getLinksList().setState(RequestState.LOADING);
        final int linkid = Integer.parseInt(link.getId());
        Call<Object> call = iApiServicesLink.deleteLink(linkid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getLinksList().setState(RequestState.FAILED);
                    return;
                }
                List<LinkOfFolder> links = state.getLinksList().getObject();
                links.remove(link);
                state.getLinksList().setObject(links);
                //update folder state, delete one link
                state.getFoldersList().deletelink(state.getLinksList().getFolderId());
                state.getLinksList().setState(RequestState.SUCCESSFUL);
                state.getFoldersList().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                state.getLinksList().setState(RequestState.FAILED);
            }
        });
    }


}
