package com.example.capstonedesignproject.view.ChabakJi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.FileDownloadTask;
import com.example.capstonedesignproject.Server.Task;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {

    static String memberID;
    static boolean like = false;
    TextView TV_ChabakjiTitle, TV_ChabakjiAddress, TV_ChabakjiAddress2;
    ViewGroup mapViewContainer;
    ImageButton BT_ChabakjiImage;
    Bitmap chabakjiImage;
    ChabakjiDAO chabakjiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 상태바 범위까지 사용하여 차박지 사진이 잘 보이도록!!
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // TODO 사용자 정보를 불러온 후 유저가 해당 차박지를 찜했으면 SunLike 메서드 실행
        Intent intent = getIntent();
        chabakjiData = (ChabakjiDAO) intent.getSerializableExtra("Chabakji");

        Init();

        TV_ChabakjiTitle.setText(chabakjiData.getPlace_name()); // 차박지 이름
        TV_ChabakjiAddress.setText(chabakjiData.getAddress()); // 차박지 주소
        TV_ChabakjiAddress2.setText(chabakjiData.getAddress()); // 차박지 주소
        BT_ChabakjiImage.setImageBitmap(chabakjiImage); // 차박지 사진

        // Map
        MapView mapView = new MapView(this);
        // 마커
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(chabakjiData.getLatitude(), chabakjiData.getLongitude());
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(chabakjiData.getPlace_name());
        marker.setTag(0);
        marker.setMapPoint(mapPoint);

        // 초기 BluePin
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때 RedPin
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    public void Init(){
        memberID = getIntent().getStringExtra("memberID");

        TV_ChabakjiTitle = findViewById(R.id.TV_ChabakjiTitle);
        TV_ChabakjiAddress = findViewById(R.id.TV_ChabakjiAddress);
        TV_ChabakjiAddress2 = findViewById(R.id.TV_ChabakjiAddress2);
        mapViewContainer = findViewById(R.id.mapView2);
        BT_ChabakjiImage = findViewById(R.id.BT_ChabakjiImage);

        chabakjiImage = getImage(chabakjiData.getFilePath());
    }

    public Bitmap getImage(String filePath){
        Bitmap image;
        try{
            image = new FileDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,filePath).get();
            return image;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void SunLike(View view) throws ExecutionException, InterruptedException {
        ImageButton sun = findViewById(R.id.BT_sun);
        if(like){
            like = false;
            sun.setImageResource(R.drawable.sun_white_24dp);
            // TODO DB에 저장
            String result = new Task().execute("member/jjim.undo", memberID, chabakjiData.getPlace_name()).get();
            Toast.makeText(this, result+ " " +memberID+" "+chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
        }else{
            like = true;
            sun.setImageResource(R.drawable.sun_yellow_24dp);
            // TODO DB에 저장
            String result = new Task().execute("member/jjim.do", memberID, chabakjiData.getPlace_name()).get();
            Toast.makeText(this, result+ " " +memberID+" "+chabakjiData.getPlace_name(), Toast.LENGTH_SHORT).show();
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
