package com.example.capstonedesignproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.capstonedesignproject.R;

public class SearchActivity extends AppCompatActivity {

    Button BT_searchRegion, BT_searchKeyword, BT_searchBoard;
    Button BT_test1, BT_test2;
    ImageButton BT_searchExec;
    EditText ET_searchMessage;
    TableLayout TL_hotKeyword;

    String searchType = "Region"; // Default 검색타입

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Toolbar
        final Toolbar mToolbar = findViewById(R.id.Toolbar_search);
        mToolbar.setTitle("검색");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가

        // findViewById
        Init();

        // 버튼 클릭 리스너
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.BT_searchRegion:
                        ChangeColor(BT_searchRegion);
                        break;
                    case R.id.BT_searchKeyword:
                        ChangeColor(BT_searchKeyword);
                        break;
                    case R.id.BT_searchBoard:
                        ChangeColor(BT_searchBoard);
                        break;
                    case R.id.BT_searchExec:
                        // TODO 검색 결과를 어떻게 나타낼 지 구상해야 함..
                        // TODO 검색 수행 -> 결과 화면으로
                        String search = ET_searchMessage.getText().toString();
                        Toast.makeText(SearchActivity.this, searchType + "타입으로 " + search + " 검색됨", Toast.LENGTH_SHORT).show();

                        /*
                        if(searchType.equals("Region")){
                            Intent intent = new Intent(SearchActivity.this, ??);
                            startActivity(intent);
                        }else if(searchType.equals("Keyword")){
                            Intent intent = new Intent(SearchActivity.this, ??);
                            startActivity(intent);
                        }else if(searchType.equals("Board")){
                            Intent intent = new Intent(SearchActivity.this, ??);
                            startActivity(intent);
                        }
                        */
                        break;
                }
            }
        };

        BT_searchRegion.setOnClickListener(onClickListener);
        BT_searchKeyword.setOnClickListener(onClickListener);
        BT_searchBoard.setOnClickListener(onClickListener);
        BT_searchExec.setOnClickListener(onClickListener);

        // TODO TableLayout 에 인기 검색 키워드 버튼 동적으로 생성, 클릭리스너 구현해야함
    }

    public void Init() {
        BT_searchRegion = findViewById(R.id.BT_searchRegion);
        BT_searchKeyword = findViewById(R.id.BT_searchKeyword);
        BT_searchBoard = findViewById(R.id.BT_searchBoard);
        BT_searchExec = findViewById(R.id.BT_searchExec);
        BT_test1 = findViewById(R.id.BT_test1);
        BT_test2 = findViewById(R.id.BT_test2);
        ET_searchMessage = findViewById(R.id.ET_searchMessage);
        TL_hotKeyword = findViewById(R.id.TL_hotKeyword);
    }

    public void ChangeColor(View view) {
        switch (view.getId()) {
            case R.id.BT_searchRegion:
                BT_searchRegion.setBackgroundResource(R.drawable.button_border_green);
                BT_searchKeyword.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchBoard.setBackgroundResource(R.drawable.button_border_gray);
                searchType = "Region";
                break;
            case R.id.BT_searchKeyword:
                BT_searchRegion.setBackgroundResource(R.drawable.button_border_gray);
                BT_searchKeyword.setBackgroundResource(R.drawable.button_border_green);
                BT_searchBoard.setBackgroundResource(R.drawable.button_border_gray);
                searchType = "Keyword";
                break;
            case R.id.BT_searchBoard:
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
