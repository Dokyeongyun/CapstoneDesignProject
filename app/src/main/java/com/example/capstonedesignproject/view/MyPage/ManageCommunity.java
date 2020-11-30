package com.example.capstonedesignproject.view.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstonedesignproject.Adapter.ArticleAdapter;
import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.Board.ShowPostActivity;
import com.example.capstonedesignproject.view.ChabakJi.DetailActivity;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiAdapter_Favorite;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.example.capstonedesignproject.view.Test.SetApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ManageCommunity extends AppCompatActivity {
    @BindView(R.id.RV_favorite) RecyclerView recyclerView;
    @BindView(R.id.PB_favorite) ProgressBar PB_favorite;
    @BindView(R.id.snackContainer) ConstraintLayout snackContainer;
    @BindView(R.id.TV_title) TextView TV_title;

    private ChabakjiAdapter_Favorite chabakjiAdapter = new ChabakjiAdapter_Favorite();
    private ArticleAdapter articleAdapter = new ArticleAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_community);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        if(intent.getStringExtra("Type").equals("Favorites")){
            Init(chabakjiAdapter, "즐겨찾는 차박지");
            FavoritesLoad();
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, (view, position) -> {
                Intent intent2 = new Intent(this, DetailActivity.class);
                intent2.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
                startActivity(intent2);
            }));
        } else if(intent.getStringExtra("Type").equals("Articles")){
            Init(articleAdapter, "내가 작성한 게시글");
            ArticlesLoad();
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, (view, position) -> {
                Intent intent2 = new Intent(this, ShowPostActivity.class);
                intent2.putExtra("articleID", articleAdapter.getItemAt(position).getArticleId());
                startActivity(intent2);
            }));
        }



    }
    /**
     * 초기 설정
     */
    private void Init(RecyclerView.Adapter adapter, String title){
        TV_title.setText(title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 즐겨찾는 차박지 목록 읽기
     */
    private void FavoritesLoad(){
        PB_favorite.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<List<ChabakjiData>> observable = application.getChabakjiService().getFavorite(HomeActivity.memberID);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ChabakjiData>>() {
                    @Override
                    public void onNext(List<com.example.capstonedesignproject.view.Test.ChabakjiData> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            chabakjiAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_favorite.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_favorite.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() { }
                });
    }

    /**
     * 작성한 게시글 목록 읽기
     */
    private void ArticlesLoad(){
        PB_favorite.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(this).getApplication();
        Observable<List<ArticleData>> observable = application.getArticleService().getArticles(HomeActivity.memberID);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ArticleData>>() {
                    @Override
                    public void onNext(List<ArticleData> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            articleAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_favorite.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_favorite.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() { }
                });
    }
}
