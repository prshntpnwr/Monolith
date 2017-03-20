package com.example.prashant.monolith.articleObject;


import org.simpleframework.xml.Root;

import retrofit2.Call;
import retrofit2.http.Query;
@Root(strict=false)
public interface ArticleInterface {

    @retrofit2.http.GET("?format=json&pretty=1")
    Call<RelatedTopic> results(
             @Query("query") String tag
    );
}
