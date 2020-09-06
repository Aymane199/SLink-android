package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesFolder;
import com.ensim.mic.slink.Retrofit.IApiServicesShare;
import com.ensim.mic.slink.Retrofit.IApiServicesUser;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.utils.FolderFilter;
import com.ensim.mic.slink.utils.RequestState;
import com.ensim.mic.slink.utils.SortStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FolderRepository {

    //user state
    private int userId;
    private String userName;

    private IApiServicesShare iApiServicesShare;
    private IApiServicesUser iApiServicesUser;
    private IApiServicesFolder iApiServicesFolder;
    private Model model;


    public FolderRepository() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        iApiServicesShare = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesShare.class);
        model = Model.getInstance();
        userName = Model.getInstance().getCurrentUser().getContent().getUserName();
        userId = Model.getInstance().getCurrentUser().getContent().getId();
    }

    //call user folder and update the state
    public void displayFolders(FolderFilter filter, String searchText) {

        model.getFolders().setState(RequestState.LOADING);

        System.out.println("display folder");
        // etablish the request
        Call<List<FolderWithoutUser>> call;
        //set filter
        switch (filter){
            case FILTER_OWNED_BY_ME :
                call = iApiServicesUser.getUserFolders(userId, searchText);
                break;
            case FILTER_SHARED_WITH_ME :
                call = iApiServicesUser.getUserShare(userId, searchText);
                break;
            case FILTER_SUBSCRIPTION :
                call = iApiServicesUser.getUserSubscribe(userId, searchText);
                break;
            default:
                call = iApiServicesUser.getUserAllFolders(userId, searchText);

        }
        //fill the folder list
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                model.getFolders().setContent(response.body());
                model.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void displayEditableFolders(String searchText) {
        model.getFolders().setState(RequestState.LOADING);
        model.getFolders().setContent(new ArrayList<FolderWithoutUser>());
        System.out.println("display folder");
        // etablish the request
        Call<List<FolderWithoutUser>> call = iApiServicesUser.getUserFolders(userId, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderWithoutUser> foldersOfUserTemp = model.getFolders().getContent();
                assert response.body() != null;
                foldersOfUserTemp.addAll(response.body());
                model.getFolders().setContent(foldersOfUserTemp);

                model.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());

                model.getFolders().setState(RequestState.FAILED);
            }
        });

        Call<List<FolderWithoutUser>> call2= iApiServicesUser.getUserShare(userId, searchText);
        call2.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderWithoutUser> foldersOfUserTemp = model.getFolders().getContent();
                assert response.body() != null;
                foldersOfUserTemp.addAll(response.body());
                model.getFolders().setContent(foldersOfUserTemp);

                model.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                model.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    //create folder and update state
    public void createFolder(String name, final int owner) {
        System.out.println("create folder ------------------------------------- ");

        model.getFolders().setState(RequestState.LOADING);
        //create body
        HashMap<String, Object> body = new HashMap<>();
        body.put("name",name);
        body.put("owner",owner);

        //call request
        Call<Folder> call = iApiServicesFolder.createFolder(body);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return ;

                }
                Folder folder = response.body();

                //convert folder to userFolder
                FolderWithoutUser folderOfUser = new FolderWithoutUser();
                folderOfUser.setOwner(userName);
                folderOfUser.setName(folder.getName());
                folderOfUser.setId(folder.getId()+"");
                folderOfUser.setOwnerId(owner+"");

                //update state
                List<FolderWithoutUser> foldersOfUserTemp = model.getFolders().getContent();
                foldersOfUserTemp.add(0,folderOfUser);
                model.getFolders().setContent(foldersOfUserTemp);
                model.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                model.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void deleteFolder(final FolderWithoutUser folderOutput){
        model.getFolders().setState(RequestState.LOADING);
        final int folderid = Integer.parseInt(folderOutput.getId());
        Call<Folder> call = iApiServicesFolder.deleteFolder(folderid);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderWithoutUser> folders = model.getFolders().getContent();
                folders.remove(folderOutput);
                model.getFolders().setContent(folders);
                model.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                model.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void QuitFolder(final FolderWithoutUser folderOutput){
        model.getFolders().setState(RequestState.LOADING);
        final int folderid = Integer.parseInt(folderOutput.getId());
        HashMap<String,Object> body=new HashMap<>();
        body.put("user",userId);
        body.put("folder",folderid);
        Call<Void> call = iApiServicesShare.deleteShare(userId,folderid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderWithoutUser> folders = model.getFolders().getContent();
                folders.remove(folderOutput);
                model.getFolders().setContent(folders);
                model.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                model.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void updateFolder(final FolderWithoutUser folder){
        model.getFolders().setState(RequestState.LOADING);

        System.out.println("update folder :"+folder);

        HashMap<String, Object> body = new HashMap<>();
        body.put("name",folder.getName());
        body.put("description",folder.getDescription());
        body.put("picture",folder.getPicture());
        body.put("public",folder.getPublic());


        Call<Folder> call = iApiServicesFolder.updateFolder(Integer.parseInt(folder.getId()),body);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderWithoutUser> folders = model.getFolders().getContent();
                System.out.println("folders : "+folders);
                int indexFolder = model.getFolders().findIndexFolderById(Integer.parseInt(folder.getId()));
                if(indexFolder ==-1) {
                    System.out.println("folder id = -1");
                    model.getFolders().setState(RequestState.FAILED);
                    return;
                }
                folders.set(indexFolder,folder);
                model.getFolders().setContent(folders);
                model.getFolders().setState(RequestState.SUCCESSFUL);
                System.out.println("update successful");
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                model.getFolders().setState(RequestState.FAILED);
                System.out.println("update failed");

            }
        });
    }

    //TODO FIX SORT
    /*
    * get the folder already sorted from the api
    * u should create a sort method that sorts list folder in state
    * add new field in foldersState that remembers the state of the sort checked button
    * call sortFolder method every time we want to display folders
     */
    public void sortFolders(SortStatus sortStatus){
        List<FolderWithoutUser> folders = Model.getInstance().getFolders().getContent();
        if(folders.isEmpty()) return;



    }

    public void getFolder(int id){
        model.getFolder().setState(RequestState.LOADING);

        System.out.println("get folder :"+id);


        Call<Folder> call = iApiServicesFolder.getFolder(id);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getFolder().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("folder : "+response.body());

                model.getFolder().setState(RequestState.SUCCESSFUL);
                System.out.println("get folder successful");
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                model.getFolder().setState(RequestState.FAILED);
                System.out.println("get folder failed");

            }
        });
    }
}
