package com.example.prashant.monolith.objects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel", strict = false)

public class channel implements Serializable {
    @ElementList(inline = true, name="item")
    private List<GalleryInterface> mGalleryItems;

    public List<GalleryInterface> getmGalleryItems() {
        return mGalleryItems;
    }

    public channel() {
    }

    public channel(List<GalleryInterface> mGalleryItems) {
        this.mGalleryItems = mGalleryItems;
    }
}

