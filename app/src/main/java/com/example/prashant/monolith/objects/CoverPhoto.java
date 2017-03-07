package com.example.prashant.monolith.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class CoverPhoto {

    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("categories")

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

}