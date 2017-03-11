package com.example.prashant.monolith.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GalleryContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.

    public static final String CONTENT_AUTHORITY = "com.example.prashant.monolith.data";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/images");

    public static final String PATH_IMAGE = "images";

    public static final class GalleryEntry implements BaseColumns {

        //path to the gallery table
        public static final Uri CONTENT_URI =
               BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + PATH_IMAGE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + PATH_IMAGE;

        public static final String TABLE_NAME = "gallery";

        public static final String COLUMN_IMAGE_ID = "image_id";

        public static final String COLUMN_IMAGE_PATH = "image";

        public static final String COLUMN_IMAGE_STATUS = "image_status";

        public static Uri buildGalleryUri(long id) {

            //ContentUris.withAppendedId() is a helper method to create an id-based URI
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}