package com.ensim.mic.slink.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Activities.LinksActivity;
import com.ensim.mic.slink.Listener.FolderMenuListener;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter_folder extends RecyclerView.Adapter<DataAdapter_folder.myViewHolder> {

    private static final String TAG = "DataAdapter_folder";

    Context mContext;
    List<Folder> mData;
    private OnItemClickListener mListener;

    public DataAdapter_folder(Context mContext, List<Folder> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
        final Folder folder = mData.get(i);

        //set default image
        myViewHolder.im_folder.setImageResource(R.drawable.ic_folder);

        try {
            String imgUrl = folder.getPicture();
            Picasso.get().load(Uri.parse(imgUrl)).into(myViewHolder.im_folder);
        } catch (Exception e) {
            Log.e(TAG, "error on loading image <" + folder.getName() + ">");
        }

        myViewHolder.tvTitle.setText(folder.getName());
        myViewHolder.tvOwner.setText(folder.getOwner());
        myViewHolder.tvLike.setText(folder.getLikes() + "");
        myViewHolder.tvLink.setText(folder.getLinks() + "");
        myViewHolder.ivMenu.setOnClickListener(new FolderMenuListener(mContext, myViewHolder.ivMenu, folder));

        // open
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "click on <" + folder.getName() + ">");
                Intent intent = new Intent(view.getContext(), LinksActivity.class);
                intent.putExtra("artist", folder.getId());
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
            im_folder = itemView.findViewById(R.id.im_folder);
            cv_folder = itemView.findViewById(R.id.cv_folder);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOwner = itemView.findViewById(R.id.tvOwner);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvLink = itemView.findViewById(R.id.tvLink);
            ivMenu = itemView.findViewById(R.id.ivMenu);
        }

    }
}
