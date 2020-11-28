package com.example.capstonedesignproject.view.Test;


import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface MemberService {

  @POST("/member/getJJimAndEvaluated.do")
  Observable<String> getJJimAndEvaluated(@Query("memberId") String memberId, @Query("placeId") String placeId);
}