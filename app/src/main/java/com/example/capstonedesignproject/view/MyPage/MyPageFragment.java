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
import com.example.capstonedesignproject.Server.SetApplication;
import com.example.capstonedesignproject.view.ETC.CustomDialog;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Login.LoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyPageFragment extends Fragment {
    @BindView(R.id.BT_manageProfile) Button BT_manageProfile;
    @BindView(R.id.BT_withdraw) Button BT_withdraw;
    @BindView(R.id.BT_getArticles) Button BT_getArticles;
    @BindView(R.id.BT_getReviews) Button BT_getReviews;
    @BindView(R.id.BT_favorites) Button BT_favorites;
    private CustomDialog customDialog;
    String withdrawResult = "";

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
        customDialog = new CustomDialog(getActivity(),
                "회원 탈퇴", "정말 탈퇴하시겠습니까? \n삭제된 계정은 복구할 수 없습니다.",
                okListener, cancelListener);
        customDialog.show();
    }

    /**
     * 회원탈퇴 확인 리스너
     */
    private View.OnClickListener okListener = v -> {
        String autoCheck = LoginActivity.autoLoginFile.getString("autoLogin","");
        if(autoCheck.equals("true")){
            LoginActivity.editor.putString("autoLogin", "false");
            LoginActivity.editor.apply();
        }
        final SetApplication application = (SetApplication) Objects.requireNonNull(getActivity()).getApplication();
        Observable<String> observable = application.getMemberService().withdraw(HomeActivity.memberID);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { withdrawResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(!withdrawResult.equals("-1")){
                            Toast.makeText(application, "이용해주셔서 감사합니다__^^__", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(application, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
    };

    /**
     * 회원탈퇴 취소 리스너
     */
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

    /**
     * 현재 사용자가 즐겨찾는 차박지 목록
     */
    @OnClick(R.id.BT_getReviews) void getReviews(){
        Intent intent = new Intent(getActivity(), ManageCommunity.class);
        intent.putExtra("Type", "Reviews");
        startActivity(intent);
    }
}
