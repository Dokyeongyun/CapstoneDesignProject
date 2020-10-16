package com.example.capstonedesignproject.view.Board;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.capstonedesignproject.Adapter.BoardAdapter;
import com.example.capstonedesignproject.Data.PostData;
import com.example.capstonedesignproject.R;
import com.google.android.material.tabs.TabLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BoardFragment extends Fragment {
    static LinearLayout LL_board, LL_notification;
    static ArrayList<PostData> postList = new ArrayList<>();
    static boolean check = false;

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

        // TabLayout
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
        if(!check){
            Init(); // 초기 한번만 실행되도록
        }
        // 리스트뷰에 어댑터 설정
        ListView boardListView = v.findViewById(R.id.LV_board);
        final BoardAdapter boardAdapter = new BoardAdapter(getContext(), postList);
        boardListView.setAdapter(boardAdapter);

        // 리스트뷰 클릭 리스너
        boardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 해당 게시글 정보 넣어 ShowPostActivity start
                Intent intent = new Intent(getActivity(), ShowPostActivity.class);
                intent. putExtra("postWriter", postList.get(position).getWriter());
                intent. putExtra("postID", postList.get(position).getpID());
                intent. putExtra("postTitle", postList.get(position).getTitle());
                intent. putExtra("postPicture", postList.get(position).getPicture());
                intent. putExtra("postDate", postList.get(position).getDate());
                intent. putExtra("postContent", postList.get(position).getContent());
                startActivity(intent);
            }
        });

        // FAB 클릭 리스너
        FloatingActionButton fab = v.findViewById(R.id.FAB_writePost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WritePostActivity.class);
                startActivityForResult(intent, 1);
            }
        });

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
    private static void Init(){
        check = true;
        postList.add(new PostData("10월 4일 20:49", "도경윤", "차박지 정보 공유 게시판입니다", "게시판 글 내용", R.drawable.pic1));
        postList.add(new PostData("10월 5일 21:15", "도112", "오늘 저녁 메뉴는?", "레오펍", R.drawable.pic2));
        postList.add(new PostData("10월 6일 22:49", "도경윤", "내일 무슨 요일?", "화요일", R.drawable.pic3));
        postList.add(new PostData("10월 7일 23:59", "도경윤", "오늘 하루 공부한 게 없다", "ㅋㅋㅋㅋㅋ", R.drawable.pic4));
    }

    // DB 삽입대신 테스트하기 위해 작성함
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==1){

                String title = data.getStringExtra("title");
                String content = data.getStringExtra("content");
                String writer = data.getStringExtra("writer");
                long date = data.getLongExtra("date", 0);
                int picture = data.getIntExtra("picture", 0);
                SimpleDateFormat sdf = new SimpleDateFormat ( "MM월 dd일 HH:mm");
                String stringDate = sdf.format(new Date(date));

                postList.add(new PostData(stringDate, writer, title, content, picture));
            }
        }
    }
}
