package com.example.capstonedesignproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.capstonedesignproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private RegionChoiceFragment regionChoiceFragment = new RegionChoiceFragment();
    private CongestionFragment congestionFragment = new CongestionFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_frame, homeFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home_menu: {
                        transaction.replace(R.id.fragment_frame, homeFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.list_menu: {
                        transaction.replace(R.id.fragment_frame, regionChoiceFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.congestion_menu: {
                        transaction.replace(R.id.fragment_frame, congestionFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.board_menu: {
                        transaction.replace(R.id.fragment_frame, boardFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.myPage_menu: {
                        transaction.replace(R.id.fragment_frame, myPageFragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });
    }
}
