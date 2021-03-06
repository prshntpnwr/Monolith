package com.app.prashant.monolith.galleryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict = false)
public class UnsplashGalleryObject {

    @SerializedName("raw")
    @Expose
    private String raw = null;
    @SerializedName("full")
    @Expose
    private String full = null;
    @SerializedName("regular")
    @Expose
    private String regular = null;
    @SerializedName("small")
    @Expose
    private String small = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}

