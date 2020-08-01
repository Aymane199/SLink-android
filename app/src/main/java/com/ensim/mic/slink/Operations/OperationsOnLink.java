package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesLink;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnLink {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    private IApiServicesUser iApiServicesUser;
    private IApiServicesFolder iApiServicesFolder;
    private IApiServicesLink iApiServicesLink;
    private State state;


    public OperationsOnLink() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        state = State.getInstance();
    }

    public void displayLinks(String searchText, final String idFolder, String idUser) {
        state.getLinks().setState(RequestState.LOADING);

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
                    state.getLinks().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.getLinks().setContent(response.body());
                state.getLinks().setFolderId(Integer.parseInt(idFolder));
                state.getLinks().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getLinks().setState(RequestState.FAILED);
            }
        });
    }
    public void displaySavedLinks(String searchText, String idUser) {
        state.getSavedLinks().setState(RequestState.LOADING);

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
                    state.getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                state.getSavedLinks().setContent(response.body());
                state.getSavedLinks().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getSavedLinks().setState(RequestState.FAILED);
            }
        });
    }



    public void deleteLink(final LinkOfFolder link){
        state.getLinks().setState(RequestState.LOADING);
        final int linkid = Integer.parseInt(link.getId());
        Call<Object> call = iApiServicesLink.deleteLink(linkid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getLinks().setState(RequestState.FAILED);
                    return;
                }
                List<LinkOfFolder> links = state.getLinks().getContent();
                links.remove(link);
                state.getLinks().setContent(links);
                //update folder state, delete one link
                state.getFolders().deletelink(state.getLinks().getFolderId());
                state.getLinks().setState(RequestState.SUCCESSFUL);
                state.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                state.getLinks().setState(RequestState.FAILED);
            }
        });
    }


}
