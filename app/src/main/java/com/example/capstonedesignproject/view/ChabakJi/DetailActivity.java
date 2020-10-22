package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    static boolean like = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 상태바 범위까지 사용하여 차박지 사진이 잘 보이도록!!
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // TODO 사용자 정보를 불러온 후 유저가 해당 차박지를 찜했으면 SunLike 메서드 실행
        Intent intent = getIntent();
        ChabakjiDAO chabakjiData = (ChabakjiDAO) intent.getSerializableExtra("Chabakji");

        // Map
        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = findViewById(R.id.mapView2);

        // 마커
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(chabakjiData.getLatitude(), chabakjiData.getLongitude()); // TODO 차박지 경위도
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("의림지"); // TODO 차박지 이름
        marker.setTag(0);
        marker.setMapPoint(mapPoint);

        // 초기 BluePin
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때 RedPin
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    public void SunLike(View view) {
        ImageButton sun = findViewById(R.id.BT_sun);
        if(like){
            like = false;
            sun.setImageResource(R.drawable.sun_yellow_24dp);
            // TODO DB에 저장
        }else{
            like = true;
            sun.setImageResource(R.drawable.sun_white_24dp);
            // TODO DB에 저장
        }
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
