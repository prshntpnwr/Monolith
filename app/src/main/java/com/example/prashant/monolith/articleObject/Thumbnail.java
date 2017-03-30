package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Thumbnail {

    @Attribute
    private String height;

    @Attribute
    private String width;

    @Attribute
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [height = " + height + ", width = " + width + ", url = " + url + "]";
    }
}

			