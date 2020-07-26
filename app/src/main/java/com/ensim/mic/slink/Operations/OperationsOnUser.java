package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesUser;
import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.FolderOfUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
test class
designed to group methods
it won't be user anywhere
we ll deleted it at the end
 */
public class OperationsOnUser {
    com.ensim.mic.slink.Api.IApiServicesUser IApiServicesUser;

    public OperationsOnUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://212.227.200.43/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApiServicesUser = retrofit.create(IApiServicesUser.class);
    }

    public void getUsers() {
        System.out.println("getUsers ------------------------------------- ");

        Call<List<User>> call = IApiServicesUser.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return ;
                }
                List<User> users = response.body();
                for (User user : users) {
                   System.out.println(user.toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });

    }

    public void createUser(User user) {
        System.out.println("createUser ------------------------------------- ");

        Call<User> call = IApiServicesUser.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return ;
                }
                User user = response.body();

                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    public void getUser(int id) {
        System.out.println("getUser ------------------------------------- ");

        Call<User> call = IApiServicesUser.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                User user = response.body();
                System.out.println(user.toString());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void updateUser(int id, User user) {
        System.out.println("updateUser ------------------------------------- ");

        Call<User> call = IApiServicesUser.updateUser(id, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                User user = response.body();
                System.out.println(user.toString());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void deleteUser(int id) {
        System.out.println("updateUser ------------------------------------- ");

        Call<Void> call = IApiServicesUser.deleteUser(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                System.out.println("delete successful");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserAllFolders(int id , String search) {
        System.out.println("getUserAllFolders ------------------------------------- ");

        Call<List<FolderOfUser>> call = IApiServicesUser.getUserAllFolders(id, search);
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderOfUser> folders = response.body();
                for ( FolderOfUser folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserFolders(int id , String search) {
        System.out.println("getUserFolders ------------------------------------- ");

        Call<List<FolderOfUser>> call = IApiServicesUser.getUserFolders(id, search);
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderOfUser> folders = response.body();
                for ( FolderOfUser folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserShare(int id , String search) {
        System.out.println("getUserShare ------------------------------------- ");

        Call<List<FolderOfUser>> call = IApiServicesUser.getUserShare(id, search);
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderOfUser> folders = response.body();
                for ( FolderOfUser folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserSubscribe(int id , String search) {
        System.out.println("getUserSubscribe ------------------------------------- ");

        Call<List<FolderOfUser>> call = IApiServicesUser.getUserSubscribe(id, search);
        call.enqueue(new Callback<List<FolderOfUser>>() {
            @Override
            public void onResponse(Call<List<FolderOfUser>> call, Response<List<FolderOfUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderOfUser> folders = response.body();
                for ( FolderOfUser folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderOfUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


}
