package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Root;

import retrofit2.Call;

@Root(strict = false)
public interface ArticleInterface {

    @retrofit2.http.GET("/space_time.xml")
    Call<Rss> results1();
}
