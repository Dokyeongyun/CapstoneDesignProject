package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // Toolbar
        Toolbar mToolbar = findViewById(R.id.listToolbar);
        mToolbar.setTitle("지역별");
        setSupportActionBar(mToolbar);

        // 선택된 지역 인텐트로부터 수신
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


        // TODO DB 에서 region 의 값에 해당되는 지역의 차박지 리스트 불러오기
    }

    // 툴바에 메뉴 인플레이트
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }

    // 툴바 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.app_bar_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
