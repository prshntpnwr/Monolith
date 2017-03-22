package com.example.prashant.monolith.articleData;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ArticleContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.prashant.monolith";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTICLE = "articles";

    public static final class ArticleEntry implements BaseColumns {

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.prashant.monolith.articles";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.prashant.monolith.articles";

        public static final Uri CONTENT_URI =
               BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();

        public static final String TABLE_NAME = "article";

        public static final String COLUMN_ARTICLE_ID = "article_id";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_IMAGE_URL = "image";

        public static final String COLUMN_PUBLISH_DATE = "pub_date";

        public static final String COLUMN_LINK = "link";

        public static Uri buildArticleUri(long id) {
            //ContentUris.withAppendedId() is a helper method to create an id-based URI
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }
}