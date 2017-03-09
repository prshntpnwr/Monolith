package com.example.prashant.monolith.flickrObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class flickrGalleryObject
{
    private Collection collection;

    public Collection getCollection ()
    {
        return collection;
    }

    public void setCollection (Collection collection)
    {
        this.collection = collection;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [collection = "+collection+"]";
    }
}