package com.example.capstonedesignproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.capstonedesignproject.Adapter.BoardAdapter;
import com.example.capstonedesignproject.Data.PostData;
import com.example.capstonedesignproject.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BoardFragment extends Fragment {
    static LinearLayout LL_board, LL_notification;
    static ArrayList<PostData> postList = new ArrayList<>();

    public BoardFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        TabLayout tabLayout = v.findViewById(R.id.boardTabLayout) ;
        LL_board = v.findViewById(R.id.LL_board);
        LL_notification = v.findViewById(R.id.LL_notification);

        // 탭 클릭 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
                // TODO : tab이 선택되지 않았다가 선택될 때 호출
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab이 선택되었다가 다른 탭을 선택할 때 호출
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시 선택될 때 호출
            }
        });

        // 샘플 데이터
        Init();
        // 리스트뷰에 어댑터 설정
        ListView boardListView = v.findViewById(R.id.LV_board);
        final BoardAdapter boardAdapter = new BoardAdapter(getContext(), postList);
        boardListView.setAdapter(boardAdapter);

        return v;
    }

    /* 탭 클릭 시 FrameLayout VISIBLE/INVISIBLE */
    private void changeView(int pos){
        switch(pos){
            case 0:
                LL_board.setVisibility(View.VISIBLE);
                LL_notification.setVisibility(View.INVISIBLE);
                break;
            case 1:
                LL_board.setVisibility(View.INVISIBLE);
                LL_notification.setVisibility(View.VISIBLE);
                break;
        }
    }

    /* 게시글 리스트에 (샘플) 데이터 삽입 */
    static void Init(){
        postList.add(new PostData("10월 4일 20:49", "도경윤", "차박지 정보 공유 게시판입니다", "게시판 글 내용", R.drawable.pic1));
        postList.add(new PostData("10월 5일 21:15", "도112", "오늘 저녁 메뉴는?", "레오펍", R.drawable.pic2));
        postList.add(new PostData("10월 6일 22:49", "도경윤", "내일 무슨 요일?", "화요일", R.drawable.pic3));
        postList.add(new PostData("10월 7일 23:59", "도경윤", "오늘 하루 공부한 게 없다", "ㅋㅋㅋㅋㅋ", R.drawable.pic4));
    }
}
