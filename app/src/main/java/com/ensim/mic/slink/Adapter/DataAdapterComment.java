package com.ensim.mic.slink.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Listener.CommentMenuListener;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Comment;

import java.util.List;

public class DataAdapterComment extends RecyclerView.Adapter<DataAdapterComment.myViewHolder> {

    private static final String TAG = "DataAdapter_comment";

    Context mContext;
    List<Comment> mData;
    private OnItemClickListener mListener;


    public DataAdapterComment(Context mContext, List<Comment> mData) {
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
        View v = inflater.inflate(R.layout.template_comment, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final Comment comment = mData.get(i);

        myViewHolder.tvText.setText(comment.getText());
        myViewHolder.tvDate.setText(comment.getDate());
        myViewHolder.tvUserName.setText(comment.getUser().getUserName());
        //TODO userid State = 3
        if(3!=comment.getUser().getId())
            myViewHolder.ivMenu.setVisibility(View.INVISIBLE);

        myViewHolder.ivMenu.setOnClickListener(new CommentMenuListener(mContext, myViewHolder.ivMenu, comment));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface OnItemClickListener {
        void onDescClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMenu;
        TextView tvUserName, tvText , tvDate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivMenu = itemView.findViewById(R.id.ivMenu);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvText = itemView.findViewById(R.id.tvText);
        }

    }


}
