package com.example.capstonedesignproject.Server.Service;
import com.example.capstonedesignproject.VO.FishingVO;
import com.example.capstonedesignproject.VO.ReviewVO;
import com.example.capstonedesignproject.VO.ToiletVO;
import com.example.capstonedesignproject.VO.ChabakjiVO;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface ChabakjiService {

    @POST("/chabak/get.do")
    Observable<List<ChabakjiVO>> getChabakjiList();

    @POST("/chabak/getOne.do")
    Observable<List<ChabakjiVO>> getChabakInfo(@Query("placeId") int placeId);

    @POST("/chabak/getPopularList.do")
    Observable<List<ChabakjiVO>> getPopularList();

    @POST("/member/getJJim.do")
    Observable<List<ChabakjiVO>> getFavorite(@Query("id") String memberId);

    @POST("/chabak/getAds.do")
    Observable<List<ChabakjiVO>> getAds(@Query("address") String address);

    @POST("/chabak/getKey.do")
    Observable<List<ChabakjiVO>> getKey(@Query("key") String key);

    @POST("/chabak/filter.do")
    Observable<List<ChabakjiVO>> filter(@Query("add") String address, @Query("flags") String flags);

    @POST("/chabak/eval.do")
    Observable<String> eval(@Query("mId") String memberId, @Query("pId") int placeId, @Query("pName") String placeName,
                            @Query("eval") double point, @Query("review") String review);

    @POST("/chabak/getReviews.do")
    Observable<List<ReviewVO>> getReviews(@Query("placeId") int placeId);

    @POST("/chabak/getToilets.do")
    Observable<List<ToiletVO>> getToilets(@Query("placeId") int placeId);

    @POST("/chabak/getFishings.do")
    Observable<List<FishingVO>> getFishings(@Query("placeId") int placeId);

    @POST("/chabak/getProvinceChabakList.do")
    Observable<List<ChabakjiVO>> getProvinceChabakList(@Query("province") String province);

    @POST("/chabak/getByKey.do")
    Observable<List<ChabakjiVO>> getByKey(@Query("key") String key);
}