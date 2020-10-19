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

public class ManageMyProfile extends AppCompatActivity {
    static boolean isNickDuplicated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_profile);

        // Toolbar
        final Toolbar mToolbar = findViewById(R.id.Toolbar_mmp);
        mToolbar.setTitle("내정보 관리");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가


        final LinearLayout LL_change = findViewById(R.id.LL_change);
        final LinearLayout LL_changeNick = findViewById(R.id.LL_changeNick);
        final LinearLayout LL_password = findViewById(R.id.LL_password);
        final LinearLayout LL_nickName = findViewById(R.id.LL_nickName);
        final EditText ET_changePw = findViewById(R.id.ET_changePw);
        final EditText ET_changePwChk = findViewById(R.id.ET_changePwChk);

        // 버튼 클릭 리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.BT_changePw:
                        LL_password.setVisibility(View.GONE);
                        LL_change.setVisibility(View.VISIBLE);
                        break;
                    case R.id.BT_changeNick:
                        LL_nickName.setVisibility(View.GONE);
                        LL_changeNick.setVisibility(View.VISIBLE);
                        break;
                    case R.id.BT_changeOK:
                        if(ET_changePw.getText().toString().equals(ET_changePwChk.getText().toString())){
                            // TODO DB Update 수행, 완료되면 다시 내정보관리 페이지 보여주기

                            // 액티비티 refresh
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(ManageMyProfile.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.BT_changeCancel:
                        LL_change.setVisibility(View.GONE);
                        LL_password.setVisibility(View.VISIBLE);
                        break;
                    case R.id.BT_changeNickOK:
                        // TODO DB Update 수행, 완료되면 다시 내정보관리 페이지 보여주기
                        if(!isNickDuplicated){
                            // DB update

                            // 액티비티 refresh
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(ManageMyProfile.this, "닉네임 중복확인을 해야합니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.BT_changeNickCancel:
                        LL_changeNick.setVisibility(View.GONE);
                        LL_nickName.setVisibility(View.VISIBLE);
                        break;
                    case R.id.BT_duplicateChk:
                        // TODO 중복확인 -> 중복아니면 isNickDuplicated = false

                        // if(중복아니면){
                        Toast.makeText(ManageMyProfile.this, "사용가능한 닉네임입니다!", Toast.LENGTH_SHORT).show();
                        isNickDuplicated = false;
                        //} else {
                        // Toast.makeText(ManageMyProfile.this, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        // }
                        break;
                    case R.id.BT_withdraw:
                        // TODO Dialog 띄워서 한번 더 확인시키고, 동의하면 회원탈퇴 -> 로그인화면으로 이동
                        Toast.makeText(ManageMyProfile.this, "이용해주셔서 감사합니다__^^__", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ManageMyProfile.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        };

        Button BT_changePw = findViewById(R.id.BT_changePw);
        Button BT_changeNick = findViewById(R.id.BT_changeNick);
        Button BT_changeCancel = findViewById(R.id.BT_changeCancel);
        Button BT_changeNickCancel = findViewById(R.id.BT_changeNickCancel);
        Button BT_changeOK = findViewById(R.id.BT_changeOK);
        Button BT_changeNickOK = findViewById(R.id.BT_changeNickOK);
        Button BT_duplicateChk = findViewById(R.id.BT_duplicateChk);
        Button BT_withdraw = findViewById(R.id.BT_withdraw);

        BT_changePw.setOnClickListener(onClickListener);
        BT_changeNick.setOnClickListener(onClickListener);
        BT_changeCancel.setOnClickListener(onClickListener);
        BT_changeNickCancel.setOnClickListener(onClickListener);
        BT_changeOK.setOnClickListener(onClickListener);
        BT_changeNickOK.setOnClickListener(onClickListener);
        BT_duplicateChk.setOnClickListener(onClickListener);
        BT_withdraw.setOnClickListener(onClickListener);
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
