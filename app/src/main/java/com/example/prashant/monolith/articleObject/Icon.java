package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Icon {

    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("Width")
    @Expose
    private String width;

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

}