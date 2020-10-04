package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capstonedesignproject.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // TODO 이메일, 비밀번호 입력 안하면 로그인 버튼 비활성화
        // TODO 로그인 작업 처리 중 프로그레스 바 띄우기
        // TODO 카카오톡, 구글, 페이스북으로 로그인하기 추가

    }

    public void Login(View view) {
        // TODO 로그인 정보 확인하기
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void Join(View view) {
        // TODO 회원가입 페이지로 이동
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    public void FindPassword(View view) {
        // TODO 비밀번호찾기 페이지로 이동
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }
}
