package com.example.prashant.monolith.galleryData;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Helper for loading a list of articles or a single article.
 */
public class GalleryLoader extends CursorLoader {

    public static GalleryLoader newAllGalleryInstance(Context context) {
        return new GalleryLoader(context, GalleryContract.GalleryEntry.CONTENT_URI);
    }

    public static GalleryLoader newInstanceForItemId(Context context, long itemId) {
        return new GalleryLoader(context, GalleryContract.GalleryEntry.buildGalleryUri(itemId));
    }

    private GalleryLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, null);
    }

    public interface Query {
        String[] PROJECTION = {
                GalleryContract.GalleryEntry.COLUMN_IMAGE_ID,
                GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH,
                GalleryContract.GalleryEntry.COLUMN_IMAGE_HD_PATH,
                GalleryContract.GalleryEntry.COLUMN_IMAGE_STATUS
        };

        int COLUMN_IMAGE_ID = 0;
        int COLUMN_IMAGE_PATH = 1;
        int COLUMN_IMAGE_HD_PATH = 2;
        int COLUMN_IMAGE_STATUS = 3;
    }
}