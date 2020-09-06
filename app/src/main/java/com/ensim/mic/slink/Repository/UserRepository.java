package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesUser;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private IApiServicesUser iApiServicesUser;

    public UserRepository() {
        iApiServicesUser = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesUser.class);

    }

    public void createCurrentUser(String userName, String mail, String token,String picture) {
        System.out.println("createUser ------------------------------------- ");
        Model.getInstance().getCurrentUser().setState(RequestState.LOADING);

        HashMap<String, Object> body = new HashMap<>();
        body.put("userName", userName);
        body.put("Gmail", mail);
        body.put("token", token);
        body.put("picture", picture);

        Call<User> call = iApiServicesUser.createUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    Model.getInstance().getCurrentUser().setState(RequestState.FAILED);

                    return;
                }
                User user = response.body();
                Model.getInstance().getCurrentUser().setContent(user);
                Model.getInstance().getCurrentUser().setState(RequestState.SUCCESSFUL);

                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
                Model.getInstance().getCurrentUser().setState(RequestState.FAILED);
                return;
            }
        });
    }

    /**
     * @param mailOrUserName search user using mail or userName
     */
    public void getSearchUser(String mailOrUserName) {

        System.out.println("-------------- get user ");
        Model.getInstance().getSearchUser().setState(RequestState.LOADING);
        HashMap<String, Object> body = new HashMap<>();
        Call<User> call;

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(mailOrUserName).matches()) {
            call = iApiServicesUser.getUserByGmail(mailOrUserName);
        } else {
            call = iApiServicesUser.getUserByUserName(mailOrUserName);
        }

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    Model.getInstance().getSearchUser().setState(RequestState.FAILED);
                    return;
                }
                System.out.println(response.toString());
                assert response.body() != null;
                User user = response.body();
                Model.getInstance().getSearchUser().setContent(user);
                Model.getInstance().getSearchUser().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("--------- onfailure");
                System.out.println(t.getMessage());
                Model.getInstance().getSearchUser().setState(RequestState.FAILED);
            }
        });

    }

    /**
     * @param mailOrUserName
     * search user using mail or userName
     */
    public void getCurrentUser(String mailOrUserName) {

        Model.getInstance().getCurrentUser().setState(RequestState.LOADING);
        HashMap<String, Object> body = new HashMap<>();
        body.put("Gmail", mailOrUserName);
        Call<User> call = iApiServicesUser.getUserByGmail(mailOrUserName);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    Model.getInstance().getCurrentUser().setState(RequestState.FAILED);
                    return;
                }
                System.out.println("getCurrentUser response :"+response.toString());
                assert response.body() != null;
                User user = response.body();
                Model.getInstance().getCurrentUser().setContent(user);
                Model.getInstance().getCurrentUser().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("--------- onfailure");
                System.out.println(t.getMessage());
                Model.getInstance().getCurrentUser().setState(RequestState.FAILED);
            }
        });

    }

    public void updateUser(int id, User user) {
        System.out.println("updateUser ------------------------------------- ");

        Model.getInstance().getCurrentUser().setState(RequestState.LOADING);

        HashMap<String, Object> body = new HashMap<>();
        if (user.getGmail() != null)
            body.put("Gmail", user.getGmail());
        if (user.getUserName() != null)
            body.put("userName", user.getUserName());
        if (user.getToken() != null)
            body.put("token", user.getToken());


        Call<User> call = iApiServicesUser.updateUser(id, body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    Model.getInstance().getCurrentUser().setState(RequestState.FAILED);
                    return;
                }
                User user = response.body();
                assert user != null;
                user.setGmail("");
                Model.getInstance().getCurrentUser().setContent(user);
                Model.getInstance().getCurrentUser().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
                Model.getInstance().getCurrentUser().setState(RequestState.FAILED);
            }
        });
    }

    public void updateToken(int id, String token) {
        System.out.println("updateUser ------------------------------------- ");

        HashMap<String, Object> body = new HashMap<>();
        if (!token.isEmpty())
            body.put("token", token);

        Call<User> call = iApiServicesUser.updateUser(id, body);
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
                Model.getInstance().getCurrentUser().setContent(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }




    public void deleteUser(int id) {
        System.out.println("updateUser ------------------------------------- ");

        Call<Void> call = iApiServicesUser.deleteUser(id);
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

    public void getUserAllFolders(int id, String search) {
        System.out.println("getUserAllFolders ------------------------------------- ");

        Call<List<FolderWithoutUser>> call = iApiServicesUser.getUserAllFolders(id, search);
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderWithoutUser> folders = response.body();
                for (FolderWithoutUser folder : folders) {
                    System.out.println("folder : " + folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserFolders(int id, String search) {
        System.out.println("getUserFolders ------------------------------------- ");

        Call<List<FolderWithoutUser>> call = iApiServicesUser.getUserFolders(id, search);
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderWithoutUser> folders = response.body();
                for (FolderWithoutUser folder : folders) {
                    System.out.println("folder : " + folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserShare(int id, String search) {
        System.out.println("getUserShare ------------------------------------- ");

        Call<List<FolderWithoutUser>> call = iApiServicesUser.getUserShare(id, search);
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderWithoutUser> folders = response.body();
                for (FolderWithoutUser folder : folders) {
                    System.out.println("folder : " + folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUserSubscribe(int id, String search) {
        System.out.println("getUserSubscribe ------------------------------------- ");

        Call<List<FolderWithoutUser>> call = iApiServicesUser.getUserSubscribe(id, search);
        call.enqueue(new Callback<List<FolderWithoutUser>>() {
            @Override
            public void onResponse(Call<List<FolderWithoutUser>> call, Response<List<FolderWithoutUser>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                List<FolderWithoutUser> folders = response.body();
                for (FolderWithoutUser folder : folders) {
                    System.out.println("folder : " + folder.toString());
                }


            }

            @Override
            public void onFailure(Call<List<FolderWithoutUser>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


}
