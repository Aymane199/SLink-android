package com.ensim.mic.slink.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Retrofit.IApiServicesLink;
import com.ensim.mic.slink.Retrofit.RetrofitFactory;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.Link;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAdapterChooseFolder extends RecyclerView.Adapter<DataAdapterChooseFolder.myViewHolder> {

    private static final String TAG = "DataAdapter_chooseFolder";

    Context mContext;
    List<FolderWithoutUser> mData;
    Link linkToput;
    IApiServicesLink iApiServicesLink;
    private OnItemClickListener mListener;


    public DataAdapterChooseFolder(Context mContext, List<FolderWithoutUser> mData, Link linkToPut) {
        this.mContext = mContext;
        iApiServicesLink = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesLink.class);
        this.mData = mData;
        this.linkToput = linkToPut;
        Uri uri = Uri.parse(linkToPut.getUrl());
        if(linkToPut.getName() != null)
        if(linkToPut.getName().isEmpty())
        linkToPut.setName(uri.getHost()+" : "+uri.getLastPathSegment());
        System.out.println("----->"+linkToPut);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.template_choosefolder, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final FolderWithoutUser folderOutput = mData.get(i);
        final Link link = linkToput;
        //set default image
        myViewHolder.im_folder.setImageResource(R.drawable.ic_folder);

        try {
            String imgUrl = folderOutput.getPicture();
            Picasso.get().load(Uri.parse(imgUrl)).placeholder(R.drawable.ic_folder).error(R.drawable.ic_folder).into(myViewHolder.im_folder);
        } catch (Exception e) {
            Log.e(TAG, "error on loading image <" + folderOutput.getName() + ">");

        }

        myViewHolder.tvTitle.setText(folderOutput.getName());
        myViewHolder.tvOwner.setText(folderOutput.getOwner());

        // add link to folder
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(link);
                //link
                addLinkToFolder(folderOutput, link, Model.getInstance().getCurrentUser().getContent().getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void addLinkToFolder(final FolderWithoutUser folderOutput, Link link, int idUser) {

        HashMap<String, Object> body = new HashMap<>();
        if (link.getUrl() != null)
            body.put("URL", link.getUrl());
        else
            return;

        if (link.getPicture() != null)
            body.put("picture", link.getPicture());

        if (link.getName() != null)
            body.put("name", link.getName());

        body.put("folder", folderOutput.getId());
        body.put("owner", idUser);

        System.out.println(body.toString());
        Call<Object> call = iApiServicesLink.createLink(body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                Object links = response.body();
                System.out.println(links.toString());
                Toast.makeText(mContext, "The link is added successfully to " + folderOutput.getName(), Toast.LENGTH_LONG).show();
                ((Activity) mContext).finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });

    }

    public interface OnItemClickListener {
        void onDescClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView im_folder;
        CardView cv_folder;
        TextView tvTitle, tvOwner;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im_folder = itemView.findViewById(R.id.ivImage);
            cv_folder = itemView.findViewById(R.id.cvImage);
            tvTitle = itemView.findViewById(R.id.tvUserName);
            tvOwner = itemView.findViewById(R.id.tvMail);
        }

    }


}
