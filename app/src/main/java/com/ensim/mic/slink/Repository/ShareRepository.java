package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesFolder;
import com.ensim.mic.slink.Retrofit.IApiServicesShare;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareRepository {

    private IApiServicesFolder iApiServicesFolder;
    private IApiServicesShare iApiServicesShare;
    private Model model;


    public ShareRepository() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesShare = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesShare.class);
        model = Model.getInstance();
    }

    //call user folder and update the state
    public void dispalySharePersonnes(int idFolder) {

        model.getFolderMembers().setState(RequestState.LOADING);
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
                    model.getFolderMembers().setState(RequestState.FAILED);
                    return;
                }
                //update state
                model.getFolderMembers().setContent(response.body());
                model.getFolderMembers().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<List<SharePersonne>> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getFolderMembers().setState(RequestState.FAILED);
            }
        });
    }

    public void addPersonne(int idFolder, int userId){
        model.getFolderMembers().setState(RequestState.LOADING);
        System.out.println("add Personne");

        if(Model.getInstance().getCurrentUser().getContent().getId() == userId){
            model.getFolderMembers().setState(RequestState.FAILED);
            return ;
        }


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
                    model.getFolderMembers().setState(RequestState.FAILED);
                    return;
                }
                //update state
                SharePersonne personne = response.body();
                assert personne != null;
                personne.setUserName(personne.getUser().getUserName());
                personne.setGmail(personne.getUser().getGmail());
                List<SharePersonne> listPersonnes = model.getFolderMembers().getContent();
                listPersonnes.add(personne);
                model.getFolderMembers().setContent(listPersonnes);
                model.getFolderMembers().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<SharePersonne> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getFolderMembers().setState(RequestState.FAILED);
            }
        });
    }

    public void deleteShare(final SharePersonne sharePersonne){
        model.getFolderMembers().setState(RequestState.LOADING);
        final int id = Integer.parseInt(sharePersonne.getId());
        Call<Object> call = iApiServicesShare.deleteShare(id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolderMembers().setState(RequestState.FAILED);
                    return;
                }
                List<SharePersonne> content = model.getFolderMembers().getContent();
                content.remove(sharePersonne);
                model.getFolderMembers().setContent(content);
                model.getFolderMembers().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                model.getFolderMembers().setState(RequestState.FAILED);
            }
        });
    }

}
