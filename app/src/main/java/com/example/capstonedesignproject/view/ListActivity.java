package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();
        String region = intent.getStringExtra("Region");

        Toast.makeText(this, region, Toast.LENGTH_SHORT).show();

        // 지역선택 버튼에 현재 지역 setText
        Button regionChoice = findViewById(R.id.BT_regionChoice);
        regionChoice.setText(region);

        regionChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegionChoiceActivity.class);
                startActivity(intent);
            }
        });

        // 타이틀 변경
        TextView title = findViewById(R.id.TV_homeTitle);
        title.setText("지역별");



        // TODO DB 에서 region 의 값에 해당되는 지역의 차박지 리스트 불러오기
    }
}
