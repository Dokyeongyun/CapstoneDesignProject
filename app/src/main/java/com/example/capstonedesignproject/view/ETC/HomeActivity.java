package com.example.capstonedesignproject.view.ETC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.ChabakjiInfoTask;
import com.example.capstonedesignproject.view.Board.BoardFragment;
import com.example.capstonedesignproject.view.ChabakJi.HomeFragment;
import com.example.capstonedesignproject.view.Congestion.CongestionFragment;
import com.example.capstonedesignproject.view.Filter.RegionChoiceFragment;
import com.example.capstonedesignproject.view.MyPage.MyPageFragment;
import com.example.capstonedesignproject.view.Filter.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    public static final String SERVER_IP = "http://211.222.234.14:";
    public static final String PORT_NUMBER = "8080";
    public static String SERVER_URL = SERVER_IP + PORT_NUMBER;

    @BindView(R.id.homeToolbar) Toolbar mToolbar;
    @BindView(R.id.bottomNavigationView) BottomNavigationView bottomNavigationView;
    private long backKeyPressedTime = 0;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private RegionChoiceFragment regionChoiceFragment = new RegionChoiceFragment();
    private CongestionFragment congestionFragment = new CongestionFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();
    private MainFragment mainFragment = new MainFragment();

    public static String memberID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        memberID = intent.getStringExtra("memberID");

        // Toolbar
        mToolbar.setTitle("차박러 모여라");
        setSupportActionBar(mToolbar);

        // BottomNavigationView
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_frame, mainFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_menu: {
                    replaceFragment(mainFragment, "차박러 모여라");
                    break;
                }
                case R.id.list_menu: {
                    replaceFragment(regionChoiceFragment, "지역선택");
                    break;
                }
                case R.id.congestion_menu: {
                    replaceFragment(congestionFragment, "혼잡도");
                    break;
                }
                case R.id.board_menu: {
                    replaceFragment(boardFragment, "게시판");
                    break;
                }
                case R.id.myPage_menu: {
                    replaceFragment(myPageFragment, "마이페이지");
                    break;
                }
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment, String toolbarTitle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fragment).commit();
        mToolbar.setTitle(toolbarTitle);
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
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}
