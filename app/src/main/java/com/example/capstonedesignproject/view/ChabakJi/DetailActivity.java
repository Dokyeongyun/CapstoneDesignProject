package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    static boolean like = false;
    @BindView(R.id.TV_ChabakjiTitle) TextView TV_ChabakjiTitle;
    @BindView(R.id.TV_ChabakjiAddress) TextView TV_ChabakjiAddress;
    @BindView(R.id.TV_ChabakjiAddress2) TextView TV_ChabakjiAddress2;
    @BindView(R.id.mapView2) ViewGroup mapViewContainer;
    @BindView(R.id.BT_ChabakjiImage) ImageButton BT_ChabakjiImage;
    @BindView(R.id.BT_sun) ImageButton sun;

    ChabakjiData chabakjiData;

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
        Glide.with(this)
                .load("http://211.222.234.14:8080/"+chabakjiData.getFilePath())
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
    }

    @OnClick(R.id.BT_sun) void SunLike() {
        try{
            if (like) {
                like = false;
                sun.setImageResource(R.drawable.sun_white_24dp);
                String result = new Task().execute("member/jjim.undo", HomeActivity.memberID, chabakjiData.getPlace_name()).get();
                Toast.makeText(this, result + " " + HomeActivity.memberID + " " + chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
            } else {
                like = true;
                sun.setImageResource(R.drawable.sun_yellow_24dp);
                String result = new Task().execute("member/jjim.do", HomeActivity.memberID, chabakjiData.getPlace_name()).get();
                Toast.makeText(this, result + " " + HomeActivity.memberID + " " + chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
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
