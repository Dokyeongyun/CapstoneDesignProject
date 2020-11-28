package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.example.capstonedesignproject.view.Test.SetApplication;
import com.example.capstonedesignproject.view.Test.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {
    boolean like = false;
    @BindView(R.id.TV_ChabakjiTitle) TextView TV_ChabakjiTitle;
    @BindView(R.id.TV_ChabakjiAddress) TextView TV_ChabakjiAddress;
    @BindView(R.id.TV_ChabakjiAddress2) TextView TV_ChabakjiAddress2;
    @BindView(R.id.TV_ChabakjiToilet) TextView TV_ChabakjiToilet;
    @BindView(R.id.TV_ChabakjiCall) TextView TV_ChabakjiCall;
    @BindView(R.id.mapView2) ViewGroup mapViewContainer;
    @BindView(R.id.BT_ChabakjiImage) ImageButton BT_ChabakjiImage;
    @BindView(R.id.BT_sun) ImageButton sun;
    @BindView(R.id.RB_ratingBar) SimpleRatingBar RB_ratingBar;

    ChabakjiData chabakjiData;
    String userChabakInfo="";
    boolean startRating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // 상태바 범위까지 사용하여 차박지 사진이 잘 보이도록!!
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // TODO 사용자 정보를 불러온 후 유저가 해당 차박지를 찜했으면 SunLike 메서드 실행
        Intent intent = getIntent();
        chabakjiData = (ChabakjiData) intent.getSerializableExtra("Chabakji");

        TV_ChabakjiTitle.setText(chabakjiData.getPlace_name()); // 차박지 이름
        TV_ChabakjiAddress.setText(chabakjiData.getAddress()); // 차박지 주소
        TV_ChabakjiAddress2.setText(chabakjiData.getAddress()); // 차박지 주소
        TV_ChabakjiCall.setText(chabakjiData.getPhoneNumber()); // 차박지 전화번호

        List<Utils> utils = chabakjiData.getUtils();
        List<Utils> toiletUtils = new ArrayList<>();
        for(int i=0; i<utils.size(); i++){
            if(utils.get(i).getUtil().equals("1"))
                toiletUtils.add(utils.get(i));
        }

        if(toiletUtils.size()!=0){
            TV_ChabakjiToilet.setText("화장실 있음" + "("+toiletUtils.size()+"개)"); // 화장실 개수
        }else{
            TV_ChabakjiToilet.setVisibility(View.GONE);
        }

        String imageURL = chabakjiData.getFilePath();
        if(!chabakjiData.getFilePath().startsWith("http://")){
            imageURL = HomeActivity.SERVER_URL + "/" + chabakjiData.getFilePath();
        }

        Glide.with(this)
                .load(imageURL)
                .centerCrop()
                .placeholder(R.drawable.button_border_gray)
                .into(BT_ChabakjiImage);

        // Map
        MapView mapView = new MapView(this);
        // 마커
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(chabakjiData.getLatitude()), Double.parseDouble(chabakjiData.getLongitude()));
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(chabakjiData.getPlace_name());
        marker.setTag(0);
        marker.setMapPoint(mapPoint);

        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

        // 화장실 마커
        for(int i=0; i<toiletUtils.size(); i++){
            MapPoint toiletMapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(toiletUtils.get(i).getLat())
                    , Double.parseDouble(toiletUtils.get(i).getLng()));

            MapPOIItem toiletMarker = new MapPOIItem();
            toiletMarker.setItemName("화장실");
            toiletMarker.setTag(0);
            toiletMarker.setMapPoint(toiletMapPoint);

            toiletMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
            toiletMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(toiletMarker);
        }

        // 별점 평가하기 클릭 시
        RB_ratingBar.setOnRatingBarChangeListener((simpleRatingBar, rating, fromUser) -> {
            if(startRating){
                startRating = false;
            }else{
                Intent intent1 = new Intent(getApplicationContext(), RatingActivity.class);
                intent1.putExtra("ChabakjiData", chabakjiData);
                intent1.putExtra("Rating", RB_ratingBar.getRating());
                startActivity(intent1);
            }
        });

        // 현재 사용자가 해당 차박지를 찜했으면 찜 이미지 변경
        // 현재 사용자가 해당 차박지를 평가했으면 평가한 점수로 RatingBar 채우기
        setJJimAndEvaluated();
    }

    @OnClick(R.id.BT_sun) void SunLike() {
        try{
            if (like) {
                like = false;
                sun.setImageResource(R.drawable.sun_white_24dp);
                String result = new Task(this).execute("member/jjim.undo", HomeActivity.memberID, chabakjiData.getPlace_name(), chabakjiData.getId()).get();
                Toast.makeText(this, result + " " + HomeActivity.memberID + " " + chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
            } else {
                like = true;
                sun.setImageResource(R.drawable.sun_yellow_24dp);
                String result = new Task(this).execute("member/jjim.do", HomeActivity.memberID, chabakjiData.getPlace_name(), chabakjiData.getId()).get();
                Toast.makeText(this, result + " " + HomeActivity.memberID + " " + chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setJJimAndEvaluated(){
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().getJJimAndEvaluated(HomeActivity.memberID, chabakjiData.getId());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        userChabakInfo = s;
                    }
                    @Override
                    public void onError(Throwable e) { e.printStackTrace(); }
                    @Override
                    public void onCompleted() {
                        Toast.makeText(application, userChabakInfo, Toast.LENGTH_SHORT).show();
                        String[] split = userChabakInfo.split(" ");
                        if(split[0].equals("1")){
                            like = true;
                            sun.setImageResource(R.drawable.sun_yellow_24dp);
                        }
                        startRating = true;
                        RB_ratingBar.setRating(Float.parseFloat(split[1]));
                    }
                });
    }
    public void Back(View view) {
        finish();
    }

    public void Share(View view) {
        // TODO 해당 차박지 정보 공유하기
    }

    public void showTotalImage(View view) {
        // TODO 해당 차박지 사진 앨범 보기
    }
}
