package com.example.prashant.monolith.galleryData;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.prashant.monolith.articleData.ArticleContract;
import com.example.prashant.monolith.articleData.ArticleContract.ArticleEntry;
import com.example.prashant.monolith.galleryData.GalleryContract.GalleryEntry;

import static com.example.prashant.monolith.galleryData.GalleryContract.GalleryEntry.TABLE_NAME;

public class DataProvider extends android.content.ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private GalleryDbHelper mOpenHelper;

    private static final int IMAGE = 0;
    private static final int IMAGE_ID = 1;
    private static final int ARTICLE = 3;
    private static final int ARTICLE_ID = 4;

    private static UriMatcher buildUriMatcher() {
        //The code passed into the constructor represents the code to return for the root URI.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GalleryContract.CONTENT_AUTHORITY;

        //for the type of URI we want to add, create a corresponding code
        //for gallery
        matcher.addURI(authority, GalleryContract.PATH_IMAGE, IMAGE);
        matcher.addURI(authority, GalleryContract.PATH_IMAGE + "/#", IMAGE_ID);

        //for article
        matcher.addURI(authority, ArticleContract.PATH_ARTICLE, ARTICLE);
        matcher.addURI(authority, ArticleContract.PATH_ARTICLE + "/#", ARTICLE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new GalleryDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case IMAGE:
                return GalleryEntry.CONTENT_ITEM_TYPE;
            case IMAGE_ID:
                return GalleryEntry.CONTENT_ITEM_TYPE;
            case ARTICLE:
                return ArticleEntry.CONTENT_ITEM_TYPE;
            case ARTICLE_ID:
                return ArticleEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case IMAGE:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            case IMAGE_ID:
//              long _id = ContentUris.parseId(uri);
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;

            case ARTICLE:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;

            case ARTICLE_ID:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long _id = db.insert(TABLE_NAME, null, values);

        switch (match) {
            case IMAGE:
                if (_id > 0) {
                    returnUri = GalleryEntry.buildGalleryUri(_id);
                } else {
                    throw new SQLException("Failed to add a record into " + uri);
                }
                break;

            case ARTICLE:
                if (_id > 0) {
                    returnUri = ArticleEntry.buildArticleUri(_id);
                } else {
                    throw new SQLException("Failed to add a record into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;

        if (null == selection) selection = "1";

        switch (match) {
            case IMAGE:
                rowDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case ARTICLE:
                rowDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowUpdated;

        switch (match) {
            case IMAGE:
                rowUpdated = db.update(TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case ARTICLE:
                rowUpdated = db.update(TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowUpdated;
    }

//    @NonNull
//    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
//            throws OperationApplicationException {
//        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//        db.beginTransaction();
//        try {
//            final int numOperations = operations.size();
//            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
//            for (int i = 0; i < numOperations; i++) {
//                results[i] = operations.get(i).apply(this, results, i);
//            }
//            db.setTransactionSuccessful();
//            return results;
//        } finally {
//            db.endTransaction();
//        }
//    }

}