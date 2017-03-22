package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Rss {

    @Element
    private Channel channel;

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