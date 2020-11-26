package com.example.capstonedesignproject.view.ChabakJi;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.Filter.FilterActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.example.capstonedesignproject.view.Test.SetApplication;
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

public class ChabakjiFragment extends Fragment {
    private static final int FILTER_REQUEST_CODE = 1;

    @BindView(R.id.BT_regionChoice) Button BT_regionChoice;
    @BindView(R.id.BT_setFilter) Button BT_setFilter;
    @BindView(R.id.RV_home) RecyclerView RV_home;
    @BindView(R.id.PB_home) ProgressBar PB_home;
    @BindView(R.id.CL_snackContainer_home) ConstraintLayout CL_snackContainer_home;

    private ChabakjiAdapter chabakjiAdapter;
    private static int page = 0;

    public ChabakjiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chabakji, container, false);

        ButterKnife.bind(this, v);
        Init();

        // CardView 아이템 클릭 리스너
        RV_home.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), RV_home, (view, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
            startActivity(intent);
        }));
        return v;
    }

    @OnClick(R.id.BT_regionChoice) void ClickRegionChoice(){
        Intent intent = new Intent(getActivity(), RegionChoiceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.BT_setFilter) void ClickSetFilter(){
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        startActivityForResult(intent, FILTER_REQUEST_CODE);
    }

    // 초기 설정
    private void Init(){
        RV_home.setLayoutManager(new LinearLayoutManager(getContext()));
        chabakjiAdapter = new ChabakjiAdapter(getContext());
        RV_home.setHasFixedSize(true);
        RV_home.setAdapter(chabakjiAdapter);

        BT_regionChoice.setText("내주변");
    }

    private void load(String value){
        PB_home.setVisibility(View.VISIBLE);
        final SetApplication application = (SetApplication) Objects.requireNonNull(getActivity()).getApplication();
        // TODO 필터로 걸러진 차박지만을 보여줄 수 있도록 ChabakjiService 에 메서드 선언 및 호출
        // ListActivity -> 필터 조건 보내줌
        Observable<List<ChabakjiData>> observable = application.getChabakjiService().getAds(value);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ChabakjiData>>() {
                    @Override
                    public void onNext(List<ChabakjiData> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            chabakjiAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_home.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(CL_snackContainer_home, "차박지 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_home.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() { }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTER_REQUEST_CODE){
            if(resultCode == 1){
                if(data!=null){
                    boolean[] filterArr = data.getBooleanArrayExtra("FilterArr");
                    StringBuilder sb = new StringBuilder();
                    for (boolean b : filterArr) {
                        sb.append(b).append(" ");
                    }
                    Toast.makeText(getActivity(), sb, Toast.LENGTH_SHORT).show();
                }
                // TODO 선택된 조건을 이용하여 검색 수행 및 결과 띄우기
            }
        }
    }
}
