package com.example.prashant.monolith.objects;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;

public interface GalleryInterface {

    @GET("/sites/default/files/thumbnails/image/")
    Call<List<GalleryObject>> respForphoto(
    );
}
