package com.ensim.mic.slink.BottomSheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Fragment.FoldersFragment;
import com.ensim.mic.slink.R;

public class BottomSheetFilter extends BottomSheetDialogFragment implements View.OnClickListener {

    static final public int FILTER_ANYONE = 0;
    static final public int FILTER_OWNED_BY_ME = 1;
    static final public int FILTER_SHARED_WITH_ME = 2;
    static final public int FILTER_SUBSCRIPTION = 3;

    private int choosen_filter = FILTER_ANYONE;

    RadioGroup radioGroup;
    RadioButton radioButton, rbAnyone, rbOwnerMe, rbshare, rbsSubscriptions;

    private ChangeListener mlistener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_filters, container, false);

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
                setChoosen_filter(FILTER_ANYONE);
                Toast.makeText(getActivity(), "Filter : Anyone applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_ownedByme:
                setChoosen_filter(FILTER_OWNED_BY_ME);
                Toast.makeText(getActivity(), "Filter : Owned by me applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_sharedWithMe:
                setChoosen_filter(FILTER_SHARED_WITH_ME);
                Toast.makeText(getActivity(), "Filter : Shared with me applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.filter_subscription:
                setChoosen_filter(FILTER_SUBSCRIPTION);
                Toast.makeText(getActivity(), "Filter : Subscriptions applied !", Toast.LENGTH_LONG).show();
                break;
            default:
                setChoosen_filter(FILTER_ANYONE);
        }
    }

    public int getChoosen_filter() {
        return choosen_filter;
    }

    public void setChoosen_filter(int choosen_filter) {
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

