package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

public class ShowPostActivity extends AppCompatActivity {
    boolean userLike; // 현재 세션의 유저가 해당 게시글 좋아요 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        // 인텐트 수신 및 setText
        Intent intent = getIntent();
        String postWriter = intent.getStringExtra("postWriter");
        int postID = intent.getIntExtra("postID", 0);
        String postTitle = intent.getStringExtra("postTitle");
        int postPicture = intent.getIntExtra("postPicture", 0);
        String postDate = intent.getStringExtra("postDate");
        String postContent = intent.getStringExtra("postContent");

        TextView TV_title = findViewById(R.id.TV_postTitle);
        TextView TV_content = findViewById(R.id.TV_postContent);
        TextView TV_postWriter = findViewById(R.id.TV_postWriter);
        TextView TV_postDate = findViewById(R.id.TV_postDate);
        ImageView iv = findViewById(R.id.IV_postPicture);

        TV_title.setText(postTitle);
        TV_content.setText(postContent);
        TV_postWriter.setText(postWriter);
        TV_postDate.setText(postDate);
        iv.setImageResource(postPicture);

        // TODO 게시글 이미지를 작게 보여주도록 수정해야 함
        // TODO 댓글 서비스 제공해야 함
    }

    // 툴바에 메뉴 인플레이트
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_post_menu, menu);
        return true;
    }

    // 툴바 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LikePost(View view){
        // 해당 유저의 게시글 좋아요 여부 가져옴 ( userLike )
        if(view.getId() == R.id.BT_LikePost){
            if(userLike){
                Toast.makeText(this, "이미 Like 한 게시글입니다.", Toast.LENGTH_SHORT).show();
            }else{
                // DB에 좋아요 반영
                userLike = true;
                Toast.makeText(this, "Like! 감사합니다.", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(userLike){
                userLike = false;
                Toast.makeText(this, "UnLike!", Toast.LENGTH_SHORT).show();
            }else{
                // DB에 싫어요 반영
                Toast.makeText(this, "UnLike!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "이미 UnLike 한 게시글입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

