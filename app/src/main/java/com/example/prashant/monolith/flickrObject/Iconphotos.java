package com.example.prashant.monolith.flickrObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Iconphotos
{
    private Photo[] photo;

    public Photo[] getPhoto ()
    {
        return photo;
    }

    public void setPhoto (Photo[] photo)
    {
        this.photo = photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photo = "+photo+"]";
    }
}

			