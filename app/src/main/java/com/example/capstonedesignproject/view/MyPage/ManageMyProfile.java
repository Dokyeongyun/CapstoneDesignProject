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
import com.example.capstonedesignproject.view.Login.LoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageMyProfile extends AppCompatActivity {
    static boolean isNickDuplicated = true;
    @BindView(R.id.Toolbar_mmp) Toolbar mToolbar;
    @BindView(R.id.LL_change) LinearLayout LL_change;
    @BindView(R.id.LL_changeNick) LinearLayout LL_changeNick;
    @BindView(R.id.LL_password) LinearLayout LL_password;
    @BindView(R.id.LL_nickName) LinearLayout LL_nickName;
    @BindView(R.id.ET_changePw) EditText ET_changePw;
    @BindView(R.id.ET_changePwChk) EditText ET_changePwChk;
    @BindView(R.id.BT_changePw) Button BT_changePw;
    @BindView(R.id.BT_changeNick) Button BT_changeNick;
    @BindView(R.id.BT_changeCancel) Button BT_changeCancel;
    @BindView(R.id.BT_changeNickCancel) Button BT_changeNickCancel;
    @BindView(R.id.BT_changeOK) Button BT_changeOK;
    @BindView(R.id.BT_changeNickOK) Button BT_changeNickOK;
    @BindView(R.id.BT_duplicateChk) Button BT_duplicateChk;
    @BindView(R.id.BT_withdraw) Button BT_withdraw;

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
    @OnClick(R.id.BT_changeOK) void ClickChangeOK() {
        if(ET_changePw.getText().toString().equals(ET_changePwChk.getText().toString())){
            // TODO DB Update 수행, 완료되면 다시 내정보관리 페이지 보여주기

            // 액티비티 refresh
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }else{
            Toast.makeText(ManageMyProfile.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.BT_changeCancel) void ClickChangeCancel() {
        LL_change.setVisibility(View.GONE);
        LL_password.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.BT_changeNickOK) void ClickChangeNickOK() {
        // TODO DB Update 수행, 완료되면 다시 내정보관리 페이지 보여주기
        if(!isNickDuplicated){
            // 액티비티 refresh
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }else{
            Toast.makeText(ManageMyProfile.this, "닉네임 중복확인을 해야합니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.BT_changeNickCancel) void ClickChangeNickCancel() {
        LL_changeNick.setVisibility(View.GONE);
        LL_nickName.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.BT_duplicateChk) void ClickDuplicateChk() {
        // TODO 중복확인 -> 중복아니면 isNickDuplicated = false
        // if(중복아니면){
        Toast.makeText(ManageMyProfile.this, "사용가능한 닉네임입니다!", Toast.LENGTH_SHORT).show();
        isNickDuplicated = false;
        //} else {
        // Toast.makeText(ManageMyProfile.this, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
        // }
    }
    @OnClick(R.id.BT_withdraw) void ClickWithdraw() {
        // TODO Dialog 띄워서 한번 더 확인시키고, 동의하면 회원탈퇴 -> 로그인화면으로 이동
        String autoCheck = LoginActivity.autoLoginFile.getString("autoLogin","");
        if(autoCheck.equals("true")){
            LoginActivity.editor.putString("autoLogin", "false");
            LoginActivity.editor.apply();
        }
        Toast.makeText(ManageMyProfile.this, "이용해주셔서 감사합니다__^^__", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ManageMyProfile.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

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
