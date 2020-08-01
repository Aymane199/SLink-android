package com.ensim.mic.slink.Adapter;

import android.content.Context;
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

import com.ensim.mic.slink.Listener.LinkMenuListener;
import com.ensim.mic.slink.Listener.ShareMenuListener;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.Table.SharePersonne;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class DataAdapterSharePersonnes extends RecyclerView.Adapter<DataAdapterSharePersonnes.myViewHolder> {

    private static final String TAG = "DataAdapter_sharePersonnes";

    Context mContext;
    List<SharePersonne> mData;

    private OnItemClickListener mListener;


    public DataAdapterSharePersonnes(Context mContext, List<SharePersonne> mData) {
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
        View v = inflater.inflate(R.layout.template_share_personnes, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final SharePersonne personne = mData.get(i);

        myViewHolder.tvUserName.setText(personne.getUserName());
        myViewHolder.tvMail.setText(personne.getGmail());
        myViewHolder.tvRight.setText(personne.getAccessRight());
        myViewHolder.ivMenu.setOnClickListener(new ShareMenuListener(mContext,myViewHolder.ivMenu,personne));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface OnItemClickListener {
        void onDescClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName,tvMail,tvRight;
        ImageView ivMenu;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvMail = itemView.findViewById(R.id.tvMail);
            tvRight = itemView.findViewById(R.id.tvRight);
            ivMenu = itemView.findViewById(R.id.ivMenu);
        }

    }


}
