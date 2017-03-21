package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Rss {

    @SerializedName("channel")
    @Expose
    private Channel channel;

    @SerializedName("version")
    @Expose
    private String version;

    public Channel getChannel () {
        return channel;
    }

    public void setChannel (Channel channel) {
        this.channel = channel;
    }

    public String getVersion () {
        return version;
    }

    public void setVersion (String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ClassPojo [channel = "+channel+", version = "+version+"]";
    }
}