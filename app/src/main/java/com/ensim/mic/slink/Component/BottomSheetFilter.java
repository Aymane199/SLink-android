package com.ensim.mic.slink.Component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.utils.FolderFilter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetFilter extends BottomSheetDialogFragment implements View.OnClickListener {

    private FolderFilter choosen_filter = FolderFilter.FILTER_ANYONE;

    RadioGroup radioGroup;
    RadioButton radioButton, rbAnyone, rbOwnerMe, rbshare, rbsSubscriptions;

    private ChangeListener mlistener;
    private int userId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_filters, container, false);

        userId = Model.getInstance().getCurrentUser().getContent().getId();

        radioGroup = v.findViewById(R.id.radioGroup);

        rbAnyone = v.findViewById(R.id.filter_anyone);
        rbOwnerMe = v.findViewById(R.id.filter_ownedByme);
        rbshare = v.findViewById(R.id.filter_sharedWithMe);
        rbsSubscriptions = v.findViewById(R.id.filter_subscription);

        rbAnyone.setOnClickListener(this);
        rbOwnerMe.setOnClickListener(this);
        rbshare.setOnClickListener(this);
        rbsSubscriptions.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        switch(radioButton.getId()) {
            case R.id.filter_anyone:
                setChoosen_filter(FolderFilter.FILTER_ANYONE);
                Toast.makeText(getActivity(), "Filter : Anyone applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_ownedByme:
                setChoosen_filter(FolderFilter.FILTER_OWNED_BY_ME);
                Toast.makeText(getActivity(), "Filter : Owned by me applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_sharedWithMe:
                setChoosen_filter(FolderFilter.FILTER_SHARED_WITH_ME);
                Toast.makeText(getActivity(), "Filter : Shared with me applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_subscription:
                setChoosen_filter(FolderFilter.FILTER_SUBSCRIPTION);
                Toast.makeText(getActivity(), "Filter : Subscriptions applied !", Toast.LENGTH_LONG).show();
                break;
            default:
                setChoosen_filter(FolderFilter.FILTER_ANYONE);
        }
    }

    public FolderFilter getChoosen_filter() {
        return choosen_filter;
    }

    public void setChoosen_filter(FolderFilter choosen_filter) {
        this.choosen_filter = choosen_filter;
        if (mlistener != null) mlistener.onChange();

    }

    public ChangeListener getListener() {
        return mlistener;
    }

    public void setListener(ChangeListener listener) {
        this.mlistener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }

}

