package com.example.capstonedesignproject.view.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstonedesignproject.R;

public class MyPageFragment extends Fragment {

    public MyPageFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.BT_manageProfile:
                        Intent intent = new Intent(getActivity(), ManageMyProfile.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        Button BT_manageProfile = v.findViewById(R.id.BT_manageProfile);
        BT_manageProfile.setOnClickListener(onClickListener);

        return v;
    }
}
