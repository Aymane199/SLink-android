package com.ensim.mic.slink.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Activities.BrowserActivity;
import com.ensim.mic.slink.Component.BottomSheetComment;
import com.ensim.mic.slink.Listener.LinkMenuListener;
import com.ensim.mic.slink.Repository.LikeRepository;
import com.ensim.mic.slink.Repository.SaveRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.Link;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterLink extends RecyclerView.Adapter<DataAdapterLink.myViewHolder> {

    private static final String TAG = "DataAdapter_link";

    Context mContext;
    public List<Link> mData;
    private OnItemClickListener mListener;



    public DataAdapterLink(Context mContext, List<Link> mData) {
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
        View v = inflater.inflate(R.layout.template_link, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final Link link = mData.get(i);

        //set default image
        try {
            String imgUrl = link.getPicture();
            Picasso.get().load(Uri.parse(imgUrl)).placeholder(R.drawable.ic_no_img).into(myViewHolder.ivPicture);
        } catch (Exception e) {
            Log.e(TAG, "error on loading image <" + link.getName() + ">");
        }

        myViewHolder.tvTitle.setText(link.getName());
        myViewHolder.tvDescription.setText(link.getDescription());
        if (link.getLike() != null)
            myViewHolder.ivLike.setChecked(true);
        if (link.getSave() != null)
            myViewHolder.ivSave.setChecked(true);


        myViewHolder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("myViewHolder.ivLike.isChecked() : " + myViewHolder.ivLike.isChecked() );
                if(myViewHolder.ivLike.isChecked()) new LikeRepository().setLike(Integer.parseInt(link.getId()));
                else new LikeRepository().deleteLike(Model.getInstance().getCurrentUser().getContent().getId(),Integer.parseInt(link.getId()));
            }
        });
        myViewHolder.ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("myViewHolder.ivSave.isChecked() : " + myViewHolder.ivSave.isChecked() );
                    if(myViewHolder.ivSave.isChecked()) new SaveRepository().setSave(link);
                    else new SaveRepository().deleteSave(link);
                }
            });
        final BottomSheetComment bottomSheetComment = new BottomSheetComment();


        myViewHolder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Comment Clicked");
                bottomSheetComment.setIdLink(Integer.parseInt(link.getId()));
                bottomSheetComment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "bottomSheetComment");

            }
        });

        myViewHolder.ivMenu.setOnClickListener(new LinkMenuListener(mContext,myViewHolder.ivMenu,link));

        // open link in browser
        myViewHolder.ivGoToBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "click on <" + link.getName() + ">");
                try {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUrl()));
                    view.getContext().startActivity(browserIntent);
                    Log.d(TAG, "opened on <" + link.getName() + ">");

                } catch (Exception e) {
                    Log.e(TAG, "error on <" + link.getName() + "> link invalid ");
                }
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra("url",link.getUrl());
                mContext.startActivity(intent);
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

        ImageView ivPicture, ivMenu, ivComment,ivGoToBrowser;
        CheckBox ivLike, ivSave;
        TextView tvTitle, tvDescription;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivSave = itemView.findViewById(R.id.ivSave);
            ivGoToBrowser = itemView.findViewById(R.id.ivgoToBrowser);
            ivMenu = itemView.findViewById(R.id.ivMenu);
            tvTitle = itemView.findViewById(R.id.tvUserName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

    }
}
