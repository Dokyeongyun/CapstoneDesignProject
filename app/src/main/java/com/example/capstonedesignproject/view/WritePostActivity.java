package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

public class WritePostActivity extends AppCompatActivity {

    static EditText ET_title, ET_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        ET_title = findViewById(R.id.ET_title);
        ET_content = findViewById(R.id.ET_content);
    }

    public void AddPhoto(View view) {
        // TODO 갤러리에서 사진 추가하기 기능
        // TODO 사진 추가 후 코드로 추가한 사진 ImageButton 추가하기
    }

    public void writeComplete(View view) {
        // TODO 글 쓰기 완료 -> DB 에 저장 및 액티비티 종료
        // TODO 제목, 내용 비어있을 때 예외처리
        if(ET_title.getText()!=null && !ET_title.getText().toString().equals("")){
            if(ET_content.getText()!=null && !ET_content.getText().toString().equals("")){
                // DB 삽입

                // 테스트
                Intent intent = new Intent();
                intent.putExtra("title", ET_title.getText().toString());
                intent.putExtra("content", ET_content.getText().toString());
                intent.putExtra("writer", "익명");
                intent.putExtra("date", System.currentTimeMillis());
                intent.putExtra("picture", 0);
                setResult(1, intent);

                finish();
                Toast.makeText(this, "게시글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
