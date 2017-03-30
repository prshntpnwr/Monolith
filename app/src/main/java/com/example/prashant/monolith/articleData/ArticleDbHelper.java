package com.example.prashant.monolith.articleData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ArticleDbHelper extends SQLiteOpenHelper {

    private final String TAG = ArticleDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "article.db";

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_Article_TABLE = "CREATE TABLE " + ArticleContract.ArticleEntry.TABLE_NAME_ARTICLE + " (" +
                ArticleContract.ArticleEntry.COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.ArticleEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.ArticleEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.ArticleEntry.COLUMN_IMAGE_URL + " TEXT, " +
                ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE + " TEXT, " +
                ArticleContract.ArticleEntry.COLUMN_LINK + " TEXT" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_Article_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleContract.ArticleEntry.TABLE_NAME_ARTICLE);
        Log.i(TAG, "Old version = " + oldVersion + " New version = " + newVersion);
        onCreate(sqLiteDatabase);
    }
}