package com.example.capstonedesignproject.view.Test;
import com.example.capstonedesignproject.Data.ArticleVO;
import com.example.capstonedesignproject.Data.CommentVO;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ArticleService {

  @POST("/article/get.do")
  Observable<List<ArticleVO>> getArticleList(@Query("num") int num);

  @POST("/article/getComments.do")
  Observable<List<CommentVO>> getComments(@Query("articleId") int num);

  @POST("/article/writeComment.do")
  Observable<String> writeComment(@Query("articleId") int num, @Query("memberId") String memberId, @Query("content") String content);

  @POST("/article/getArticles.do")
  Observable<List<ArticleVO>> getArticles(@Query("memberId") String memberId);

  @POST("/article/getArticle.do")
  Observable<List<ArticleVO>> getArticle(@Query("articleId") int articleId);

  @POST("/article/updateArticle.do")
  Observable<String> updateArticle(@Query("articleId") int articleId, @Query("title") String title,
                                            @Query("content") String content, @Query("fileName") String fileName);

  @POST("/article/writeArticle.do")
  Observable<String> writeArticle(@Query("memberId") String memberId, @Query("title") String title,
                                            @Query("content") String content, @Query("fileName") String fileName);

  @POST("/article/deleteArticle.do")
  Observable<String> deleteArticle(@Query("articleId") int articleId);

  @POST("/article/getArticleByKeyword.do")
  Observable<List<ArticleVO>> getArticleByKeyword(@Query("key") String key);
}