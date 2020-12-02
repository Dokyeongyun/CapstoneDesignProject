package com.example.capstonedesignproject.view.Test;
import com.example.capstonedesignproject.Data.ReviewVO;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface ChabakjiService {

  @POST("/chabak/get.do")
  Observable<List<ChabakjiData>> getChabakjiList();

  @POST("/member/getJJim.do")
  Observable<List<ChabakjiData>> getFavorite(@Query("id") String memberId);

  @POST("/chabak/eval.do")
  Observable<String> eval(@Query("mId") String memberId, @Query("pId") String placeId, @Query("pName") String placeName,
  @Query("eval") String point, @Query("review") String review);

  @POST("/chabak/getReviews.do")
  Observable<List<ReviewVO>> getReviews(@Query("placeId") String placeId);
}