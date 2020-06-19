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
import android.widget.Toast;

import com.ensim.mic.slink.R;

public class BottomSheetSort extends BottomSheetDialogFragment implements View.OnClickListener {

    static final public int SORT_MOST_RECENT = 0;
    static final public int SORT_OLDEST = 1;

    private int choosen_sort = SORT_MOST_RECENT;

    RadioGroup radioGroup;
    RadioButton radioButton, radioMostRecent , radioOldest;

    private BottomSheetFilter.ChangeListener mlistener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_sort,container,false);

        radioGroup = v.findViewById(R.id.radioGroup);

        radioMostRecent = v.findViewById(R.id.sort_mostRecent);
        radioOldest = v.findViewById(R.id.sort_oldest);

        radioMostRecent.setOnClickListener(this);
        radioOldest.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        switch(radioButton.getId()) {
            case R.id.sort_mostRecent:
                setChoosen_sort(SORT_MOST_RECENT);
                Toast.makeText(getActivity(), "Sort by the most recent applied !", Toast.LENGTH_LONG).show();
                break;
            case R.id.sort_oldest:
                setChoosen_sort(SORT_OLDEST);
                Toast.makeText(getActivity(), "Sort by the oldest applied !", Toast.LENGTH_LONG).show();

                break;
            default:

        }
    }

    public int getChoosen_sort() {
        return choosen_sort;
    }

    public void setChoosen_sort(int choosen_sort) {
        this.choosen_sort = choosen_sort;
        if (mlistener != null) mlistener.onChange();
    }

    public BottomSheetFilter.ChangeListener getMlistener() {
        return mlistener;
    }

    public void setMlistener(BottomSheetFilter.ChangeListener mlistener) {
        this.mlistener = mlistener;
    }

    public interface ChangeListener {
        void onChange();
    }

}
