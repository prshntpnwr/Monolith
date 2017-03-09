package com.example.prashant.monolith.unsplashObject;

import org.simpleframework.xml.Root;

import retrofit2.Call;
import retrofit2.http.Query;

@Root(strict=false)
public interface unsplashGalleryInterface {

//    @retrofit2.http.GET("/search/collections?page=1&query=nasa&client_id=2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7")
//    Call<CoverPhoto> respForphoto();

    @retrofit2.http.GET("/search/collections?")
    Call<Results> result(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("query") String tag,
            @Query("client_id") String id
    );
}
