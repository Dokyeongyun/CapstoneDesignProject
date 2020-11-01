package com.example.capstonedesignproject.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    static boolean isEmailCheck = false, isNickCheck = false;
    static String checkedEmail = "", checkedNick = "";
    EditText ET_email, ET_password, ET_passwordChk, ET_nick;
    LinearLayout LL_joinForm, LL_email, LL_pw, LL_nick, LL_join;
    TextView TV_joinProgress;
    View[] views;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // TODO 이메일 중복확인 X, 비밀번호 불일치, 닉네임 비어있으면 완료버튼 비활성화
        // TODO 카카오톡, 구글, 페이스북으로 가입하기 추가

        // findViewById View 초기화
        Init();


    }

    private void Init() {
        // Toolbar
        final Toolbar mToolbar = findViewById(R.id.Toolbar_join);
        mToolbar.setTitle("회원가입");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        ET_email = findViewById(R.id.ET_joinEmail);
        ET_password = findViewById(R.id.ET_joinPassword);
        ET_passwordChk = findViewById(R.id.ET_joinPasswordChk);
        ET_nick = findViewById(R.id.ET_nick);
        LL_joinForm = findViewById(R.id.LL_joinForm);
        LL_email = findViewById(R.id.LL_email);
        LL_nick = findViewById(R.id.LL_nick);
        LL_pw = findViewById(R.id.LL_pw);
        LL_join = findViewById(R.id.LL_join);
        TV_joinProgress = findViewById(R.id.TV_joinProgress);

        views = new View[3];
        views[0] = LL_nick;
        views[1] = LL_pw;
        views[2] = LL_join;
    }

    // 이전메뉴
    public void Previous(View view) {
        if (index == 0) {
            index = 1;
        }
        transAnimation(false, views[--index]);
        TV_joinProgress.setText("(" + (index + 1) + " / " + views.length + ")");
        TV_joinProgress.setTextColor(Color.parseColor("#C5C5C5"));
    }

    // 다음메뉴
    public void Next(View view) {
        if (index >= views.length) {
            index = views.length - 1;
        }
        transAnimation(true, views[index++]);
        TV_joinProgress.setText("(" + (index + 1) + " / " + views.length + ")");
        if (index == views.length) {
            TV_joinProgress.setText("완료버튼을 클릭해주세요!");
            TV_joinProgress.setTextColor(Color.RED);
        }
    }

    // 이메일 중복확인
    public void CheckEmail(View view) {
        // TODO DB에서 이메일 중복확인
        isEmailCheck = true;
        checkedEmail = ET_email.getText().toString().trim();
        Toast.makeText(this, checkedEmail, Toast.LENGTH_SHORT).show();
    }

    // 닉네임 중복확인
    public void CheckNick(View view) {
        // TODO DB에서 닉네임 중복확인
        isNickCheck = true;
        checkedNick = ET_nick.getText().toString().trim();
        Toast.makeText(this, checkedNick, Toast.LENGTH_SHORT).show();
    }

    // 입력된 정보로 회원가입 수행
    public void doJoin(View view)  {
        String email = ET_email.getText().toString();
        String password = ET_password.getText().toString();
        String passwordChk = ET_passwordChk.getText().toString();
        String nick = ET_nick.getText().toString();

        if (!isEmailCheck || !checkedEmail.equals(email)) {
            Toast.makeText(this, "이메일 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isNickCheck || !checkedNick.equals(nick)) {
            Toast.makeText(this, "닉네임 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            if (password.equals("") || nick.equals("") || email.equals("")) {
                Toast.makeText(this, "입력되지 않은 값이 있습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else if(!password.equals(passwordChk)){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                String result = new Task().execute("member/insert.do", email, nick, password).get();

                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                if(result.equals("\"success\"")){
                    Toast.makeText(this, "회원가입에 성공했습니다. 환영합니다!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "실패! 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e){
            Toast.makeText(this, "서버 연결이 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // View 애니메이션 효과
    private void transAnimation(boolean bool, View view) {
        AnimationSet aniInSet = new AnimationSet(true);
        AnimationSet aniOutSet = new AnimationSet(true);
        aniInSet.setInterpolator(new AccelerateInterpolator());
        Animation transInAni = new TranslateAnimation(0, 0, 800.0f, 0);
        Animation transOutAni = new TranslateAnimation(0, 0, 0, 800.0f);
        transInAni.setDuration(500);
        transOutAni.setDuration(500);
        aniInSet.addAnimation(transInAni);
        aniOutSet.addAnimation(transOutAni);
        if (bool) {
            view.setAnimation(aniInSet);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setAnimation(aniOutSet);
            view.setVisibility(View.GONE);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
