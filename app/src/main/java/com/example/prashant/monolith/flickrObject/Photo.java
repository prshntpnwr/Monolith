package com.example.prashant.monolith.flickrObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Photo
{
    private String id;

    private String farm;

    private String isfamily;

    private String title;

    private String ispublic;

    private String owner;

    private String secret;

    private String server;

    private String isfriend;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFarm ()
    {
        return farm;
    }

    public void setFarm (String farm)
    {
        this.farm = farm;
    }

    public String getIsfamily ()
    {
        return isfamily;
    }

    public void setIsfamily (String isfamily)
    {
        this.isfamily = isfamily;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getIspublic ()
    {
        return ispublic;
    }

    public void setIspublic (String ispublic)
    {
        this.ispublic = ispublic;
    }

    public String getOwner ()
    {
        return owner;
    }

    public void setOwner (String owner)
    {
        this.owner = owner;
    }

    public String getSecret ()
    {
        return secret;
    }

    public void setSecret (String secret)
    {
        this.secret = secret;
    }

    public String getServer ()
    {
        return server;
    }

    public void setServer (String server)
    {
        this.server = server;
    }

    public String getIsfriend ()
    {
        return isfriend;
    }

    public void setIsfriend (String isfriend)
    {
        this.isfriend = isfriend;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", farm = "+farm+", isfamily = "+isfamily+", title = "+title+", ispublic = "+ispublic+", owner = "+owner+", secret = "+secret+", server = "+server+", isfriend = "+isfriend+"]";
    }
}