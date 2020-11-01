package com.example.capstonedesignproject.view.ETC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.ChabakjiInfoTask;
import com.example.capstonedesignproject.view.Board.BoardFragment;
import com.example.capstonedesignproject.view.ChabakJi.HomeFragment;
import com.example.capstonedesignproject.view.Congestion.CongestionFragment;
import com.example.capstonedesignproject.view.Filter.RegionChoiceFragment;
import com.example.capstonedesignproject.view.MyPage.MyPageFragment;
import com.example.capstonedesignproject.view.Filter.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    private static final int SEARCH_REQUEST_CODE = 1;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private RegionChoiceFragment regionChoiceFragment = new RegionChoiceFragment();
    private CongestionFragment congestionFragment = new CongestionFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();

    public static String memberID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        memberID = intent.getStringExtra("memberID");

        // Toolbar
        final Toolbar mToolbar = findViewById(R.id.homeToolbar);
        mToolbar.setTitle("차박러 모여라");
        setSupportActionBar(mToolbar);

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_frame, homeFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home_menu: {
                        mToolbar.setTitle("차박러 모여라");
                        transaction.replace(R.id.fragment_frame, homeFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.list_menu: {
                        transaction.replace(R.id.fragment_frame, regionChoiceFragment).commitAllowingStateLoss();
                        mToolbar.setTitle("지역");
                        break;
                    }
                    case R.id.congestion_menu: {
                        transaction.replace(R.id.fragment_frame, congestionFragment).commitAllowingStateLoss();
                        mToolbar.setTitle("혼잡도");
                        break;
                    }
                    case R.id.board_menu: {
                        transaction.replace(R.id.fragment_frame, boardFragment).commitAllowingStateLoss();
                        mToolbar.setTitle("게시판");
                        break;
                    }
                    case R.id.myPage_menu: {
                        transaction.replace(R.id.fragment_frame, myPageFragment).commitAllowingStateLoss();
                        mToolbar.setTitle("마이페이지");
                        break;
                    }
                }
                return true;
            }
        });
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
                Intent intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SEARCH_REQUEST_CODE){
            if(data!=null){
                String search = data.getStringExtra("Search");
                String requestUrl = "";
                if (resultCode == 1)  requestUrl = "getAds.do";
                else if(resultCode==2) requestUrl = "getKey.do";
                else if(resultCode==3) requestUrl = "getBoard.do";

                Toast.makeText(this, requestUrl, Toast.LENGTH_SHORT).show();
                try {
                    HomeFragment.list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestUrl, search).get();
                    HomeFragment.myDataset = new ArrayList<>();
//                    HomeFragment.mAdapter.notifyDataSetChanged();
//                    HomeFragment.setChabakjiList();
                    HomeFragment.mAdapter.notifyDataSetChanged();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
