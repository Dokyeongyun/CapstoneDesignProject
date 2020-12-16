package com.example.capstonedesignproject.view.ETC;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstonedesignproject.VO.ChabakjiVO;
import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.Board.BoardFragment;
import com.example.capstonedesignproject.view.Board.SuggestActivity;
import com.example.capstonedesignproject.view.ChabakJi.DetailActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceFragment;
import com.example.capstonedesignproject.view.Filter.SearchActivity;
import com.example.capstonedesignproject.view.Login.LoginActivity;
import com.example.capstonedesignproject.view.MyPage.ManageCommunity;
import com.example.capstonedesignproject.view.MyPage.MyPageFragment;
import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.Server.SetApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainFragment extends Fragment {
    @BindView(R.id.RV_main) RecyclerView RV_main;
    @BindView(R.id.TV_searchUsingRegion) TextView TV_searchUsingRegion;
    @BindView(R.id.TV_searchUsingKeyword) TextView TV_searchUsingKeyword;
    @BindView(R.id.TV_searchBoard) TextView TV_searchBoard;
    @BindView(R.id.TV_goToBoard) TextView TV_goToBoard;
    @BindView(R.id.TV_goToSuggest) TextView TV_goToSuggest;
    @BindView(R.id.TV_goToJJIM) TextView TV_goToJJIM;
    @BindView(R.id.TV_goToMypage) TextView TV_goToMypage;
    @BindView(R.id.TV_goToQnA) TextView TV_goToQnA;
    @BindView(R.id.TV_goToLogout) TextView TV_goToLogout;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.snackContainer) ConstraintLayout snackContainer;

    private ChabakjiAdapter chabakjiAdapter;
    private static int page = 0;

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        Init();
        load();

        // CardView 아이템 클릭 리스너
        RV_main.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), RV_main, (view, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
            startActivity(intent);
        }));
        return v;
    }
    // 초기 설정
    private void Init(){
        // Recycler View
        RV_main.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chabakjiAdapter = new ChabakjiAdapter();
        RV_main.setHasFixedSize(true);
        RV_main.setAdapter(chabakjiAdapter);
    }

    private void load(){
        progressBar.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(getActivity()).getApplication();
        Observable<List<ChabakjiVO>> observable = application.getChabakjiService().getPopularList();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ChabakjiVO>>() {
            @Override
            public void onNext(List<ChabakjiVO> items) {
                Log.d("수신", "총 수신 개수: "+items.size());
                for(int i=0; i<items.size(); i++){
                    Log.d("수신", String.valueOf(items.get(i)));
                    chabakjiAdapter.setItemsAndRefresh(items.get(i));
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError(Throwable e) {
                Log.d("수신","수신실패");
                e.printStackTrace();
                Snackbar.make(snackContainer, "차박지 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCompleted() { }
        });
    }

    @OnClick(R.id.TV_searchUsingRegion) void searchUsingRegion(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new RegionChoiceFragment(), "지역선택");
    }
    @OnClick(R.id.TV_searchUsingKeyword) void searchUsingKeyword(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("FromMain", "Keyword");
        startActivity(intent);
    }
    @OnClick(R.id.TV_searchBoard) void searchBoard(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("FromMain", "Board");
        startActivityForResult(intent, 1);
    }
    @OnClick(R.id.TV_goToBoard) void goToBoard(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new BoardFragment(), "게시판");
    }
    @OnClick(R.id.TV_goToSuggest) void goToSuggest(){
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }

    /**
     * 즐겨찾는 차박지 리스트 화면으로 이동
     */
    @OnClick(R.id.TV_goToJJIM) void goToJJIM(){
        Intent intent = new Intent(getActivity(), ManageCommunity.class);
        intent.putExtra("Type", "Favorites");
        startActivity(intent);
    }
    @OnClick(R.id.TV_goToMypage) void goToMypage(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new MyPageFragment(), "마이페이지");
    }
    @OnClick(R.id.TV_goToQnA) void goToQnA(){
        // TODO QnA 페이지로 이동
    }
    @OnClick(R.id.TV_goToLogout) void goToLogout(){
        String autoCheck = LoginActivity.autoLoginFile.getString("autoLogin","");
        if(autoCheck.equals("true")){
            LoginActivity.editor.putString("autoLogin", "false");
            LoginActivity.editor.apply();
        }
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                String keyword = data.getStringExtra("SearchKeyword");
                Bundle bundle = new Bundle();
                bundle.putString("SearchKeyword", keyword);
                HomeActivity.boardFragment.setArguments(bundle);
                ((HomeActivity)getActivity()).searchBoard(HomeActivity.boardFragment);
            }
        }
    }
}
