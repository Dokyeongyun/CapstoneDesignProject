package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    static EditText ET_email, ET_password, ET_nick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // TODO 이메일 중복확인 X, 비밀번호 불일치, 닉네임 비어있으면 완료버튼 비활성화
        // TODO 카카오톡, 구글, 페이스북으로 가입하기 추가

        ET_email = findViewById(R.id.ET_joinEmail);
        ET_password = findViewById(R.id.ET_joinPassword);
        ET_nick = findViewById(R.id.ET_nick);
    }

    public void CheckEmail(View view) {
        // TODO DB에서 이메일 중복확인
    }

    public void doJoin(View view) throws ExecutionException, InterruptedException {
        // TODO DB에 데이터 삽입, 회원가입 완료 메시지, 메인페이지로 이동
        // TODO password와 passwordChk 값 일치여부 확인

        String email = ET_email.getText().toString();
        String password = ET_password.getText().toString();
        String nick = ET_nick.getText().toString();
        if(email.equals("") && password.equals("") && nick.equals("")){
            Toast.makeText(this, "입력되지 않은 값이 있습니다. 다시 확인해주세요", Toast.LENGTH_SHORT).show();
        }else{
            String result = new Task().execute("Join", email, password, nick).get();

            if(result.equals("Join_OK")){
                Toast.makeText(this, "회원가입에 성공했습니다. 환영합니다!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "실패! 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
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
