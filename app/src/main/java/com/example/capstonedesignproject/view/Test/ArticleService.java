package com.example.capstonedesignproject.view.Test;
import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.Data.CommentVO;

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

  @POST("/article/getComments.do")
  Observable<List<CommentVO>> getComments(@Query("articleId") int num);

  @POST("/article/writeComment.do")
  Observable<String> writeComment(@Query("articleId") int num, @Query("memberId") String memberId, @Query("content") String content);

  @POST("/article/getArticles.do")
  Observable<List<ArticleData>> getArticles(@Query("memberId") String memberId);
}