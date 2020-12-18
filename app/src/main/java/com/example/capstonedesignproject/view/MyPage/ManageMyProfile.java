package com.example.capstonedesignproject.view.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ManageMyProfile extends AppCompatActivity {
    boolean isNickDuplicated = true;
    @BindView(R.id.Toolbar_mmp) Toolbar mToolbar;
    @BindView(R.id.LL_change) LinearLayout LL_change;
    @BindView(R.id.LL_changeNick) LinearLayout LL_changeNick;
    @BindView(R.id.LL_password) LinearLayout LL_password;
    @BindView(R.id.LL_nickName) LinearLayout LL_nickName;
    @BindView(R.id.ET_changePw) EditText ET_changePw;
    @BindView(R.id.ET_changePwChk) EditText ET_changePwChk;
    @BindView(R.id.BT_changePw) Button BT_changePw;
    @BindView(R.id.BT_changeNick) Button BT_changeNick;
    @BindView(R.id.ET_changeNick) EditText ET_changeNick;
    @BindView(R.id.BT_changeCancel) Button BT_changeCancel;
    @BindView(R.id.BT_changeNickCancel) Button BT_changeNickCancel;
    @BindView(R.id.BT_changeOK) Button BT_changeOK;
    @BindView(R.id.BT_changeNickOK) Button BT_changeNickOK;
    @BindView(R.id.BT_duplicateChk) Button BT_duplicateChk;
    @BindView(R.id.BT_withdraw) Button BT_withdraw;
    private CustomDialog customDialog;
    String changePwResult = "", changeNickResult = "", nickDoubleCheckResult = "", withdrawResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_profile);
        ButterKnife.bind(this);
        // Toolbar
        mToolbar.setTitle("내정보 관리");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가
    }

    @OnClick(R.id.BT_changePw) void ClickChangePw(){
        LL_password.setVisibility(View.GONE);
        LL_change.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.BT_changeNick) void ClickChangeNick(){
        LL_nickName.setVisibility(View.GONE);
        LL_changeNick.setVisibility(View.VISIBLE);
    }

    /**
     * 비밀번호 변경
     */
    @OnClick(R.id.BT_changeOK) void ClickChangeOK() {
        if(!ET_changePw.getText().toString().equals(ET_changePwChk.getText().toString())) {
            Toast.makeText(ManageMyProfile.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            return;
        }
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().changePassword(HomeActivity.memberID, ET_changePw.getText().toString());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { changePwResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(!changePwResult.equals("-1")){ // 성공
                            Toast.makeText(application, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @OnClick(R.id.BT_changeCancel) void ClickChangeCancel() {
        LL_change.setVisibility(View.GONE);
        LL_password.setVisibility(View.VISIBLE);
    }

    /**
     * 닉네임 변경
     */
    @OnClick(R.id.BT_changeNickOK) void ClickChangeNickOK() {
        if(!isNickDuplicated) {
            Toast.makeText(this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().changeNickname(HomeActivity.memberID, ET_changeNick.getText().toString());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { changeNickResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(!changeNickResult.equals("-1")){ // 성공
                            Toast.makeText(application, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.BT_changeNickCancel) void ClickChangeNickCancel() {
        LL_changeNick.setVisibility(View.GONE);
        LL_nickName.setVisibility(View.VISIBLE);
    }

    /**
     * 닉네임 중복확인
     */
    @OnClick(R.id.BT_duplicateChk) void ClickDuplicateChk() {
        String changeNick = ET_changeNick.getText().toString();
        if(changeNick.equals("")){
            Toast.makeText(this, "변경할 닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().nickDoubleCheck(changeNick);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { nickDoubleCheckResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(nickDoubleCheckResult.equals("1")){
                            Toast.makeText(ManageMyProfile.this, "사용가능한 닉네임입니다!", Toast.LENGTH_SHORT).show();
                            isNickDuplicated = false;
                        }else{
                            Toast.makeText(ManageMyProfile.this, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 회원 탈퇴
     */
    @OnClick(R.id.BT_withdraw) void ClickWithdraw() {
        customDialog = new CustomDialog(this,
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

        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
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

    private View.OnClickListener cancelListener = v -> {
        customDialog.dismiss();
    };

    // 툴바에 메뉴 인플레이트
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_toolbar_menu, menu);
        return true;
    }

    // 툴바 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
