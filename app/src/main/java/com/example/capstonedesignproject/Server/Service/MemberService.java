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

  @POST("/member/insert.do")
  Observable<String> join(@Query("id") String memberId, @Query("nickName") String nickName, @Query("password") String password);

  @POST("/member/login.do")
  Observable<String> login(@Query("id") String memberId, @Query("password") String password);

  @POST("/member/nickDoubleCheck.do")
  Observable<String> nickDoubleCheck(@Query("nickName") String nickName);

  @POST("/member/idDoubleCheck.do")
  Observable<String> idDoubleCheck(@Query("memberId") String memberId);

  @POST("/member/jjim.do")
  Observable<String> jjim(@Query("id") String memberId, @Query("placeName") String placeName, @Query("placeId") String placeId);

  @POST("/member/jjim.undo")
  Observable<String> jjimCancel(@Query("id") String memberId, @Query("placeName") String placeName, @Query("placeId") String placeId);

  @POST("/member/changePassword.do")
  Observable<String> changePassword(@Query("memberId") String memberId, @Query("password") String password);

  @POST("/member/changeNickname.do")
  Observable<String> changeNickname(@Query("memberId") String memberId, @Query("nickName") String nickName);

  @POST("/member/withdraw.do")
  Observable<String> withdraw(@Query("memberId") String memberId);
}