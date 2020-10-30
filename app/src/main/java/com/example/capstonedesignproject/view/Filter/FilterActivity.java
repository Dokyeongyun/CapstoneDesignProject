package com.example.capstonedesignproject.view.Filter;

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

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;

public class FilterActivity extends AppCompatActivity {
    static final int optionNum = 5;

    Toolbar mToolbar;
    TextView TV_fishingSpot, TV_toilet, TV_convenience, TV_pay, TV_free, TV_refresh;
    Button BT_filterComplete;
    boolean[] isChecked = new boolean[optionNum];
    TextView[] optionArr = new TextView[optionNum];
    int[] checkedImage = new int[optionNum];
    int[] notCheckedImage = new int[optionNum];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Init();

        // 버튼 클릭 리스너
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.TV_fishingSpot:
                        CheckOption(0);
                        break;
                    case R.id.TV_toilet:
                        CheckOption(1);
                        break;
                    case R.id.TV_convenience:
                        CheckOption(2);
                        break;
                    case R.id.TV_pay:
                        CheckOption(3);
                        break;
                    case R.id.TV_free:
                        CheckOption(4);
                        break;
                    case R.id.TV_refresh:
                        isChecked = new boolean[optionNum];
                        for(int i=0; i<optionNum; i++){
                            isChecked[i] = true;
                            CheckOption(i);
                        }
                        break;
                    case R.id.BT_filterComplete:
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("FilterArr", isChecked);
                        setResult(1, intent);
                        finish();
                        break;
                }
            }
        };

        TV_fishingSpot.setOnClickListener(onClickListener);
        TV_toilet.setOnClickListener(onClickListener);
        TV_convenience.setOnClickListener(onClickListener);
        TV_pay.setOnClickListener(onClickListener);
        TV_free.setOnClickListener(onClickListener);
        TV_refresh.setOnClickListener(onClickListener);
        BT_filterComplete.setOnClickListener(onClickListener);
    }

    void Init(){
        // Toolbar
        mToolbar = findViewById(R.id.Toolbar_filter);
        mToolbar.setTitle("조건설정");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        TV_fishingSpot = findViewById(R.id.TV_fishingSpot);
        TV_toilet = findViewById(R.id.TV_toilet);
        TV_convenience = findViewById(R.id.TV_convenience);
        TV_pay = findViewById(R.id.TV_pay);
        TV_free = findViewById(R.id.TV_free);
        TV_refresh = findViewById(R.id.TV_refresh);
        BT_filterComplete = findViewById(R.id.BT_filterComplete);

        optionArr = new TextView[]{TV_fishingSpot, TV_toilet, TV_convenience, TV_pay, TV_free};
        checkedImage = new int[]{R.drawable.fishing_spot_blue,
                R.drawable.toilet_blue, R.drawable.convenience_blue,
                R.drawable.cash_red, R.drawable.cash_blue};
        notCheckedImage = new int[]{R.drawable.fishing_spot,
                R.drawable.toilet, R.drawable.convenience,
                R.drawable.cash_black, R.drawable.cash_black};
    }

    // 옵션 선택시 색상 변경
    void CheckOption(int index){
        if(isChecked[index]){
            optionArr[index].setCompoundDrawablesWithIntrinsicBounds(0, notCheckedImage[index],0,0);
            isChecked[index] = false;
        }else{
            optionArr[index].setCompoundDrawablesWithIntrinsicBounds(0, checkedImage[index],0,0);
            isChecked[index] = true;
        }
    }

    // 툴바에 메뉴 인플레이트
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_toolbar_menu, menu);
        return true;
    }

    // 툴바 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
