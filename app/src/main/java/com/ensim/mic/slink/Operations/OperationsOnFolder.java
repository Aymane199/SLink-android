package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesShare;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.FolderFilter;
import com.ensim.mic.slink.utils.RequestState;
import com.ensim.mic.slink.utils.SortStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnFolder {

    //user state
    private int userId;
    private String userName;

    private IApiServicesShare iApiServicesShare;
    private IApiServicesUser iApiServicesUser;
    private IApiServicesFolder iApiServicesFolder;
    private State state;


    public OperationsOnFolder() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        iApiServicesShare = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesShare.class);
        state = State.getInstance();
        userName = State.getInstance().getCurrentUser().getContent().getUserName();
        userId = State.getInstance().getCurrentUser().getContent().getId();
    }

    //call user folder and update the state
    public void displayFolders(FolderFilter filter, String searchText) {

        state.getFolders().setState(RequestState.LOADING);

        System.out.println("display folder");
        // etablish the request
        Call<List<FolderOfUser>> call;
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
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                state.getFolders().setContent(response.body());
                state.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void displayEditableFolders(String searchText) {
        state.getFolders().setState(RequestState.LOADING);
        state.getFolders().setContent(new ArrayList<FolderOfUser>());
        System.out.println("display folder");
        // etablish the request
        Call<List<FolderOfUser>> call = iApiServicesUser.getUserFolders(userId, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp = state.getFolders().getContent();
                assert response.body() != null;
                foldersOfUserTemp.addAll(response.body());
                state.getFolders().setContent(foldersOfUserTemp);

                state.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());

                state.getFolders().setState(RequestState.FAILED);
            }
        });

        Call<List<FolderOfUser>> call2= iApiServicesUser.getUserShare(userId, searchText);
        call2.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp = state.getFolders().getContent();
                assert response.body() != null;
                foldersOfUserTemp.addAll(response.body());
                state.getFolders().setContent(foldersOfUserTemp);

                state.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                state.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    //create folder and update state
    public void createFolder(String name, final int owner) {
        System.out.println("create folder ------------------------------------- ");

        state.getFolders().setState(RequestState.LOADING);
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
                    state.getFolders().setState(RequestState.FAILED);
                    return ;

                }
                Folder folder = response.body();

                //convert folder to userFolder
                FolderOfUser folderOfUser = new FolderOfUser();
                folderOfUser.setOwner(userName);
                folderOfUser.setName(folder.getName());
                folderOfUser.setId(folder.getId()+"");
                folderOfUser.setOwnerId(owner+"");

                //update state
                List<FolderOfUser> foldersOfUserTemp = state.getFolders().getContent();
                foldersOfUserTemp.add(0,folderOfUser);
                state.getFolders().setContent(foldersOfUserTemp);
                state.getFolders().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void deleteFolder(final FolderOfUser folderOutput){
        state.getFolders().setState(RequestState.LOADING);
        final int folderid = Integer.parseInt(folderOutput.getId());
        Call<Folder> call = iApiServicesFolder.deleteFolder(folderid);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getFolders().getContent();
                folders.remove(folderOutput);
                state.getFolders().setContent(folders);
                state.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void QuitFolder(final FolderOfUser folderOutput){
        state.getFolders().setState(RequestState.LOADING);
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
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getFolders().getContent();
                folders.remove(folderOutput);
                state.getFolders().setContent(folders);
                state.getFolders().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                state.getFolders().setState(RequestState.FAILED);
            }
        });
    }

    public void updateFolder(final FolderOfUser folder){
        state.getFolders().setState(RequestState.LOADING);

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
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getFolders().getContent();
                System.out.println("folders : "+folders);
                int indexFolder = state.getFolders().findIndexFolderById(Integer.parseInt(folder.getId()));
                if(indexFolder ==-1) {
                    System.out.println("folder id = -1");
                    state.getFolders().setState(RequestState.FAILED);
                    return;
                }
                folders.set(indexFolder,folder);
                state.getFolders().setContent(folders);
                state.getFolders().setState(RequestState.SUCCESSFUL);
                System.out.println("update successful");
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.getFolders().setState(RequestState.FAILED);
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
        List<FolderOfUser> folders = State.getInstance().getFolders().getContent();
        if(folders.isEmpty()) return;



    }

    public void getFolder(int id){
        state.getFolder().setState(RequestState.LOADING);

        System.out.println("get folder :"+id);


        Call<Folder> call = iApiServicesFolder.getFolder(id);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.getFolder().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("folder : "+response.body());

                state.getFolder().setState(RequestState.SUCCESSFUL);
                System.out.println("get folder successful");
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.getFolder().setState(RequestState.FAILED);
                System.out.println("get folder failed");

            }
        });
    }
}
