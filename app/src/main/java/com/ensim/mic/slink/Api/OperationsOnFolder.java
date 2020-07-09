package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.State.StateListFolders;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.FolderFilter;
import com.ensim.mic.slink.utils.RequestState;

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

        state.setUserFoldersState(RequestState.LOADING);
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
                    state.setUserFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                state.setUserFolders(new StateListFolders(response.body(), RequestState.SUCCESSFUL));

            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
                state.setUserFoldersState(RequestState.FAILED);
            }
        });
    }

    public void displayEditableFolders(String searchText) {
        state.setUserFoldersState(RequestState.LOADING);
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
                    state.setUserFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp =  state.getUserFolders().getListFolder();
                foldersOfUserTemp.addAll(response.body());
                state.setUserFoldersList(foldersOfUserTemp);

                state.setUserFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());

                state.setUserFoldersState(RequestState.FAILED);
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
                    state.setUserFoldersState(RequestState.FAILED);
                    return;
                }
                //update state
                List<FolderOfUser> foldersOfUserTemp =  state.getUserFolders().getListFolder();
                foldersOfUserTemp.addAll(response.body());
                state.setUserFoldersList(foldersOfUserTemp);

                state.setUserFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                state.setUserFoldersState(RequestState.FAILED);            }
        });
    }

    //create folder and update state
    public void createFolder(String name,int owner) {
        System.out.println("create folder ------------------------------------- ");

        state.setUserFoldersState(RequestState.LOADING);
        // TODO U CAN CHANGE THE STATE FROM JUST A LIST OF FOLDER TO FOLDER AND RESPONSE MESSAGE. UR LISTNE ON BOTH IF GOOD DISPLAY LIST ELSE DESPLAY MESSAGE
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
                    state.setUserFoldersState(RequestState.FAILED);
                    return ;

                }
                Folder folder = response.body();

                //convert folder to userFolder
                FolderOfUser folderOfUser = new FolderOfUser();
                folderOfUser.setOwner(userName);
                folderOfUser.setName(folder.getName());
                folderOfUser.setId(folder.getId()+"");

                //update state
                List<FolderOfUser> foldersOfUserTemp = state.getUserFolders().getListFolder();
                foldersOfUserTemp.add(folderOfUser);
                state.setUserFoldersList(foldersOfUserTemp);
                state.setUserFoldersState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.setUserFoldersState(RequestState.FAILED);
                return;
            }
        });
    }

    public void deleteFolder(final FolderOfUser folderOutput){
        state.setUserFoldersState(RequestState.LOADING);
        final int folderid = Integer.parseInt(folderOutput.getId());
        Call<Folder> call = iApiServicesFolder.deleteFolder(folderid);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setUserFoldersState(RequestState.FAILED);
                    return;
                }
                List<FolderOfUser> folders = state.getUserFolders().getListFolder();
                folders.remove(folderOutput);
                state.setUserFoldersList(folders);
                state.setUserFoldersState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                state.setUserFoldersState(RequestState.FAILED);
            }
        });
    }

}
