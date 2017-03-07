package com.example.prashant.monolith.objects;

import org.simpleframework.xml.Root;

import retrofit2.Call;

public interface GalleryInterface {

    @Root(strict=false)

    @retrofit2.http.GET("lg_image_of_the_day.rss")
    Call<Rss> respForphoto();
}
