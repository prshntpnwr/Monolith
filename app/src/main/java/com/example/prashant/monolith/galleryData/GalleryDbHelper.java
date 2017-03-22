package com.example.prashant.monolith.galleryData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.prashant.monolith.galleryData.GalleryContract.GalleryEntry;

public class GalleryDbHelper extends SQLiteOpenHelper{

    private final String TAG = GalleryDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "gallery.db";


    public GalleryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_Gallery_TABLE = "CREATE TABLE " + GalleryEntry.TABLE_NAME + " (" +
                GalleryEntry.COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GalleryEntry.COLUMN_IMAGE_PATH + " TEXT, " +
                GalleryEntry.COLUMN_IMAGE_STATUS + " INTEGER" + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_Gallery_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GalleryEntry.TABLE_NAME);
        Log.i(TAG, "Old version = " + oldVersion + " New version = " + newVersion);

        onCreate(sqLiteDatabase);
    }
}