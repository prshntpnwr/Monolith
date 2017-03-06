package com.example.prashant.monolith.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GalleryObject {

    @SerializedName("_url")
    @Expose
    private String url;
    @SerializedName("_length")
    @Expose
    private String length;
    @SerializedName("_type")
    @Expose
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

