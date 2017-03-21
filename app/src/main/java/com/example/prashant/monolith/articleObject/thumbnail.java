package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class thumbnail {

    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("url")
    @Expose
    private String url;

    public String getHeight () {
        return height;
    }

    public void setHeight (String height) {
        this.height = height;
    }

    public String getWidth () {
        return width;
    }

    public void setWidth (String width) {
        this.width = width;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [height = "+height+", width = "+width+", url = "+url+"]";
    }
}

			