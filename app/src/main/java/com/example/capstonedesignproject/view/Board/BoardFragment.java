package com.example.capstonedesignproject.view.Board;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.capstonedesignproject.Adapter.ArticleAdapter;
import com.example.capstonedesignproject.VO.ArticleVO;
import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.SetApplication;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BoardFragment extends Fragment {
    @BindView(R.id.LL_board) LinearLayout LL_board;
    @BindView(R.id.LL_notification) LinearLayout LL_notification;
    @BindView(R.id.TL_board) TabLayout TL_board;
    @BindView(R.id.FAB_writePost) FloatingActionButton FAB_writePost;
    @BindView(R.id.RV_board) RecyclerView RV_board;
    @BindView(R.id.LV_notification) ListView LV_notification;
    @BindView(R.id.PB_board) ProgressBar PB_board;
    @BindView(R.id.CL_snackContainer) ConstraintLayout CL_snackContainer;

    private ArticleAdapter articleAdapter;
    private int page = 1;
    String keyword = "";
    String temp = "";

    public BoardFragment() { }

    public static Fragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        ButterKnife.bind(this, v);

        Bundle bundle = getArguments();

        if (bundle != null && bundle.getString("SearchKeyword") != null) {
            keyword = bundle.getString("SearchKeyword");
        }
        // 초기 설정
        Init();
        // 게시글 목록 읽어오기
        if(!keyword.equals(temp)){
            load("getArticleByKeyword", keyword);
        }else{
            load("", "");
        }
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
        // 리싸이클러뷰 클릭 리스너
        RV_board.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), RV_board, (view, position) -> {
            Intent intent = new Intent(getActivity(), ShowPostActivity.class);
            intent.putExtra("articleID", articleAdapter.getItemAt(position).getArticleId());
            intent.putExtra("memberID", articleAdapter.getItemAt(position).getMemberId());
            startActivityForResult(intent, 2);
        }));
        return v;
    }

    /**
     * 초기 설정
     */
    private void Init(){
        articleAdapter = new ArticleAdapter();
        RV_board.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RV_board.setHasFixedSize(true);
        RV_board.setAdapter(articleAdapter);
    }

    /**
     * 게시글 목록 읽어오기
     */
    private void load(String type, String key){
        articleAdapter.clear();
        PB_board.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(getActivity()).getApplication();
        Observable<List<ArticleVO>> observable;
        if(type.equals("getArticleByKeyword")){
            observable = application.getArticleService().getArticleByKeyword(key);
        } else{
            observable = application.getArticleService().getArticleList(page);
        }
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ArticleVO>>() {
                    @Override
                    public void onNext(List<ArticleVO> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            articleAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_board.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(CL_snackContainer, "게시판 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_board.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() {
                        temp = keyword;
                    }
                });
    }

    /**
     * TabLayout
     */
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

    /**
     * 게시글 작성 버튼
     */
    @OnClick(R.id.FAB_writePost) void ClickWritePost(){
        Intent intent = new Intent(getActivity(), WritePostActivity.class);
        intent.putExtra("writeMode", "NEW");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                load("", "");
            }
        }else if(requestCode == 2){
            load("", "");
        }
    }
}
