package com.example.capstonedesignproject.view.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.DetailActivity;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiAdapter;
import com.example.capstonedesignproject.view.Test.ChabakjiData;
import com.example.capstonedesignproject.view.Test.SetApplication;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MapFragment extends Fragment {
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.RV_map) RecyclerView RV_map;
    @BindView(R.id.PB_map) ProgressBar PB_map;
    @BindView(R.id.snackContainer) ConstraintLayout snackContainer;
    @BindView(R.id.TV_notice) TextView TV_notice;
    @BindView(R.id.TV_regionName) TextView TV_regionName;

    private ChabakjiAdapter chabakjiAdapter;
    public Context context;
    public MapFragment() { }

    private WebSettings webSettings; //웹뷰세팅
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, v);
        context = this.getContext();

        Init();

        // 웹뷰 시작
        webView.setWebViewClient(new WebViewClientClass()); // 클릭시 새창 안뜨게
        webSettings = webView.getSettings(); //세부 세팅 등록
        webSettings.setJavaScriptEnabled(true); // 웹페이지 자바스크립트 허용 여부
        webSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        webSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        webSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        webSettings.setSupportZoom(false); // 화면 줌 허용 여부
        webSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        webSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.loadUrl(HomeActivity.SERVER_URL+"/map/"); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        // CardView 아이템 클릭 리스너
        RV_map.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), RV_map, (view, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("Chabakji", chabakjiAdapter.getItemAt(position));
            startActivity(intent);
        }));
        return v;
    }

    // 초기 설정
    public void Init(){
        // Recycler View
        RV_map.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chabakjiAdapter = new ChabakjiAdapter();
        RV_map.setHasFixedSize(true);
        RV_map.setAdapter(chabakjiAdapter);
    }

    /**
     * 지역별 차박지 검색
     */
    public void load(String province){
        chabakjiAdapter.clear();
        TV_notice.setVisibility(View.GONE);
        RV_map.setVisibility(View.VISIBLE);
        PB_map.setVisibility(View.VISIBLE);
        TV_regionName.setVisibility(View.VISIBLE);
        TV_regionName.setText(province);
        final SetApplication application = (SetApplication) Objects.requireNonNull(getActivity()).getApplication();
        Observable<List<ChabakjiData>> observable = application.getChabakjiService().getProvinceChabakList(province);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ChabakjiData>>() {
                    @Override
                    public void onNext(List<com.example.capstonedesignproject.view.Test.ChabakjiData> items) {
                        Log.d("수신", "총 수신 개수: "+items.size());
                        for(int i=0; i<items.size(); i++){
                            Log.d("수신", String.valueOf(items.get(i)));
                            chabakjiAdapter.setItemsAndRefresh(items.get(i));
                        }
                        PB_map.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("수신","수신실패");
                        e.printStackTrace();
                        Snackbar.make(snackContainer, "차박지 데이터를 읽어올 수 없습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        PB_map.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCompleted() { }
                });
    }



    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            try {
                url =  URLDecoder.decode(url,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(url.startsWith("app://")){
                String province = url.split("_")[1];
                load(province);
                Toast.makeText(context, province, Toast.LENGTH_SHORT).show();
                return true;
            }else{
                view.loadUrl(url);
                return true;
            }
        }
    }
}
