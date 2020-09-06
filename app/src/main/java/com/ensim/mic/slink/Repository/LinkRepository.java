package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesFolder;
import com.ensim.mic.slink.Retrofit.IApiServicesLink;
import com.ensim.mic.slink.Retrofit.IApiServicesUser;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinkRepository {

    private IApiServicesUser iApiServicesUser;
    private IApiServicesFolder iApiServicesFolder;
    private IApiServicesLink iApiServicesLink;
    private Model model;


    public LinkRepository() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        model = Model.getInstance();
    }

    public void displayLinks(String searchText, final String idFolder, String idUser) {
        model.getLinks().setState(RequestState.LOADING);

        // etablish the request
        Call<List<Link>> call = iApiServicesFolder.getFolderLinks(idFolder, idUser, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<Link>>() {

            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getLinks().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                model.getLinks().setContent(response.body());
                model.getLinks().setFolderId(Integer.parseInt(idFolder));
                model.getLinks().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getLinks().setState(RequestState.FAILED);
            }
        });
    }

    public void displaySavedLinks(String searchText, String idUser) {
        model.getSavedLinks().setState(RequestState.LOADING);

        // etablish the request
        Call<List<Link>> call = iApiServicesUser.getFolderSaved(idUser, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<Link>>() {

            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getSavedLinks().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("body" + response.body());
                model.getSavedLinks().setContent(response.body());
                model.getSavedLinks().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getSavedLinks().setState(RequestState.FAILED);
            }
        });
    }

    public void addLinktoFolder(final FolderWithoutUser folderOutput, Link link, int idUser) {
        model.getLinks().setState(RequestState.LOADING);
        model.getFolders().setState(RequestState.LOADING);

        HashMap<String, Object> body = new HashMap<>();
        if (link.getUrl() != null)
            body.put("URL", link.getUrl());
        else
            return;
        if (link.getPicture() != null)
            body.put("picture", link.getPicture());
        if (link.getName() != null)
            body.put("name", link.getName());
        body.put("folder", folderOutput.getId());
        body.put("owner", idUser);

        Call<Object> call = iApiServicesLink.createLink(body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getLinks().setState(RequestState.FAILED);
                    model.getFolders().setState(RequestState.FAILED);

                    return;
                }
                /*LinkOfFolder link = response.body();
                System.out.println(link.toString());
                List<LinkOfFolder> links = state.getLinks().getContent();
                links.add(link);
                state.getLinks().setContent(links);*/
                model.getFolders().addLink(Integer.parseInt(folderOutput.getId()));
                model.getFolders().setState(RequestState.SUCCESSFUL);
                model.getLinks().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getLinks().setState(RequestState.FAILED);
                model.getFolders().setState(RequestState.FAILED);

            }
        });

    }

    public void deleteLink(final Link link){
        model.getLinks().setState(RequestState.LOADING);
        final int linkid = Integer.parseInt(link.getId());
        Call<Object> call = iApiServicesLink.deleteLink(linkid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getLinks().setState(RequestState.FAILED);
                    return;
                }
                List<Link> links = model.getLinks().getContent();
                links.remove(link);
                model.getLinks().setContent(links);
                //update folder state, delete one link
                model.getFolders().deleteLink(model.getLinks().getFolderId());
                model.getLinks().setState(RequestState.SUCCESSFUL);
                model.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                model.getLinks().setState(RequestState.FAILED);
            }
        });
    }


}
