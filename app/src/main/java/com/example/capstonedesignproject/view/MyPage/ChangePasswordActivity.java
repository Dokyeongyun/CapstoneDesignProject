package com.example.capstonedesignproject.view.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.Login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.BasicToolbar) Toolbar mToolbar;
    @BindView(R.id.BT_changePassword) Button BT_changePassword;
    @BindView(R.id.ET_changePw) EditText ET_changePw;
    @BindView(R.id.ET_changePwChk) EditText ET_changePwChk;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        // Toolbar
        mToolbar.setTitle("비밀번호 변경");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
    }

    @OnClick(R.id.BT_changePassword) void changePassword(){
        String password = ET_changePw.getText().toString().trim();
        String passwordChk = ET_changePwChk.getText().toString().trim();

        if(password.equals("")){
            Toast.makeText(this, "변경할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordChk)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String result = "";
        try {
            result = new Task(this).execute("member/changePassword.do", email, password).get();
        } catch (Exception e){ e.printStackTrace(); }

        if(result.equals("\"1\"")){
            Toast.makeText(this, "비밀번호가 변경되었습니다. 변경된 비밀번호로 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "실패! 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
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
