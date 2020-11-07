package com.example.capstonedesignproject.view.Board;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.BoardAdapter;
import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class BoardFragment extends Fragment {
    @BindView(R.id.LL_board) LinearLayout LL_board;
    @BindView(R.id.LL_notification) LinearLayout LL_notification;
    @BindView(R.id.TL_board) TabLayout TL_board;
    @BindView(R.id.FAB_writePost) FloatingActionButton FAB_writePost;
    @BindView(R.id.LV_board) ListView LV_board;
    @BindView(R.id.LV_notification) ListView LV_notification;

    private Context context;
    private static ArrayList<ArticleData> postList;

    public BoardFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, v);
        Init();

        // 탭 클릭 리스너
        TL_board.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        try {
            getArticleList(1);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return v;
    }

    @OnItemClick(R.id.LV_board) void ClickPost(AdapterView<?> parent, View view, int position, long id){
        Intent intent = new Intent(getActivity(), ShowPostActivity.class);
        intent.putExtra("articleID", postList.get(position).getArticleId());
        startActivity(intent);
    }

    @OnClick(R.id.FAB_writePost) void ClickWritePost(){
        Intent intent = new Intent(getActivity(), WritePostActivity.class);
        startActivityForResult(intent, 1);
    }

    private void Init(){
        context = getActivity();
        postList = new ArrayList<>();
        BoardAdapter boardAdapter = new BoardAdapter(getContext(), postList);
        LV_board.setAdapter(boardAdapter);
    }
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

    private void getArticleList(int num) throws ExecutionException, InterruptedException {
        List<ArticleData> articleData = new GetArticleTask().execute("article/get.do", num).get();
        if(articleData == null){
            Toast.makeText(context, "게시글을 불러오는 데 실패했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }else{
             postList.addAll(articleData);
        }
    }
}
