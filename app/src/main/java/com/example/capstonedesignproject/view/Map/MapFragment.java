package com.example.capstonedesignproject.view.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment {
    @BindView(R.id.webView) WebView webView;
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
        return v;
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
                String searchRegion = url.split("_")[1];
                Toast.makeText(context, searchRegion, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), ListActivity.class);
                intent.putExtra("FromMap", searchRegion);
                intent.putExtra("Type", "FromMap");

                startActivity(intent);
                return true;
            }else{
                view.loadUrl(url);
                return true;
            }
        }
    }
}
