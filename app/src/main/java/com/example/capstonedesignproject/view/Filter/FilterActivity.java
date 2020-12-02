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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {
    static final int optionNum = 5;

    @BindView(R.id.TV_fishingSpot) TextView TV_fishingSpot;
    @BindView(R.id.TV_toilet) TextView TV_toilet;
    @BindView(R.id.TV_convenience) TextView TV_convenience;
    @BindView(R.id.TV_pay) TextView TV_pay;
    @BindView(R.id.TV_free) TextView TV_free;
    @BindView(R.id.TV_refresh) TextView TV_refresh;
    @BindView(R.id.Toolbar_filter) Toolbar mToolbar;
    @BindView(R.id.BT_filterComplete) Button BT_filterComplete;

    boolean[] isChecked = new boolean[optionNum];
    TextView[] optionArr = new TextView[optionNum];
    int[] checkedImage = new int[optionNum];
    int[] notCheckedImage = new int[optionNum];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        Init();
    }

    @OnClick(R.id.TV_toilet) void ClickToilet() { CheckOption(0); }
    @OnClick(R.id.TV_fishingSpot) void ClickFishingSpot() { CheckOption(1); }
    @OnClick(R.id.TV_convenience) void ClickConvenience() { CheckOption(2); }
    @OnClick(R.id.TV_pay) void ClickPay() { CheckOption(3); }
    @OnClick(R.id.TV_free) void ClickFree() { CheckOption(4); }
    @OnClick(R.id.TV_refresh) void ClickRefresh() {
        isChecked = new boolean[optionNum];
        for (int i = 0; i < optionNum; i++) {
            isChecked[i] = true;
            CheckOption(i);
        }
    }
    @OnClick(R.id.BT_filterComplete) void ClickFilterComplete() {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.putExtra("FilterArr", isChecked);
        setResult(1, intent);
        finish();
    }

    void Init(){
        // Toolbar
        mToolbar.setTitle("조건설정");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        optionArr = new TextView[]{TV_toilet, TV_fishingSpot, TV_convenience, TV_pay, TV_free};
        checkedImage = new int[]{R.drawable.toilet_blue, R.drawable.fishing_spot_blue, R.drawable.convenience_blue,
                R.drawable.cash_red, R.drawable.cash_blue};
        notCheckedImage = new int[]{R.drawable.toilet, R.drawable.fishing_spot, R.drawable.convenience,
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
