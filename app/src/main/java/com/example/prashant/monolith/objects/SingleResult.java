package com.example.prashant.monolith.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleResult {

    @SerializedName("cover_photo")
    @Expose
    private CoverPhoto coverPhoto;

    public CoverPhoto getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhoto coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
