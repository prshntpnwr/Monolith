package com.example.prashant.monolith.objects;

import org.simpleframework.xml.Root;

import retrofit2.Call;

@Root(strict=false)
public interface GalleryInterface {

    @retrofit2.http.GET("lg_image_of_the_day.rss")
    Call<Rss> respForphoto();
}
