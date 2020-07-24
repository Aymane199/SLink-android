package com.ensim.mic.slink.Operations;

import com.ensim.mic.slink.Api.IApiServicesComment;
import com.ensim.mic.slink.Api.IApiServicesLink;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.State.StateListComments;
import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsOnComment {

    //user state
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    IApiServicesComment iApiServicesComment;
    IApiServicesLink iApiServicesLink;
    State state;


    public OperationsOnComment() {
        iApiServicesComment = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesComment.class);
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        state = State.getInstance();
    }

    //call links comments and update the state
    public void displayComments(int linkId) {

        state.setCommentsState(RequestState.LOADING);
        System.out.println("display Comments :"+linkId);
        // etablish the request
        //Call<List<Comment>> call;
        Call<LinkOfFolder> call;
        //call = iApiServicesComment.showComments();
        call = iApiServicesLink.showLink(linkId);

        //fill the folder list
        call.enqueue(new Callback<LinkOfFolder>() {
            @Override
            public void onResponse(Call<LinkOfFolder> call, Response<LinkOfFolder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setCommentsState(RequestState.FAILED);
                    return;
                }
                System.out.println(response.body().toString());
                //update state
                state.setComments(new StateListComments(response.body().getComments(), RequestState.SUCCESSFUL));
                state.setCommentsState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<LinkOfFolder> call, Throwable t) {
                System.out.println(t.getMessage());
                state.setCommentsState(RequestState.FAILED);
            }
        });
    }

    //create folder and update state
    public void createComment(int linkId,int userId,String text) {
        System.out.println("create comment ------------------------------------- ");

        state.setCommentsState(RequestState.LOADING);
        //create body
        HashMap<String, Object> body = new HashMap<>();
        body.put("link",linkId);
        body.put("user",userId);
        body.put("text",text);

        //call request
        Call<Comment> call = iApiServicesComment.createComment(body);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setCommentsState(RequestState.FAILED);
                    return ;

                }
                Comment comment = response.body();

                //update state
                List<Comment> commentListTemp = state.getComments().getListComments();
                commentListTemp.add(comment);
                state.setCommentsList(commentListTemp);
                state.setCommentsState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                state.setCommentsState(RequestState.FAILED);
                return;
            }
        });
    }

    public void deleteComment(final Comment comment){
        state.setCommentsState(RequestState.LOADING);
        final int commmentId = comment.getId();
        Call<Void> call = iApiServicesComment.deleteComment(commmentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    state.setCommentsState(RequestState.FAILED);
                    return;
                }
                List<Comment> comments = state.getComments().getListComments();
                comments.remove(comment);
                state.setCommentsList(comments);
                state.setCommentsState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                state.setCommentsState(RequestState.FAILED);
            }
        });
    }

}