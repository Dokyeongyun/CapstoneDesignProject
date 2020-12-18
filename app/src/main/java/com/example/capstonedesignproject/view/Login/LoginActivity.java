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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.SetApplication;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ET_email) EditText ET_email;
    @BindView(R.id.ET_password) EditText ET_password;
    @BindView(R.id.BT_login) Button BT_login;
    @BindView(R.id.CB_autoLogin) CheckBox CB_autoLogin;
    boolean autoLogin = false;
    String autoCheck, loginResult = "";
    public static SharedPreferences autoLoginFile;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO 이메일, 비밀번호 입력 안하면 로그인 버튼 비활성화
        // TODO 카카오톡, 구글, 페이스북으로 로그인하기 추가
        Log.d("Debug Key", Objects.requireNonNull(getSignature(getApplicationContext())));

        ButterKnife.bind(this);
        AutoLogin();
        ET_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    ET_password.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }else{
                    ET_password.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6E6E71")));
                    BT_login.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6E6E71")));
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        ET_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    BT_login.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#96EAC1")));
                }else{
                    BT_login.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6E6E71")));
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    /**
     * 자동로그인
     */
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

    /**
     * 자동로그인 체크박스 선택/해제
     */
    @OnClick(R.id.CB_autoLogin) void AutoLoginCheck(){ autoLogin = CB_autoLogin.isChecked(); }

    /**
     * 로그인 작업 수행
     */
    @OnClick(R.id.BT_login) void Login() {
        String id = ET_email.getText().toString();
        String password = ET_password.getText().toString();

        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().login(id, password);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { loginResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if (loginResult.equals(id)) {
                            if (autoLogin) {
                                editor.putString("autoLogin", "true");
                                editor.putString("id", id);
                                editor.apply();
                            }
                            Toast.makeText(application, "로그인 성공!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(application, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("memberID", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(application, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 회원가입 페이지로 이동
     */
    @OnClick(R.id.BT_doJoin) void Join() {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    /**
     * 비밀번호 찾기 페이지로 이동
     */
    @OnClick(R.id.BT_findPassword) void FindPassword() {
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
