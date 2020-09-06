package com.ensim.mic.slink.Repository;

import com.ensim.mic.slink.Retrofit.IApiServicesComment;
import com.ensim.mic.slink.Retrofit.IApiServicesLink;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepository {

    private IApiServicesComment iApiServicesComment;
    private IApiServicesLink iApiServicesLink;
    private Model model;


    public CommentRepository() {
        iApiServicesComment = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesComment.class);
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        model = Model.getInstance();
    }

    //call links comments and update the state
    public void displayComments(final int linkId) {

        model.getComments().setState(RequestState.LOADING);
        System.out.println("display Comments : "+linkId);
        // etablish the request
        //Call<List<Comment>> call;
        Call<Link> call;
        //call = iApiServicesComment.showComments();
        call = iApiServicesLink.showLink(linkId);

        //fill the folder list
        call.enqueue(new Callback<Link>() {
            @Override
            public void onResponse(Call<Link> call, Response<Link> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getComments().setContent(new ArrayList<Comment>());
                    model.getComments().setState(RequestState.FAILED);
                    return;
                }
                if(response.body() == null) return;
                System.out.println(response.body().toString());

                //update state
                model.getComments().setContent(response.body().getComments());
                model.getComments().setLinkId(linkId);
                model.getComments().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Link> call, Throwable t) {
                System.out.println(t.getMessage());
                model.getComments().setContent(new ArrayList<Comment>());
                model.getComments().setState(RequestState.FAILED);
            }
        });
    }

    //create folder and update state
    public void createComment(int linkId,int userId,String text) {
        System.out.println("create comment ------------------------------------- ");

        model.getComments().setState(RequestState.LOADING);
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
                    model.getComments().setState(RequestState.FAILED);
                    return ;

                }
                Comment comment = response.body();

                //update state
                List<Comment> commentListTemp = model.getComments().getContent();
                commentListTemp.add(comment);
                model.getComments().setContent(commentListTemp);
                model.getComments().setState(RequestState.SUCCESSFUL);

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                model.getComments().setState(RequestState.FAILED);
            }
        });
    }

    public void deleteComment(final Comment comment){
        model.getComments().setState(RequestState.LOADING);
        final int commmentId = comment.getId();
        Call<Void> call = iApiServicesComment.deleteComment(commmentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    model.getComments().setState(RequestState.FAILED);
                    return;
                }
                List<Comment> comments = model.getComments().getContent();
                comments.remove(comment);
                model.getComments().setContent(comments);
                model.getComments().setState(RequestState.SUCCESSFUL);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                model.getComments().setState(RequestState.FAILED);
            }
        });
    }

}
