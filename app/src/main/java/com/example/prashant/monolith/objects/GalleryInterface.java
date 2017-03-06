package com.example.prashant.monolith.objects;

import java.util.List;

import retrofit2.Call;

public interface GalleryInterface {

    @retrofit2.http.GET("lg_image_of_the_day.rss")
    Call<Rss> respForphoto();
}
