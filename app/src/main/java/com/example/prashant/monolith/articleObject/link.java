package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class link {
    @SerializedName("rel")
    @Expose
    private String rel;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("href")
    @Expose
    private String href;

    public String getRel () {
        return rel;
    }

    public void setRel (String rel) {
        this.rel = rel;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getHref () {
        return href;
    }

    public void setHref (String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "ClassPojo [rel = "+rel+", type = "+type+", href = "+href+"]";
    }
}