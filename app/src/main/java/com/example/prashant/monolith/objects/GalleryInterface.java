package com.example.prashant.monolith.objects;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface GalleryInterface {

    @GET("/sites/default/files/thumbnails/image/")
    Call<List<GalleryObject>> reposForphoto(
            @Path("sites") String images
    );
}
