package com.example.prashant.monolith.articleData;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Helper for loading a list of articles or a single article.
 */
public class ArticleLoader extends CursorLoader {

    public static ArticleLoader newAllArticlesInstance(Context context) {
        return new ArticleLoader(context, ArticleContract.ArticleEntry.CONTENT_URI);
    }

    public static ArticleLoader newInstanceForItemId(Context context, long itemId) {
        return new ArticleLoader(context, ArticleContract.ArticleEntry.buildArticleUri(itemId));
    }

    private ArticleLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, null);
    }

    public interface Query {
        String[] PROJECTION = {
                ArticleContract.ArticleEntry.COLUMN_ARTICLE_ID,
                ArticleContract.ArticleEntry.COLUMN_TITLE,
                ArticleContract.ArticleEntry.COLUMN_DESCRIPTION,
                ArticleContract.ArticleEntry.COLUMN_IMAGE_URL,
                ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE,
                ArticleContract.ArticleEntry.COLUMN_LINK,
        };

        int COLUMN_ARTICLE_ID = 0;
        int COLUMN_TITLE = 1;
        int COLUMN_DESCRIPTION = 2;
        int COLUMN_IMAGE_URL = 3;
        int COLUMN_PUBLISH_DATE = 4;
        int COLUMN_LINK = 5;
    }
}