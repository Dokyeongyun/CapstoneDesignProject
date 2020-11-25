package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RatingActivity extends AppCompatActivity {
    @BindView(R.id.RB_ratingBar2) SimpleRatingBar RB_ratingBar2;
    @BindView(R.id.TV_chabakjiName) TextView TV_chabakjiName;
    @BindView(R.id.BT_ratingComplete) Button BT_ratingComplete;
    @BindView(R.id.BT_back_rating) ImageButton BT_back_rating;

    ChabakjiData chabakjiData;
    float chabakjiRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);

        Intent intent = getIntent();


        // 차박지 이름
        chabakjiData = (ChabakjiData) intent.getSerializableExtra("ChabakjiData");
        if(chabakjiData!=null){
            TV_chabakjiName.setText("["+chabakjiData.getPlace_name()+"]");
        }

        // 사용자가 입력한 별점 가져와 초기세팅
        float inputRating = intent.getFloatExtra("Rating", 0);
        RB_ratingBar2.setRating(inputRating);
        chabakjiRating = inputRating;
        RB_ratingBar2.setOnRatingBarChangeListener((simpleRatingBar, rating, fromUser) -> chabakjiRating = rating);
    }

    @OnClick(R.id.BT_ratingComplete) void ratingComplete(){
        Toast.makeText(this, chabakjiRating+"", Toast.LENGTH_SHORT).show();
        // DB에 평가정보 삽입 [ 사용자ID, 차박지ID, 차박지이름 , 평가점수 ]
        String result = "";
        try{
            result = new Task(this).execute("chabak/eval.do", HomeActivity.memberID,
                    chabakjiData.getId(), chabakjiData.getPlace_name(), String.valueOf(chabakjiRating)).get();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(result!=null && result.equals("true")){
                Toast.makeText(this, "평가에 참여해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.BT_back_rating) void Back(){ finish(); }
}
