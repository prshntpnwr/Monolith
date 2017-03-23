package com.example.prashant.monolith.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.articleData.ArticleContract;
import com.example.prashant.monolith.articleData.ArticleLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

        return new RemoteAdapter(WidgetService.this, appWidgetId);
    }


    public static class RemoteAdapter implements RemoteViewsFactory {

        private static final String TAG = RemoteAdapter.class.getSimpleName();
        Cursor mCursor;
        Context mContext;
        int appWidgetId;

        public RemoteAdapter(Context context, int appWidgetId) {
            this.appWidgetId = appWidgetId;
            this.mContext = context;
        }

        @Override
        public void onCreate() {
            mCursor = mContext.getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            mCursor.moveToPosition(i);
            Log.d(TAG, "Cursor is at " + mCursor.getPosition());
            final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_detail_list_item);
            try {
                Picasso picasso = Picasso.with(mContext);
                picasso.setLoggingEnabled(true);
                Bitmap bitmap = picasso.load(mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL))
                        .placeholder(R.color.accent)
                        .error(R.color.primary_dark)
                        .get();
                remoteViews.setImageViewBitmap(R.id.widget_photo, bitmap);
                remoteViews.setTextViewText(R.id.widget_article_title, mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));
                remoteViews.setTextViewText(R.id.widget_article_date, mCursor.getString(ArticleLoader.Query.COLUMN_PUBLISH_DATE));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}