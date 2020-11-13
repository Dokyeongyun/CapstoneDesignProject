package com.example.capstonedesignproject.view.Test;
import com.example.capstonedesignproject.Data.ArticleData;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface ArticleService {

  @POST("/article/get.do")
  Observable<List<ArticleData>> getArticleList(@Query("num") int num);
}