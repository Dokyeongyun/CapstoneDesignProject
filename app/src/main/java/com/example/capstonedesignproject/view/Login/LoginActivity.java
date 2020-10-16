package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.capstonedesignproject.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // TODO 이메일, 비밀번호 입력 안하면 로그인 버튼 비활성화
        // TODO 로그인 작업 처리 중 프로그레스 바 띄우기
        // TODO 카카오톡, 구글, 페이스북으로 로그인하기 추가
        Log.d("Debug Key", getSigneture(getApplicationContext()));

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

    public static String getSigneture(Context context){
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for(int i = 0; i < packageInfo.signatures.length; i++){
                Signature signature = packageInfo.signatures[i];
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }

        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
