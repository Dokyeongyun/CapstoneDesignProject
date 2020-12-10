package com.example.capstonedesignproject.view.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.CustomDialog;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageFragment extends Fragment {
    @BindView(R.id.BT_manageProfile) Button BT_manageProfile;
    @BindView(R.id.BT_withdraw) Button BT_withdraw;
    @BindView(R.id.BT_getArticles) Button BT_getArticles;
    @BindView(R.id.BT_favorites) Button BT_favorites;
    private CustomDialog customDialog;

    public MyPageFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    /**
     * 프로필 수정 페이지로 이동
     */
    @OnClick(R.id.BT_manageProfile) void ClickManageProfile(){
        Intent intent = new Intent(getActivity(), ManageMyProfile.class);
        startActivity(intent);
    }

    /**
     * 회원 탈퇴
     */
    @OnClick(R.id.BT_withdraw) void withdraw(){
        // TODO Dialog 띄워서 한번 더 확인시키고, 동의하면 회원탈퇴 -> 로그인화면으로 이동
        customDialog = new CustomDialog(getActivity(),
                "회원 탈퇴", "정말 탈퇴하시겠습니까? \n삭제된 계정은 복구할 수 없습니다.",
                okListener, cancelListener);
        customDialog.show();
    }

    private View.OnClickListener okListener = v -> {
        String autoCheck = LoginActivity.autoLoginFile.getString("autoLogin","");
        if(autoCheck.equals("true")){
            LoginActivity.editor.putString("autoLogin", "false");
            LoginActivity.editor.apply();
        }
        String result = "";
        try {
            result = new Task(getActivity()).execute("member/withdraw.do", HomeActivity.memberID).get();
        } catch (Exception e){ e.printStackTrace(); }

        if(!result.equals("\"-1\"")){
            Toast.makeText(getActivity(), "이용해주셔서 감사합니다__^^__", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    };

    private View.OnClickListener cancelListener = v -> {
        customDialog.dismiss();
    };

    /**
     * 현재 사용자가 작성한 게시글 읽기
     */
    @OnClick(R.id.BT_getArticles) void getArticles(){
        Intent intent = new Intent(getActivity(), ManageCommunity.class);
        intent.putExtra("Type", "Articles");
        startActivity(intent);
    }

    /**
     * 현재 사용자가 즐겨찾는 차박지 목록
     */
    @OnClick(R.id.BT_favorites) void getFavorites(){
        Intent intent = new Intent(getActivity(), ManageCommunity.class);
        intent.putExtra("Type", "Favorites");
        startActivity(intent);
    }


}
