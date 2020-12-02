package com.example.capstonedesignproject.view.ChabakJi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Filter.FilterActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiAdapter_Large;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.example.capstonedesignproject.view.Test.SetApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity {
    private static final int FILTER_REQUEST_CODE = 1;

    @BindView(R.id.RV_chabakList) RecyclerView RV_chabakList;
    @BindView(R.id.listToolbar) Toolbar mToolbar;
    @BindView(R.id.BT_regionChoice) Button BT_regionChoice;
    @BindView(R.id.BT_setFilter) Button BT_setFilter;
    @BindView(R.id.PB_chabakList) ProgressBar PB_chabakList;
    @BindView(R.id.CL_snackContainer_list) ConstraintLayout CL_snackContainer_list;
    @BindView(R.id.LL_noResult) LinearLayout LL_noResult;
    @BindView(R.id.LL_searchResult) LinearLayout LL_searchResult;
    @BindView(R.id.BT_research) Button BT_research;
    @BindView(R.id.BT_moveToHome) Button BT_moveToHome;

    private ChabakjiAdapter_Large chabakjiAdapter;
    String region = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String search = intent.getStringExtra("Search");
        String requestUrl = "";
        region = search;

        Init(search);

        // '지역검색'을 통해 넘어왔거나 '지역선택'을 통해서 넘어온 경우
        if (Objects.equals(intent.getStringExtra("Type"), "Region"))  {
            requestUrl = "getAds.do";
            BT_regionChoice.setText(search);

            if(intent.getStringExtra("Region")!=null){
                region = intent.getStringExtra("Region");
                mToolbar.setTitle("\"" + region + "\" 검색결과");
                setSupportActionBar(mToolbar);
                BT_regionChoice.setText(region);
            }
        } else if(Objects.equals(intent.getStringExtra("Type"), "Keyword")){
            requestUrl = "getKey.do";
            BT_regionChoice.setVisibility(View.GONE);
        }


        // TODO 필터 조건만 취득한 후에, HomeFragment 의 load() 메서드의 인자로 넘겨주어 필터링된 차박지만 가져오도록 수정하기
        if(requestUrl.equals("getAds.do")){
            load("getAds.do", region, "F/F");
        }else if(requestUrl.equals("getKey.do")){
            load("getKey.do", search, "F/F");
        }

        // CardView 아이템 클릭 리스너
        RV_chabakList.addOnItemTouchListener(new RecyclerTouchListener(this, RV_chabakList, (view, position) -> {
            Intent intent1 = new Intent(this, DetailActivity.class);
            intent1.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
            startActivity(intent1);
        }));
    }

    private void Init(String search){
        // Toolbar
        mToolbar.setTitle("\"" + search + "\" 검색결과");
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Recycler View
        RV_chabakList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chabakjiAdapter = new ChabakjiAdapter_Large(this);
        RV_chabakList.setHasFixedSize(true);
        RV_chabakList.setAdapter(chabakjiAdapter);
    }

    private void load(String requestUrl, String addressOrKeyword, String filter){
        chabakjiAdapter.clear();
        PB_chabakList.setVisibility(View.VISIBLE);
        LL_noResult.setVisibility(View.GONE);
        LL_searchResult.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();

        Observable<List<ChabakjiData>> observable = null;
        if(requestUrl.equals("getAds.do")){
            observable = application.getChabakjiService().filter(addressOrKeyword, filter);
        }else if(requestUrl.equals("getKey.do")){
            observable = application.getChabakjiService().getKey(addressOrKeyword);
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ChabakjiData>>() {
                    @Override
                    public void onNext(List<com.example.capstonedesignproject.view.Test.ChabakjiData> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            chabakjiAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_chabakList.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(CL_snackContainer_list, "차박지 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_chabakList.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() {
                        if(chabakjiAdapter.getItemCount()==0){
                            LL_searchResult.setVisibility(View.GONE);
                            LL_noResult.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @OnClick(R.id.BT_research) void ClickResearch(){
        setResult(1);
        finish();
    }
    @OnClick(R.id.BT_moveToHome) void ClickMoveToHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.BT_regionChoice)
    void ChoiceRegion(){
        finish();
    }

    @OnClick(R.id.BT_setFilter) void ClickSetFilter(){
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, FILTER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTER_REQUEST_CODE){
            if(resultCode == 1){
                if(data!=null){
                    StringBuilder flags = new StringBuilder();
                    boolean[] filterArr = data.getBooleanArrayExtra("FilterArr");
                    for (int i=0; i<2; i++) {
                        if(filterArr[i]){ flags.append("T/"); }else{ flags.append("F/"); }
                    }
                    Toast.makeText(this, flags, Toast.LENGTH_SHORT).show();

                    // TODO 선택된 조건을 이용하여 검색 수행 및 결과 띄우기
                    load("getAds.do", region, flags.toString());
                }
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
