package com.ensim.mic.slink.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ensim.mic.slink.Activities.SavedLinksActivity;
import com.ensim.mic.slink.R;

public class PreferencesFragment extends Fragment implements View.OnClickListener {

    View mview;
    ImageView ivSave;
    TextView tvSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_preferences,container,false);
        ivSave = mview.findViewById(R.id.ivSave);
        tvSave = mview.findViewById(R.id.tvSave);

        ivSave.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(mview.getContext(), SavedLinksActivity.class);
        switch(v.getId())
        {
            case R.id.ivSave :
                mview.getContext().startActivity(i);
                break;
            case R.id.tvSave :
                mview.getContext().startActivity(i);
                break;
        }
    }
}
