package com.example.capstonedesignproject.view.Test;
import com.example.capstonedesignproject.Data.FishingVO;
import com.example.capstonedesignproject.Data.ReviewVO;
import com.example.capstonedesignproject.Data.ToiletVO;

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

    @POST("/chabak/getOne.do")
    Observable<List<ChabakjiData>> getChabakInfo(@Query("placeId") int placeId);

    @POST("/chabak/getPopularList.do")
    Observable<List<ChabakjiData>> getPopularList();

    @POST("/member/getJJim.do")
    Observable<List<ChabakjiData>> getFavorite(@Query("id") String memberId);

    @POST("/chabak/getAds.do")
    Observable<List<ChabakjiData>> getAds(@Query("address") String address);

    @POST("/chabak/getKey.do")
    Observable<List<ChabakjiData>> getKey(@Query("key") String key);

    @POST("/chabak/filter.do")
    Observable<List<ChabakjiData>> filter(@Query("add") String address, @Query("flags") String flags);

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
    Observable<List<ChabakjiData>> getProvinceChabakList(@Query("province") String province);

    @POST("/chabak/getByKey.do")
    Observable<List<ChabakjiData>> getByKey(@Query("key") String key);
}