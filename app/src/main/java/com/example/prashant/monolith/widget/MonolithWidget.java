package com.example.prashant.monolith.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.prashant.monolith.ArticleDetailActivity;
import com.example.prashant.monolith.MainActivity;
import com.example.prashant.monolith.R;
/**
 * Implementation of App Widget functionality.
 */
public class MonolithWidget extends AppWidgetProvider {

    private String TAG = getClass().getName();
    public static final String ACTION_WIDGET_CLICK = "com.example.prashant.monolith.widget.ACTION_TOAST";

    static Intent intent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.monolith_widget);

        // Intent to launch MainActivity
        final Intent onItemClick = new Intent(context, MonolithWidget.class);
        onItemClick.setAction(ACTION_WIDGET_CLICK);
        if (intent != null) {
            onItemClick.setData(intent.getData());
            Log.e("Content not null", "updateAppWidget: " + intent.getData() );
        }
        PendingIntent onClickPendingIntent = PendingIntent
                .getBroadcast(context, 0, onItemClick,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list,
                onClickPendingIntent);
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));
        views.setEmptyView(R.id.widget_list, R.id.widget_empty);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            Log.e(TAG, "onUpdate: " + appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        MonolithWidget.intent = intent;
        Log.d(TAG, "onReceive is called");
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.monolith_widget);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        }

        if (intent.getAction().equals(ACTION_WIDGET_CLICK)) {
            Log.e(TAG, "onReceive: Intent has ACTION_WIDGET_CLICK");
            Bundle bundle = new Bundle();
            bundle.putString(context.getResources().getString(R.string.title), intent.getStringExtra(context.getResources().getString(R.string.title)));
            bundle.putString(context.getResources().getString(R.string.date), intent.getStringExtra("date"));
            bundle.putString(context.getResources().getString(R.string.image), intent.getStringExtra("image"));
            bundle.putString(context.getResources().getString(R.string.description), intent.getStringExtra("description"));
            bundle.putString(context.getResources().getString(R.string.link), intent.getStringExtra(context.getResources().getString(R.string.link)));
            Intent intent1 = new Intent(context, ArticleDetailActivity.class);
            intent1.putExtras(bundle);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}

