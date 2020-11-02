package com.example.capstonedesignproject.view.ChabakJi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.ChabakjiInfoTask;
import com.example.capstonedesignproject.view.Filter.NoResultActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.listToolbar) Toolbar mToolbar;
    @BindView(R.id.BT_regionChoice) Button regionChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);
        Init();

        Intent intent = getIntent();
        String search = intent.getStringExtra("Search");
        String[] regionArr = new String[]{search};
        String requestUrl = "";
        if (Objects.equals(intent.getStringExtra("Type"), "Region"))  {
            requestUrl = "getAds.do";
            regionChoice.setText(search);

            if(intent.getStringExtra("Region")!=null){
                String region = intent.getStringExtra("Region");
                regionArr = region.split("/");
                regionChoice.setText(region);
            }
        }
        else if(Objects.equals(intent.getStringExtra("Type"), "Keyword")) requestUrl = "getKey.do";
        else if(Objects.equals(intent.getStringExtra("Type"), "Board")) requestUrl = "getBoard.do";

        try {
            List<ChabakjiDAO> list = new ArrayList<>();
            if(requestUrl.equals("getAds.do")){
                for (String s : regionArr) {
                    list.addAll(new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestUrl, s).get());
                }
            }else{
                list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestUrl, search).get();
            }
            if(list == null || list.size()==0){
                Intent intent1 = new Intent(this, NoResultActivity.class);
                startActivityForResult(intent1, 1);
            }else{
                HomeFragment.myDataset.clear();
                HomeFragment.setChabakjiList(list);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Init(){
        // Toolbar
        mToolbar.setTitle("검색결과");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바에 뒤로가기버튼 추가
    }

    @OnClick(R.id.BT_regionChoice)
    void ChoiceRegion(){
        Intent intent = new Intent(getApplicationContext(), RegionChoiceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==1){
                finish();
            }
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
