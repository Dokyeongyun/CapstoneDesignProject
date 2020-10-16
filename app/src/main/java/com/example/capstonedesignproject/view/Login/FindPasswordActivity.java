package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.capstonedesignproject.R;

public class FindPasswordActivity extends AppCompatActivity {
    Button findPassword;
    LinearLayout LL_findPassword, LL_sendVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        findPassword = findViewById(R.id.BT_findPassword);
        LL_findPassword = findViewById(R.id.LL_findPassword);
        LL_sendVerification = findViewById(R.id.LL_sendVerification);
        // TODO 이메일 입력 안되면 완료버튼 비활성화

    }

    public void BackToLogin(View view) {
        // TODO 로그인 페이지로 이동
        finish();
    }

    public void FindPassword(View view) {
        // TODO 존재하는 이메일이면 이메일로 인증번호 발송, LL_verification VISIBLE, 존재하지 않으면 오류메시지
        LL_findPassword.setVisibility(View.GONE);
        LL_sendVerification.setVisibility(View.VISIBLE);
    }

    public void CheckVerificationCode(View view) {
        // TODO 인증번호 확인 -> 일치하면 로그인페이지, 아니면 오류메시지
    }

    public void Resend(View view) {
        // TODO 인증번호 재발송
    }

}
