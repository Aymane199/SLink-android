package com.ensim.mic.slink.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Activities.LoginActivity;
import com.ensim.mic.slink.Activities.SavedLinksActivity;
import com.ensim.mic.slink.Component.UserComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class PreferencesFragment extends Fragment implements View.OnClickListener {

    private View mview;
    private ImageView ivSave,ivChangeName;
    private TextView tvSave,tvChangeName,tvUserName,tvMail;
    private CardView cdDisconnect,cdDeleteAccount;
    private ImageView ivPicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_preferences, container, false);

        ivSave = mview.findViewById(R.id.ivSave);
        tvSave = mview.findViewById(R.id.tvSave);
        ivChangeName = mview.findViewById(R.id.ivChangeName);
        tvChangeName = mview.findViewById(R.id.tvChangeName);
        tvUserName = mview.findViewById(R.id.tvUserName);
        tvMail = mview.findViewById(R.id.tvMail);
        cdDisconnect = mview.findViewById(R.id.cdDisconnect);
        cdDeleteAccount = mview.findViewById(R.id.cdDeleteAccount);
        ivPicture = mview.findViewById(R.id.ivPicture);

        ivSave.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        ivChangeName.setOnClickListener(this);
        tvChangeName.setOnClickListener(this);
        cdDisconnect.setOnClickListener(this);
        cdDeleteAccount.setOnClickListener(this);

        User user = Model.getInstance().getCurrentUser().getContent();
        tvUserName.setText(user.getUserName());
        tvMail.setText(user.getGmail());

        Model.getInstance().getCurrentUser().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onDataReady() {
                User user = Model.getInstance().getCurrentUser().getContent();
                tvUserName.setText(user.getUserName());
                tvMail.setText(user.getGmail());
                String picture = user.getPicture();
                if(picture != null)
                Picasso.get().load(Uri.parse(picture)).placeholder(R.drawable.ic_face_black_24dp).error(R.drawable.ic_face_black_24dp).into(ivPicture);

            }

            @Override
            public void onFailed() {

            }
        });


        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(mview.getContext(), SavedLinksActivity.class);
        switch (v.getId()) {
            case R.id.ivSave:
            case R.id.tvSave:
                mview.getContext().startActivity(i);
                break;
            case R.id.tvChangeName:
            case R.id.ivChangeName:
                new UserComponents().showRenameUserName(getActivity(), Model.getInstance().getCurrentUser().getContent());
                break;
            case R.id.cdDeleteAccount:
                new UserComponents().showDeleteDialog(getActivity(), Model.getInstance().getCurrentUser().getContent().getId());
                break;
            case R.id.cdDisconnect:
                signOut();
                break;
        }
    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        requireActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
    }
}
