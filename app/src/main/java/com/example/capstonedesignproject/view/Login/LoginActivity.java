package com.example.capstonedesignproject.view.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ET_email) EditText ET_email;
    @BindView(R.id.ET_password) EditText ET_password;
    @BindView(R.id.BT_member) Button BT_member;
    @BindView(R.id.BT_guest) Button BT_guest;
    @BindView(R.id.CB_autoLogin) CheckBox CB_autoLogin;
    boolean isMember = true, autoLogin = false;
    String autoCheck;
    public static SharedPreferences autoLoginFile;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO 이메일, 비밀번호 입력 안하면 로그인 버튼 비활성화
        // TODO 로그인 작업 처리 중 프로그레스 바 띄우기
        // TODO 카카오톡, 구글, 페이스북으로 로그인하기 추가
        Log.d("Debug Key", Objects.requireNonNull(getSignature(getApplicationContext())));

        ButterKnife.bind(this);
        AutoLogin();
    }

    @SuppressLint("CommitPrefEdits")
    void AutoLogin(){
        autoLoginFile = getSharedPreferences("autoLoginFile", Activity.MODE_PRIVATE);
        editor = autoLoginFile.edit();
        autoCheck = autoLoginFile.getString("autoLogin","");
        if(autoCheck.equals("true")){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("memberID", autoLoginFile.getString("id", ""));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @OnClick(R.id.CB_autoLogin) void AutoLoginCheck(){
        autoLogin = CB_autoLogin.isChecked();
    }

    @OnClick(R.id.BT_guest) void Guest(View view) {
        isMember = false;
        BT_guest.setBackgroundColor(Color.parseColor("#D30C0B0E"));
        BT_member.setBackgroundColor(Color.WHITE);
        BT_guest.setTextColor(Color.WHITE);
        BT_member.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.BT_member) void Member(View view) {
        isMember = true;
        BT_member.setBackgroundColor(Color.parseColor("#D30C0B0E"));
        BT_guest.setBackgroundColor(Color.WHITE);
        BT_member.setTextColor(Color.WHITE);
        BT_guest.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.BT_login) void Login(View view) {
        if (!isMember) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            String id = ET_email.getText().toString();
            String password = ET_password.getText().toString();
            String result = "";
            try {
                result = new Task(this).execute("member/login.do", id, password).get();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(result != null) {
                    if (result.equals("\"" + id + "\"")) {
                        if (autoLogin) {
                            editor.putString("autoLogin", "true");
                            editor.putString("id", id);
                            editor.apply();
                        }
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("memberID", id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @OnClick(R.id.BT_doJoin) void Join(View view) {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.BT_findPassword) void FindPassword(View view) {
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }

    private static String getSignature(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (int i = 0; i < packageInfo.signatures.length; i++) {
                Signature signature = packageInfo.signatures[i];
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
