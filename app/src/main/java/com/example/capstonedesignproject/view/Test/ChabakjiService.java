package com.example.capstonedesignproject.view.Test;
import java.util.List;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Retrofit으로 차박지 API를 이용히기 위한 interface
 */
public interface ChabakjiService {

  @POST("/chabak/get.do")
  Observable<List<ChabakjiData>> getChabakjiList();
}