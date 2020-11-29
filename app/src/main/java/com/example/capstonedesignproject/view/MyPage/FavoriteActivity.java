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

import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.DetailActivity;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiAdapter;
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

public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.RV_favorite) RecyclerView RV_favorite;
    @BindView(R.id.PB_favorite) ProgressBar PB_favorite;
    @BindView(R.id.snackContainer) ConstraintLayout snackContainer;

    private ChabakjiAdapter_Favorite chabakjiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        Init();
        load();

        // CardView 아이템 클릭 리스너
        RV_favorite.addOnItemTouchListener(new RecyclerTouchListener(this, RV_favorite, (view, position) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
            startActivity(intent);
        }));
    }
    // 초기 설정
    private void Init(){
        // Recycler View
        RV_favorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chabakjiAdapter = new ChabakjiAdapter_Favorite(this);
        RV_favorite.setHasFixedSize(true);
        RV_favorite.setAdapter(chabakjiAdapter);
    }

    private void load(){
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
                        Snackbar.make(snackContainer, "차박지 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_favorite.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() { }
                });
    }

}