package com.ensim.mic.slink.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Activities.LinksActivity;
import com.ensim.mic.slink.Listener.FolderMenuOtherUserListener;
import com.ensim.mic.slink.Listener.FolderMenuOwnerListener;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterFolder extends RecyclerView.Adapter<DataAdapterFolder.myViewHolder> {

    private static final String TAG = "DataAdapter_folder";

    Context mContext;
    public List<FolderOfUser> mData;
    private OnItemClickListener mListener;
    private int userId;

    public DataAdapterFolder(Context mContext, List<FolderOfUser> mData, int userId) {
        this.mContext = mContext;
        this.mData = mData;
        this.userId = userId;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.template_folder, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final FolderOfUser folderOutput = mData.get(i);

        System.out.println(folderOutput);
        //set default image
        myViewHolder.im_folder.setImageResource(R.drawable.ic_folder);

        try {
            String imgUrl = folderOutput.getPicture();
            if(folderOutput.getType().equals("share")){
                Picasso.get().load(Uri.parse(imgUrl)).placeholder(R.drawable.ic_folder_publiv).error(R.drawable.ic_folder_publiv).into(myViewHolder.im_folder);
                myViewHolder.ivMenu.setOnClickListener(new FolderMenuOtherUserListener(mContext, myViewHolder.ivMenu, folderOutput));

            }else{
                myViewHolder.ivMenu.setOnClickListener(new FolderMenuOwnerListener(mContext, myViewHolder.ivMenu, folderOutput));
                Picasso.get().load(Uri.parse(imgUrl)).placeholder(R.drawable.ic_folder).error(R.drawable.ic_folder).into(myViewHolder.im_folder);

            }
        } catch (Exception e) {
            Log.e(TAG, "error on loading image <" + folderOutput.getName() + ">");

        }

        myViewHolder.tvTitle.setText(folderOutput.getName());
        myViewHolder.tvOwner.setText(folderOutput.getOwner());




        if (folderOutput.getLikes()==null)
            myViewHolder.tvLike.setText("0");
        else
            myViewHolder.tvLike.setText(folderOutput.getLikes());

        if (folderOutput.getLinks()==null)
            myViewHolder.tvLink.setText("0");
        else
            myViewHolder.tvLink.setText(folderOutput.getLinks());


        // open
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "click on <" + folderOutput.getName() + ">");
                Intent intent = new Intent(view.getContext(), LinksActivity.class);
                intent.putExtra("idFolder", folderOutput.getId());
                intent.putExtra("nameFolder", folderOutput.getName());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onDescClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView im_folder, ivMenu;
        CardView cv_folder;
        TextView tvTitle, tvOwner, tvLike, tvLink;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im_folder = itemView.findViewById(R.id.ivImage);
            cv_folder = itemView.findViewById(R.id.cvImage);
            tvTitle = itemView.findViewById(R.id.tvUserName);
            tvOwner = itemView.findViewById(R.id.tvMail);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvLink = itemView.findViewById(R.id.tvLink);
            ivMenu = itemView.findViewById(R.id.ivMenu);
        }

    }
}
