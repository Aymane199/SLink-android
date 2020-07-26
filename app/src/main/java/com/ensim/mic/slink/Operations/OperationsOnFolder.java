package com.ensim.mic.slink.Operations;

import android.widget.Toast;

import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.State.StateListFolders;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.FolderFilter;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnFolder {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesUser iApiServicesUser;
    IApiServicesFolder iApiServicesFolder;
    State state;


    public OperationsOnFolder() {
        iApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);
        state = State.getInstance();
    }

    //call user folder and update the state
    public void displayFolders(FolderFilter filter, String searchText) {

        state.setFoldersState(RequestState.LOADING);
        System.out.println("display folder");
        // etablish the request
        Call<List<FolderOfUser>> call;
        //set filter
        switch (filter){
            case FILTER_ANYONE:
                call = iApiServicesUser.getUserAllFolders(userId, searchText);
                break;
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
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                state.setFolders(new StateListFolders(response.body(), RequestState.SUCCESSFUL));

            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.setFoldersState(RequestState.FAILED);
            }
        });
    }

    public void displayEditableFolders(String searchText) {
        state.setFoldersState(RequestState.LOADING);
        state.setFoldersList(new ArrayList<FolderOfUser>());
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
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp =  state.getFolders().getListFolder();
                foldersOfUserTemp.addAll(response.body());
                state.setFoldersList(foldersOfUserTemp);

                state.setFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());

                state.setFoldersState(RequestState.FAILED);
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
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp =  state.getFolders().getListFolder();
                foldersOfUserTemp.addAll(response.body());
                state.setFoldersList(foldersOfUserTemp);

                state.setFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                state.setFoldersState(RequestState.FAILED);            }
        });
    }

    //create folder and update state
    public void createFolder(String name,int owner) {
        System.out.println("create folder ------------------------------------- ");

        state.setFoldersState(RequestState.LOADING);
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
                    state.setFoldersState(RequestState.FAILED);
                    return ;

                }
                Folder folder = response.body();

                //convert folder to userFolder
                FolderOfUser folderOfUser = new FolderOfUser();
                folderOfUser.setOwner(userName);
                folderOfUser.setName(folder.getName());
                folderOfUser.setId(folder.getId()+"");

                //update state
                List<FolderOfUser> foldersOfUserTemp = state.getFolders().getListFolder();
                foldersOfUserTemp.add(folderOfUser);
                state.setFoldersList(foldersOfUserTemp);
                state.setFoldersState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.setFoldersState(RequestState.FAILED);
                return;
            }
        });
    }

    public void deleteFolder(final FolderOfUser folderOutput){
        state.setFoldersState(RequestState.LOADING);
        final int folderid = Integer.parseInt(folderOutput.getId());
        Call<Folder> call = iApiServicesFolder.deleteFolder(folderid);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getFolders().getListFolder();
                folders.remove(folderOutput);
                state.setFoldersList(folders);
                state.setFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.setFoldersState(RequestState.FAILED);
            }
        });
    }

    public void updateFolder(final FolderOfUser folder){
        state.setFoldersState(RequestState.LOADING);

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
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getFolders().getListFolder();
                System.out.println("folders : "+folders);
                int indexFolder = state.getFolders().findIndexFolderById(Integer.parseInt(folder.getId()));
                if(indexFolder ==-1) {
                    System.out.println("folder id = -1");
                    state.setFoldersState(RequestState.FAILED);
                    return;
                }
                folders.set(indexFolder,folder);
                state.setFoldersList(folders);
                state.setFoldersState(RequestState.SUCCESSFUL);
                System.out.println("update successful");
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.setFoldersState(RequestState.FAILED);
                System.out.println("update failed");

            }
        });
    }
}
