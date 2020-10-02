package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.capstonedesignproject.R;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // TODO 이메일 중복확인 X, 비밀번호 불일치, 닉네임 비어있으면 완료버튼 비활성화
        // TODO 카카오톡, 구글, 페이스북으로 가입하기 추가
    }

    public void CheckEmail(View view) {
        // TODO DB에서 이메일 중복확인
    }

    public void doJoin(View view) {
        // TODO DB에 데이터 삽입, 회원가입 완료 메시지, 메인페이지로 이동
    }

    public void SelectMan(View view) {
        // TODO 여성 버튼 색 어둡게, 남성 버튼 색 밝게, isMan=true
    }

    public void SelectWoman(View view) {
        // TODO 남성 버튼 색 어둡게, 여성 버튼 색 밝게, isMan=false
    }

    public void BackToLogin(View view) {
        // TODO 로그인 페이지로 이동
        finish();
    }
}
