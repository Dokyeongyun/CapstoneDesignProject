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
import com.example.capstonedesignproject.Server.SetApplication;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.ET_joinPassword) EditText ET_password;
    @BindView(R.id.ET_joinPasswordChk) EditText ET_passwordChk;
    @BindView(R.id.ET_nick) EditText ET_nick;
    @BindView(R.id.ET_joinEmail) EditText ET_joinEmail;
    @BindView(R.id.LL_joinForm) LinearLayout LL_joinForm;
    @BindView(R.id.LL_email) LinearLayout LL_email;
    @BindView(R.id.LL_pw) LinearLayout LL_pw;
    @BindView(R.id.LL_nick) LinearLayout LL_nick;
    @BindView(R.id.LL_join) LinearLayout LL_join;
    @BindView(R.id.TV_joinProgress) TextView TV_joinProgress;
    @BindView(R.id.Toolbar_join) Toolbar mToolbar;

    static boolean isEmailCheck = false, isNickCheck = false;
    static String checkedEmail = "", checkedNick = "";
    String joinResult = "", nickDoubleCheckResult = "", idDoubleCheckResult = "";
    View[] views;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        Init();

        // TODO 이메일 중복확인 X, 비밀번호 불일치, 닉네임 비어있으면 완료버튼 비활성화
        // TODO 카카오톡, 구글, 페이스북으로 가입하기 추가
    }

    private void Init() {
        // Toolbar
        mToolbar.setTitle("회원가입");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        views = new View[3];
        views[0] = LL_nick;
        views[1] = LL_pw;
        views[2] = LL_join;
    }

    /**
     * 이전메뉴
     */
    @OnClick(R.id.BT_previousMenu) void Previous() {
        if (index == 0) index = 1;
        transAnimation(false, views[--index]);
        TV_joinProgress.setText("(" + (index + 1) + " / " + views.length + ")");
        TV_joinProgress.setTextColor(Color.parseColor("#C5C5C5"));
    }

    /**
     * 다음메뉴
     */
    @OnClick(R.id.BT_nextMenu) void Next() {
        if (index >= views.length) index = views.length - 1;
        transAnimation(true, views[index++]);
        TV_joinProgress.setText("(" + (index + 1) + " / " + views.length + ")");
        if (index == views.length) {
            TV_joinProgress.setText("완료버튼을 클릭해주세요!");
            TV_joinProgress.setTextColor(Color.RED);
        }
    }

    /**
     * 이메일 중복확인
     */
    @OnClick(R.id.BT_checkEmail) void CheckEmail() {
        checkedEmail = ET_joinEmail.getText().toString().trim();

        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().idDoubleCheck(checkedEmail);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { idDoubleCheckResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(idDoubleCheckResult.equals("1")){
                            isEmailCheck = true;
                            Toast.makeText(application, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                            Next();
                        }else{
                            Toast.makeText(application, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 닉네임 중복확인
     */
    @OnClick(R.id.BT_checkNick) void CheckNick() {
        checkedNick = ET_nick.getText().toString().trim();
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().nickDoubleCheck(checkedNick);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { nickDoubleCheckResult = s; }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        if(nickDoubleCheckResult.equals("1")){
                            isNickCheck = true;
                            Toast.makeText(application, "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            Next();
                        }else{
                            Toast.makeText(application, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 회원가입
     */
    @OnClick(R.id.BT_doJoin) void doJoin()  {
        String email = ET_joinEmail.getText().toString().trim();
        String password = ET_password.getText().toString().trim();
        String passwordChk = ET_passwordChk.getText().toString().trim();
        String nick = ET_nick.getText().toString().trim();
        if (!isEmailCheck || !checkedEmail.equals(email)) {
            Toast.makeText(this, "이메일 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isNickCheck || !checkedNick.equals(nick)) {
            Toast.makeText(this, "닉네임 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
            if (password.equals("") || nick.equals("") || email.equals("")) {
                Toast.makeText(this, "입력되지 않은 값이 있습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else if(!password.equals(passwordChk)){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
                Observable<String> observable = application.getMemberService().join(email, nick, password);
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onNext(String s) { joinResult = s; }
                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(application, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            @Override
                            public void onCompleted() {
                                if(joinResult.equals("success")){
                                    Toast.makeText(application, "회원가입에 성공했습니다. 환영합니다!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(application, "실패! 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
