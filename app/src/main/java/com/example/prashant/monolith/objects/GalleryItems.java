package com.example.prashant.monolith.objects;

import com.google.gson.annotations.SerializedName;

public class GalleryItems {

    @SerializedName("MessagesRequestStatus")
    private String Image;
    private String date;
    private String title;
    private String detail;

    public GalleryItems(String image, String date, String title, String detail){
        this.Image = image;
        this.date = date;
        this.title = title;
        this.detail = detail;
    }

    public String getImage() {
        return Image;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

}
