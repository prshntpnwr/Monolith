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
    public static final String EXTRA_STRING = "com.example.prashant.monolith.widget.EXTRA_STRING";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.monolith_widget);

        // Intent to launch MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);

        // Intent to launch ArticleDetailActivity
        Intent clickIntentTemplate = new Intent(context, ArticleDetailActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);

        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));

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
        Log.d(TAG, "onReceive is called");
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            Log.e("Widget", "onReceive: ");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.monolith_widget);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        }

        if (intent.getAction().equals(ACTION_WIDGET_CLICK)) {

            Bundle bundle = new Bundle();
            bundle.putString("title", intent.getStringExtra("title"));
            bundle.putString("date", intent.getStringExtra("date"));
            bundle.putString("image", intent.getStringExtra("image"));
            bundle.putString("description", intent.getStringExtra("description"));
            bundle.putString("link", intent.getStringExtra("link"));
            Intent intent1 = new Intent(context, ArticleDetailActivity.class);
            intent.putExtras(bundle);

        }
    }
}

