package com.example.capstonedesignproject.view.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstonedesignproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageFragment extends Fragment {
    @BindView(R.id.BT_manageProfile) Button BT_manageProfile;
    public MyPageFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
    @OnClick(R.id.BT_manageProfile) void ClickManageProfile(){
        Intent intent = new Intent(getActivity(), ManageMyProfile.class);
        startActivity(intent);
    }
}
