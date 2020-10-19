package com.example.capstonedesignproject.view.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.GMailSender;
import com.example.capstonedesignproject.view.MyPage.ChangePasswordActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class FindPasswordActivity extends AppCompatActivity {
    Button findPassword;
    LinearLayout LL_findPassword, LL_sendVerification;
    String authCode;
    EditText ET_email, ET_verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        /* SMTP 사용을 위한 인터넷 허용 */
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        ET_email = findViewById(R.id.ET_email);
        ET_verificationCode = findViewById(R.id.ET_verificationCode);
        findPassword = findViewById(R.id.BT_findPassword);
        LL_findPassword = findViewById(R.id.LL_findPassword);
        LL_sendVerification = findViewById(R.id.LL_sendVerification);
        // TODO 이메일 입력 안되면 완료버튼 비활성화

    }

    public void BackToLogin(View view) {
        // TODO 로그인 페이지로 이동
        finish();
    }

    public void SendAuthCode(View view) {
        // TODO 존재하는 이메일이면 이메일로 인증번호 발송, LL_verification VISIBLE, 존재하지 않으면 오류메시지
        try {
            GMailSender gMailSender = new GMailSender("aservmz@gmail.com", "ehruddbs4$");
            //GMailSender.sendMail(제목, 본문내용, 받는사람);
            authCode = gMailSender.getEmailCode();
            gMailSender.sendMail("이메일 인증요청", "인증번호 : " + authCode, ET_email.getText().toString());
            Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
            LL_findPassword.setVisibility(View.GONE);
            LL_sendVerification.setVisibility(View.VISIBLE);
        } catch (SendFailedException e) {
            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CheckVerificationCode(View view) {
        // TODO 인증번호 확인 -> 일치하면 로그인페이지, 아니면 오류메시지

        if(ET_verificationCode!=null && !ET_verificationCode.getText().toString().equals("")){
            if(authCode.equals(ET_verificationCode.getText().toString())){
                Toast.makeText(this, "확인되었습니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                // TODO 액티비티 스택 관리
            }else{
                Toast.makeText(this, "인증번호가 잘못되었습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "인증번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Resend(View view) {
        // TODO 인증번호 재발송
        findPassword.callOnClick();
    }
}
