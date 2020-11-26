package com.example.capstonedesignproject.view.Filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.BT_searchRegion) Button BT_searchRegion;
    @BindView(R.id.BT_searchKeyword) Button BT_searchKeyword;
    @BindView(R.id.BT_searchBoard) Button BT_searchBoard;
    @BindView(R.id.BT_searchExec) ImageButton BT_searchExec;
    @BindView(R.id.ET_searchMessage) EditText ET_searchMessage;
    @BindView(R.id.TL_hotKeyword) TableLayout TL_hotKeyword;
    @BindView(R.id.Toolbar_search) Toolbar mToolbar;

    String searchType = "Region"; // Default 검색타입

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Init();

        Intent intent = getIntent();
        if(intent.getStringExtra("FromMain") != null){
            searchType = intent.getStringExtra("FromMain");
            ChangeColor(searchType);
        }
        // TODO TableLayout 에 인기 검색 키워드 버튼 동적으로 생성, 클릭리스너 구현해야함
    }

    @OnClick(R.id.BT_searchRegion) void ClickSearchRegion(){ ChangeColor("Region"); }
    @OnClick(R.id.BT_searchKeyword) void ClickSearchKeyword(){ ChangeColor("Keyword"); }
    @OnClick(R.id.BT_searchBoard) void ClickSearchBoard(){ ChangeColor("Board"); }
    @OnClick(R.id.BT_searchExec) void ClickSearchExec(){
        String search = ET_searchMessage.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.putExtra("Search", search);
        intent.putExtra("Type", searchType);
        startActivity(intent);
    }

    private void Init(){
        // Toolbar
        mToolbar.setTitle("검색");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가
    }

    private void ChangeColor(String st) {
        switch (st) {
            case "Region":
                BT_searchRegion.setBackgroundResource(R.drawable.button_border_green);
                BT_searchKeyword.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchBoard.setBackgroundResource(R.drawable.button_border_gray);
                searchType = "Region";
                break;
            case "Keyword":
                BT_searchRegion.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchKeyword.setBackgroundResource(R.drawable.button_border_green);
                BT_searchBoard.setBackgroundResource(R.drawable.button_border_gray);
                searchType = "Keyword";
                break;
            case "Board":
                BT_searchRegion.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchKeyword.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchBoard.setBackgroundResource(R.drawable.button_border_green);
                searchType = "Board";
                break;
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
