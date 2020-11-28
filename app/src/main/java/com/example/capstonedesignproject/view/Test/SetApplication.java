package com.example.capstonedesignproject.view.Test;

import android.app.Application;
import android.util.Log;

import com.example.capstonedesignproject.view.ETC.HomeActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SetApplication extends Application {
    private Retrofit retrofit;
    private ChabakjiService chabakjiService;
    private ArticleService articleService;
    private MemberService memberService;

    @Override
    public void onCreate() {
        super.onCreate();
        setupAPIClient();
    }

    private void setupAPIClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d("API LOG", message));

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HomeActivity.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        chabakjiService = retrofit.create(ChabakjiService.class);
        articleService = retrofit.create(ArticleService.class);
        memberService = retrofit.create(MemberService.class);
    }

    public ChabakjiService getChabakjiService() { return chabakjiService; }
    public ArticleService getArticleService() { return articleService; }
    public MemberService getMemberService() { return memberService; }

}
