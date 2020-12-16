package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.Adapter.ReviewAdapter;
import com.example.capstonedesignproject.VO.FishingVO;
import com.example.capstonedesignproject.VO.ReviewVO;
import com.example.capstonedesignproject.VO.ToiletVO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.Task;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.VO.ChabakjiVO;
import com.example.capstonedesignproject.Server.SetApplication;
import com.google.android.material.snackbar.Snackbar;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.TV_ChabakjiTitle) TextView TV_ChabakjiTitle;
    @BindView(R.id.TV_ChabakjiAddress) TextView TV_ChabakjiAddress;
    @BindView(R.id.TV_ChabakjiAddress2) TextView TV_ChabakjiAddress2;
    @BindView(R.id.TV_ChabakjiToilet) TextView TV_ChabakjiToilet;
    @BindView(R.id.TV_ChabakjiFishing) TextView TV_ChabakjiFishing;
    @BindView(R.id.TV_ChabakjiCall) TextView TV_ChabakjiCall;
    @BindView(R.id.TV_reviewCount) TextView TV_reviewCount;
    @BindView(R.id.TV_rating) TextView TV_rating;
    @BindView(R.id.mapView2) ViewGroup mapViewContainer;
    @BindView(R.id.BT_ChabakjiImage) ImageButton BT_ChabakjiImage;
    @BindView(R.id.BT_sun) ImageButton sun;
    @BindView(R.id.RB_ratingBar) SimpleRatingBar RB_ratingBar;
    @BindView(R.id.RV_reviews) RecyclerView RV_reviews;
    @BindView(R.id.snackContainer) ConstraintLayout snackContainer;

    private boolean like = false;
    private ReviewAdapter reviewAdapter;
    private ChabakjiVO chabakjiData;
    private String userChabakInfo="";
    private boolean startRating = false;
    private Map<String, Integer> utils;
    private List<ToiletVO> toiletList = new ArrayList<>();
    private List<FishingVO> fishingList = new ArrayList<>();
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Init(); // 초기설정
        Binding(); // Intent로 전달된 데이터 뷰에 바인딩
        setMap(); // 지도 관련 설정
        setJJimAndEvaluated(); // 사용자가 차박지를 평가, 찜했으면 초기 값 변경
        getReviews(); // 차박지 리뷰 읽어오기
        getToilets(); // 화장실 데이터 읽어오기
        getFishings(); // 낚시터 데이터 읽어오기

        // RB_ratingBar 클릭 리스너
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
    }

    /**
     * 찜 / 찜 취소
     */
    @OnClick(R.id.BT_sun) void SunLike() {
        String result = "";
        try{
            if (like) {
                result = new Task(this).execute("member/jjim.undo", HomeActivity.memberID, chabakjiData.getPlaceName(), String.valueOf(chabakjiData.getPlaceId())).get();
            } else {
                result = new Task(this).execute("member/jjim.do", HomeActivity.memberID, chabakjiData.getPlaceName(), String.valueOf(chabakjiData.getPlaceId())).get();
            }
        }catch (Exception e){ e.printStackTrace(); }
        if(!result.equals("\"false\"")){
            if(like){
                like = false;
                sun.setImageResource(R.drawable.sun_white_24dp);
            }else{
                like = true;
                sun.setImageResource(R.drawable.sun_yellow_24dp);
            }
        }else{
            Toast.makeText(this, "잠시 후 다시 시도해주세요..ㅠㅠ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 리싸이클러뷰 등 초기 설정
     */
    private void Init(){
        // 상태바 범위까지 사용하여 차박지 사진이 잘 보이도록!!
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        RV_reviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewAdapter = new ReviewAdapter();
        RV_reviews.setHasFixedSize(true);
        RV_reviews.setAdapter(reviewAdapter);
    }

    /**
     * 인텐트로 전달된 데이터 수신 후 뷰에 바인딩하기
     */
    private void Binding(){
        // 인텐트로 전달된 데이터 받기
        Intent intent = getIntent();
        chabakjiData = (ChabakjiVO) intent.getSerializableExtra("Chabakji");

        // 데이터 바인딩
        TV_ChabakjiTitle.setText(chabakjiData.getPlaceName()); // 차박지 이름
        TV_ChabakjiAddress.setText(chabakjiData.getAddress()); // 차박지 주소
        TV_ChabakjiAddress2.setText(chabakjiData.getAddress()); // 차박지 주소2
        TV_ChabakjiCall.setText(chabakjiData.getPhone_number()); // 차박지 전화번호
        TV_rating.setText(String.valueOf(chabakjiData.getAvg_point()));
        utils = chabakjiData.getUtilityCount(); // 차박지 주변시설 (화장실, 낚시터)

        int toiletCount = utils.get("toilet");
        int fishingCount = utils.get("fishing");
        if(toiletCount!=0){
            TV_ChabakjiToilet.setVisibility(View.VISIBLE);
            TV_ChabakjiToilet.setText("화장실 있음" + "("+toiletCount+"개)"); // 화장실 개수
        }
        if(fishingCount!=0){
            TV_ChabakjiFishing.setVisibility(View.VISIBLE);
            TV_ChabakjiFishing.setText("낚시터 있음" + "("+fishingCount+"개)"); // 화장실 개수
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
    }

    /**
     * 지도 관련 설정
     */
    private void setMap(){
        // Map
        mapView = new MapView(this);
        // 마커
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(chabakjiData.getLatitude(), chabakjiData.getLongitude());
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(chabakjiData.getPlaceName());
        marker.setTag(0);
        marker.setMapPoint(mapPoint);

        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    /**
     * 현재 사용자가 해당 차박지를 찜했으면 찜 이미지 변경
     * 현재 사용자가 해당 차박지를 평가했으면 평가한 점수로 RatingBar 채우기
     */
    public void setJJimAndEvaluated(){
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<String> observable = application.getMemberService().getJJimAndEvaluated(HomeActivity.memberID, String.valueOf(chabakjiData.getPlaceId()));
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
                        String[] split = userChabakInfo.split(" ");
                        if(split[0].equals("1")){
                            like = true;
                            sun.setImageResource(R.drawable.sun_yellow_24dp);
                        }
                        startRating = true;
                        if(!split[1].equals("Error")){
                            RB_ratingBar.setRating(Float.parseFloat(split[1]));
                        }
                    }
                });
    }

    /**
     * 해당 차박지에 등록된 리뷰 읽어오기
     */
    public void getReviews(){
        reviewAdapter.clear();
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<List<ReviewVO>> observable = application.getChabakjiService().getReviews(chabakjiData.getPlaceId());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ReviewVO>>() {
                    @Override
                    public void onNext(List<ReviewVO> items) {
                        for(int i=0; i<items.size(); i++){
                            reviewAdapter.setItemsAndRefresh(items.get(i));
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "리뷰 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    @Override
                    public void onCompleted() {
                        TV_reviewCount.setText(reviewAdapter.getItemCount() + "개의 후기");
                    }
                });
    }

    /**
     * 해당 차박지 근처의 화장실 데이터 읽어오기
     */
    public void getToilets(){
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<List<ToiletVO>> observable = application.getChabakjiService().getToilets(chabakjiData.getPlaceId());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ToiletVO>>() {
                    @Override
                    public void onNext(List<ToiletVO> items) {
                        toiletList.addAll(items);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "화장실 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    @Override
                    public void onCompleted() {
                        setToiletMarker(); // 지도 관련 설정
                    }
                });
    }

    /**
     * 해당 차박지 근처의 낚시터 데이터 읽어오기
     */
    public void getFishings(){
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<List<FishingVO>> observable = application.getChabakjiService().getFishings(chabakjiData.getPlaceId());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FishingVO>>() {
                    @Override
                    public void onNext(List<FishingVO> items) {
                        fishingList.addAll(items);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "낚시터 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    @Override
                    public void onCompleted() {
                        setFishingMarker(); // 지도 관련 설정
                    }
                });
    }

    /**
     * 화장실 데이터 모두 불러온 후 화장실 마커 설정
     */
    private void setToiletMarker(){
        // 화장실 마커
        for(int i=0; i<toiletList.size(); i++){
            MapPoint toiletMapPoint =
                    MapPoint.mapPointWithGeoCoord(toiletList.get(i).getLatitude(), toiletList.get(i).getLongitude());

            MapPOIItem toiletMarker = new MapPOIItem();
            toiletMarker.setItemName(toiletList.get(i).getAddress());
            toiletMarker.setTag(0);
            toiletMarker.setMapPoint(toiletMapPoint);

            toiletMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
            toiletMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(toiletMarker);
        }
    }

    /**
     * 낚시터 데이터 모두 불러온 후 낚시터 마커 설정
     */
    private void setFishingMarker() {
        // 낚시터 마커
        for(int i=0; i<fishingList.size(); i++){
            MapPoint toiletMapPoint =
                    MapPoint.mapPointWithGeoCoord(fishingList.get(i).getLatitude(), fishingList.get(i).getLongitude());

            MapPOIItem toiletMarker = new MapPOIItem();
            toiletMarker.setItemName(fishingList.get(i).getName());
            toiletMarker.setTag(0);
            toiletMarker.setMapPoint(toiletMapPoint);

            toiletMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
            toiletMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(toiletMarker);
        }
    }

    /**
     * 뒤로 가기
     */
    public void Back(View view) { finish(); }

    public void Share(View view) {
        // TODO 해당 차박지 정보 공유하기
    }

    public void showTotalImage(View view) {
        // TODO 해당 차박지 사진 앨범 보기
    }
}
