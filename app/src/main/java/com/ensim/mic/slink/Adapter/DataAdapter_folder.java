package com.ensim.mic.slink.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Listener.FolderMenuListener;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter_folder extends RecyclerView.Adapter<DataAdapter_folder.myViewHolder> {


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
        String imgUrl = mData.get(i).getPicture();
        myViewHolder.im_folder.setImageResource(R.drawable.ic_folder);

        try {
            Picasso.get().load(Uri.parse(imgUrl)).into(myViewHolder.im_folder);
        } catch (Exception e) {

        }

        myViewHolder.tvTitle.setText(mData.get(i).getName());
        myViewHolder.tvOwner.setText(mData.get(i).getOwner());
        myViewHolder.tvLike.setText(mData.get(i).getLikes() + "");
        myViewHolder.tvLink.setText(mData.get(i).getLinks() + "");
        myViewHolder.ivMenu.setOnClickListener( new FolderMenuListener(mContext, myViewHolder.ivMenu, mData.get(i)));

      /*  myViewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myViewHolder.ivMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.folder_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuDetails:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext,R.style.CustomAlertDialog);

                                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(mContext, "menuDetails " + mData.get(i).getName(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(mContext, "menuDetals no " + mData.get(i).getName(),
                                                Toast.LENGTH_LONG).show();
                                    }

                                });
                                TextView tvTitle = new TextView(mContext);
                                tvTitle.setText("Delete");
                                tvTitle.setPadding(20, 30, 20, 30);
                                tvTitle.setTextSize(20F);
                                tvTitle.setTextColor(Color.RED);
                                alertDialogBuilder.setMessage("Are you sure you want to permanently remove this folder ?")
                                        .setCustomTitle(tvTitle)
                                        .setCancelable(true);

                                alertDialogBuilder.show();
                                //handle menu1 click
                            case R.id.menuShare:
                                //handle menu2 click
                                break;
                            case R.id.menuGet_link:
                                //handle menu3 click
                                break;
                            case R.id.menuRename:
                                //handle menu3 click
                                break;
                            case R.id.menuDelete:
                                //handle menu3 click
                                break;
                            case R.id.menuAdd_link:
                                //handle menu3 click
                                break;
                            case R.id.menuMake_public:
                                //handle menu3 click
                                break;
                            case R.id.menuChange_picture:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });*/
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
