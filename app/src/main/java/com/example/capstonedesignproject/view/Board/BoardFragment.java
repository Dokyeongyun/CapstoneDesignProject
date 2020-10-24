package com.example.capstonedesignproject.view.Board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.FileDownloadTask;
import com.example.capstonedesignproject.Server.GetArticleTask;
import com.google.android.material.tabs.TabLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BoardFragment extends Fragment {
    private LinearLayout LL_board, LL_notification;
    private static ArrayList<ArticleData> postList = new ArrayList<>();
    private static boolean check = false;
    static Bitmap[] imageArr;
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
        TabLayout tabLayout = v.findViewById(R.id.boardTabLayout);
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
        if (!check) {
            try {
                Init(); // 초기 한번만 실행되도록
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
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
                intent.putExtra("articleID", postList.get(position).getArticleId());
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
    private void changeView(int pos) {
        switch (pos) {
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
    private static void Init() throws ExecutionException, InterruptedException {
        check = true;
        getArticleList(1);
/*
        postList.add(new ArticleData("10월 4일 20:49", "도경윤", "차박지 정보 공유 게시판입니다", "게시판 글 내용", R.drawable.pic1));
        postList.add(new ArticleData("10월 5일 21:15", "도112", "오늘 저녁 메뉴는?", "레오펍", R.drawable.pic2));
        postList.add(new ArticleData("10월 6일 22:49", "도경윤", "내일 무슨 요일?", "화요일", R.drawable.pic3));
        postList.add(new ArticleData("10월 7일 23:59", "도경윤", "오늘 하루 공부한 게 없다", "ㅋㅋㅋㅋㅋ", R.drawable.pic4));
*/
    }

    private static void getArticleList(int num) throws ExecutionException, InterruptedException {
        List<ArticleData> articleData = new GetArticleTask().execute("article/get.do", num).get();

        // 차박지 사진 filePath 에 접근하여 파일을 Bitmap 으로 가져오기
        imageArr = new Bitmap[articleData.size()];
        for(int i=0; i<imageArr.length; i++){
            String filePath = articleData.get(i).getUrlPath();
            System.out.println(filePath);
            try{
                imageArr[i] = new FileDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,filePath).get();
                for(int j=0; j<imageArr.length; j++){
                    if(imageArr[j]!=null){
                        articleData.get(j).setImage(imageArr[j]);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        postList.addAll(articleData);
    }

/*    // DB 삽입대신 테스트하기 위해 작성함
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {

                String title = data.getStringExtra("title");
                String content = data.getStringExtra("content");
                String writer = data.getStringExtra("writer");
                long date = data.getLongExtra("date", 0);
                int picture = data.getIntExtra("picture", 0);
                SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 HH:mm");
                String stringDate = sdf.format(new Date(date));

               // postList.add(new ArticleData(stringDate, writer, title, content, picture));
            }
        }
    }*/
}
