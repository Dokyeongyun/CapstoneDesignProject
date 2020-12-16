package com.example.capstonedesignproject.Server.Service;


import com.example.capstonedesignproject.VO.ReviewVO;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface MemberService {

  @POST("/member/getJJimAndEvaluated.do")
  Observable<String> getJJimAndEvaluated(@Query("memberId") String memberId, @Query("placeId") String placeId);

  @POST("/member/getUsersReview.do")
  Observable<List<ReviewVO>> getUsersReview(@Query("memberId") String memberId);
}