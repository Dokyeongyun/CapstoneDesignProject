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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends AppCompatActivity {

    @BindView(R.id.BT_findPassword) Button findPassword;
    @BindView(R.id.LL_findPassword) LinearLayout LL_findPassword;
    @BindView(R.id.LL_sendVerification) LinearLayout LL_sendVerification;
    @BindView(R.id.ET_email) EditText ET_email;
    @BindView(R.id.ET_verificationCode) EditText ET_verificationCode;

    String authCode;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        /* SMTP 사용을 위한 인터넷 허용 */
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
        // TODO 이메일 입력 안되면 완료버튼 비활성화
    }

    @OnClick(R.id.BT_back) void BackToLogin() { finish(); }
    @OnClick(R.id.BT_findPassword) void SendAuthCode() {
        email = ET_email.getText().toString().trim();
        try {
            GMailSender gMailSender = new GMailSender("aservmz@gmail.com", "ehruddbs4$");
            authCode = gMailSender.getEmailCode();
            //GMailSender.sendMail(제목, 본문내용, 받는사람);
            gMailSender.sendMail("이메일 인증요청", "인증번호 : " + authCode, email);
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
    @OnClick(R.id.BT_checkVerificationCode) void CheckVerificationCode() {
        if(ET_verificationCode!=null && !ET_verificationCode.getText().toString().equals("")){
            if(authCode.equals(ET_verificationCode.getText().toString())){
                Toast.makeText(this, "확인되었습니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                // TODO 액티비티 스택 관리
            }else{
                Toast.makeText(this, "인증번호가 잘못되었습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "인증번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.BT_resend) void Resend() {
        findPassword.callOnClick();
    }
}
