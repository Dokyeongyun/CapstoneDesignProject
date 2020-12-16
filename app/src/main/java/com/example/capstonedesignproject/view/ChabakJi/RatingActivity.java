package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.VO.ChabakjiVO;
import com.example.capstonedesignproject.Server.SetApplication;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RatingActivity extends AppCompatActivity {
    @BindView(R.id.RB_ratingBar2) SimpleRatingBar RB_ratingBar2;
    @BindView(R.id.TV_chabakjiName) TextView TV_chabakjiName;
    @BindView(R.id.BT_ratingComplete) Button BT_ratingComplete;
    @BindView(R.id.BT_back_rating) ImageButton BT_back_rating;
    @BindView(R.id.ET_review) EditText ET_review;
    @BindView(R.id.PB_rating) ProgressBar PB_rating;

    ChabakjiVO chabakjiData;
    float chabakjiRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        // 차박지 이름
        chabakjiData = (ChabakjiVO) intent.getSerializableExtra("ChabakjiData");
        if(chabakjiData!=null){
            TV_chabakjiName.setText("["+chabakjiData.getPlaceName()+"]");
        }

        // 사용자가 입력한 별점 가져와 초기세팅
        float inputRating = intent.getFloatExtra("Rating", 0);
        RB_ratingBar2.setRating(inputRating);
        chabakjiRating = inputRating;
        RB_ratingBar2.setOnRatingBarChangeListener((simpleRatingBar, rating, fromUser) -> chabakjiRating = rating);
    }

    /**
     * 별점 및 리뷰 작성
     */
    @OnClick(R.id.BT_ratingComplete) void ratingComplete(){
        String review = ET_review.getText().toString().trim();
        if(review.equals("")){
            Toast.makeText(this, "리뷰를 작성해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // DB에 평가정보 삽입 [ 사용자ID, 차박지ID, 차박지이름 , 평가점수, 리뷰내용 ]
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getChabakjiService().eval(HomeActivity.memberID, chabakjiData.getPlaceId(),
                chabakjiData.getPlaceName(), chabakjiRating, review);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { PB_rating.setVisibility(View.VISIBLE); }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "리뷰 등록에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        Toast.makeText(application, "평가에 참여해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @OnClick(R.id.BT_back_rating) void Back(){ finish(); }
}
