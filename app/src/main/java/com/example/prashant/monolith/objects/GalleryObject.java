package com.example.prashant.monolith.objects;

public class GalleryObject {

    private String Image = null;
    private String date = null;
    private String title = null;
    private String detail = null;

    public GalleryObject(String image, String date, String title, String detail){
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
