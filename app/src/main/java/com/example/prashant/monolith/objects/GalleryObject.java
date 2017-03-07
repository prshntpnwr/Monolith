package com.example.prashant.monolith.objects;

import org.simpleframework.xml.Root;

public class GalleryObject {

    @Root(strict=false)

    private String url;

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [url = "+url+"]";
    }
}

