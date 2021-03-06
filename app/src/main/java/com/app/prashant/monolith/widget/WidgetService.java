package com.app.prashant.monolith.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.prashant.monolith.R;
import com.app.prashant.monolith.articleData.ArticleContract;
import com.app.prashant.monolith.articleData.ArticleLoader;
import com.squareup.picasso.Picasso;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteAdapter(WidgetService.this, intent);
    }

    public class RemoteAdapter implements RemoteViewsService.RemoteViewsFactory {

        private final String TAG = RemoteAdapter.class.getSimpleName();
        Cursor mCursor;
        Context mContext;
        private Intent intent;

        public RemoteAdapter(WidgetService context, Intent intent) {
            this.mContext = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            Log.e(TAG, "onCreate: ");
            mCursor = mContext.getContentResolver()
                    .query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onDataSetChanged() {
            Log.e(TAG, "onDataSetChanged: ");
            if (mCursor != null) {
                mCursor.close();
            }

            final long identityToken = Binder.clearCallingIdentity();
            mCursor = mContext.getContentResolver()
                    .query(ArticleContract.ArticleEntry.CONTENT_URI, null, null, null, null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (mCursor != null) {
                mCursor.close();
                mCursor = null;
            }
        }

        @Override
        public int getCount() {
            return mCursor == null ? 0 : 10;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.e(TAG, "getViewAt: " + position);
            if (position == AdapterView.INVALID_POSITION ||
                    mCursor == null || !mCursor.moveToPosition(position)) {
                return null;
            }

            Log.d(TAG, "Cursor position is : " + mCursor.getPosition());
            final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_detail_list_item);

            try {
                Picasso picasso = Picasso.with(mContext);
                picasso.setLoggingEnabled(true);
                Bitmap bitmap = picasso.load(mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL))
                        .placeholder(R.color.photo_placeholder)
                        .error(R.color.primary_dark)
                        .get();
                remoteViews.setImageViewBitmap(R.id.widget_photo, bitmap);
                remoteViews.setTextViewText(R.id.widget_article_title, mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));
                remoteViews.setTextViewText(R.id.widget_article_date, mCursor.getString(ArticleLoader.Query.COLUMN_PUBLISH_DATE));

                final Intent fillInIntent = new Intent();
                fillInIntent.setAction(MonolithWidget.ACTION_WIDGET_CLICK);
                fillInIntent.putExtra(getResources().getString(R.string.title), mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));
                fillInIntent.putExtra(getResources().getString(R.string.date), mCursor.getString(ArticleLoader.Query.COLUMN_PUBLISH_DATE));
                fillInIntent.putExtra(getResources().getString(R.string.image), mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL));
                fillInIntent.putExtra(getResources().getString(R.string.description), mCursor.getString(ArticleLoader.Query.COLUMN_DESCRIPTION));
                fillInIntent.putExtra(getResources().getString(R.string.link), mCursor.getString(ArticleLoader.Query.COLUMN_LINK));
                remoteViews.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

            } catch (Exception e) {
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}