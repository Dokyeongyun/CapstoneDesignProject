package com.example.capstonedesignproject.view.Board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.GetArticleTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShowPostActivity extends AppCompatActivity {
    boolean userLike; // 현재 세션의 유저가 해당 게시글 좋아요 여부
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.TV_postTitle) TextView TV_title;
    @BindView(R.id.TV_postContent) TextView TV_content;
    @BindView(R.id.TV_postWriter) TextView TV_postWriter;
    @BindView(R.id.TV_postDate) TextView TV_postDate;
    @BindView(R.id.BT_postImage) ImageButton BT_postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        Intent intent = getIntent();
        int articleID = intent.getIntExtra("articleID", 0);
        List<ArticleData> list = new ArrayList<>();
        try {
            list = new GetArticleTask().execute("article/selectOne.do", articleID).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        ArticleData articleData = list.get(0);
        TV_title.setText(articleData.getTitle());
        TV_content.setText(articleData.getContent());
        TV_postWriter.setText(articleData.getMemberId());
        TV_postDate.setText(articleData.getCreateTime());

        Glide.with(this)
                .load("http://211.222.234.14:8080/" + articleData.getUrlPath())
                .placeholder(R.drawable.button_border_gray)
                .into(BT_postImage);
        // TODO 댓글 서비스 제공해야 함
    }

    @OnClick(R.id.BT_LikePost) void LikePost() {
        if (userLike) {
            Toast.makeText(this, "이미 Like 한 게시글입니다.", Toast.LENGTH_SHORT).show();
        } else {
            userLike = true;
            Toast.makeText(this, "Like! 감사합니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.BT_UnLikePost) void UnLikePost(){
        if (userLike) {
            userLike = false;
            Toast.makeText(this, "UnLike!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "UnLike!", Toast.LENGTH_SHORT).show();
        }
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
        switch (item.getItemId()) {
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
}

