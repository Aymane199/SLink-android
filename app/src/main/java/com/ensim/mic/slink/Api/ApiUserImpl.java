package com.ensim.mic.slink.Api;

import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.UserFolder;

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
public class ApiUserImpl {
    UserApiServices userApiServices;

    public ApiUserImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://212.227.200.43/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApiServices = retrofit.create(UserApiServices.class);
    }

    public void getUsers() {
        System.out.println("getUsers ------------------------------------- ");

        Call<List<User>> call = userApiServices.getUsers();
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

        Call<User> call = userApiServices.createUser(user);
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

        Call<User> call = userApiServices.getUser(id);
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

        Call<User> call = userApiServices.updateUser(id, user);
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

        Call<Void> call = userApiServices.deleteUser(id);
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

        Call<List<UserFolder>> call = userApiServices.getUserAllFolders(id, search);
        call.enqueue(new Callback<List<UserFolder>>() {
            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<UserFolder> folders = response.body();
                for ( UserFolder folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserFolders(int id , String search) {
        System.out.println("getUserFolders ------------------------------------- ");

        Call<List<UserFolder>> call = userApiServices.getUserFolders(id, search);
        call.enqueue(new Callback<List<UserFolder>>() {
            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<UserFolder> folders = response.body();
                for ( UserFolder folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserShare(int id , String search) {
        System.out.println("getUserShare ------------------------------------- ");

        Call<List<UserFolder>> call = userApiServices.getUserShare(id, search);
        call.enqueue(new Callback<List<UserFolder>>() {
            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<UserFolder> folders = response.body();
                for ( UserFolder folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserSubscribe(int id , String search) {
        System.out.println("getUserSubscribe ------------------------------------- ");

        Call<List<UserFolder>> call = userApiServices.getUserSubscribe(id, search);
        call.enqueue(new Callback<List<UserFolder>>() {
            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<UserFolder> folders = response.body();
                for ( UserFolder folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserSave(int id , String search) {
        System.out.println("getUserSave ------------------------------------- ");

        Call<List<UserFolder>> call = userApiServices.getUserSave(id, search);
        call.enqueue(new Callback<List<UserFolder>>() {
            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<UserFolder> folders = response.body();
                for ( UserFolder folder: folders ) {
                    System.out.println("folder : "+folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
