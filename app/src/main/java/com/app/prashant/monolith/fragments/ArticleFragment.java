package com.app.prashant.monolith.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.prashant.monolith.R;
import com.app.prashant.monolith.adapters.ArticleAdapter;
import com.app.prashant.monolith.articleData.ArticleContract;
import com.app.prashant.monolith.articleData.ArticleLoader;
import com.app.prashant.monolith.articleObject.ArticleInterface;
import com.app.prashant.monolith.articleObject.Rss;
import com.app.prashant.monolith.ui.Utility;
import com.app.prashant.monolith.widget.MonolithWidget;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ArticleFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = ArticleFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    View mRootView;
    FrameLayout mEmptyView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    Handler handler = new Handler();
    boolean shouldHandlerRunAgain = true;
    private Bundle savedInstanceState;
    private final int HANDLER_DELAY_TIME = 1000;

    Runnable task = new Runnable() {
        @Override
        public void run() {
            runNetworkTask();
            if (shouldHandlerRunAgain)
                handler.postDelayed(task, HANDLER_DELAY_TIME);
        }
    };

    private void runNetworkTask() {
        if (Utility.isNetworkAvailable(getContext())) {
            shouldHandlerRunAgain = false;
            handler.removeCallbacks(task);
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if (savedInstanceState == null) {
                ArticleFetchTask();
            }
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(ViewGroup.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize loader here
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        mRootView = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_article);
        mEmptyView = (FrameLayout) mRootView.findViewById(R.id.empty_container);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        ArticleFetchTask();

        return mRootView;
    }

    @Override
    public void onRefresh() {
        runNetworkTask();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(task);
    }

    public void ArticleFetchTask() {

        if (Utility.isNetworkAvailable(getContext())) {
            mEmptyView.setVisibility(View.GONE);

            String API_BASE_URL = "https://rss.sciencedaily.com/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            ArticleInterface service = retrofit.create(ArticleInterface.class);

            Call<Rss> call = service.results1();

            call.enqueue(new Callback<Rss>() {
                @Override
                public void onResponse(Call<Rss> call, Response<Rss> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG + " Article response ", response.message());
                    Log.d(TAG + " Article response size", String.valueOf(response.body().getChannel().getItem().size()));

                    String title, description, image_url, pub_date, link;

                    int deleteRows = getContext().getContentResolver()
                            .delete(ArticleContract.ArticleEntry.CONTENT_URI, null, null);

                    Log.d(TAG + " deleted rows ", Integer.toString(deleteRows));

                    int length = response.body().getChannel().getItem().size();

                    for (int i = 0; i < length; i++) {

                        title = response.body().getChannel().getItem().get(i).getTitle();
                        description = response.body().getChannel().getItem().get(i).getDescription();
                        image_url = response.body().getChannel().getItem().get(i).getthumbnail().getUrl();
                        pub_date = response.body().getChannel().getItem().get(i).getPubDate();
                        link = response.body().getChannel().getItem().get(i).getLink();

                        Log.d(TAG + " title : ", title);
                        Log.d(TAG + " description : ", description);
                        Log.d(TAG + " image url : ", image_url);
                        Log.d(TAG + " publish date : ", pub_date);
                        Log.d(TAG + " link : ", link);

                        Uri uri = ArticleContract.ArticleEntry.CONTENT_URI;
                        ContentValues contentValues = new ContentValues();
                        final ContentResolver resolver = getContext().getContentResolver();

                        contentValues.put(ArticleContract.ArticleEntry.COLUMN_TITLE, title);
                        contentValues.put(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION, description);
                        contentValues.put(ArticleContract.ArticleEntry.COLUMN_IMAGE_URL, image_url);
                        contentValues.put(ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE, pub_date);
                        contentValues.put(ArticleContract.ArticleEntry.COLUMN_LINK, link);

                        resolver.insert(uri, contentValues);

                    }
                }

                @Override
                public void onFailure(Call<Rss> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG + " failed response from ", t.getLocalizedMessage());
                }
            });
        } else {
            //tells if the current view is attached to the window
            if (mRootView != null && mRootView.isAttachedToWindow()) {
                mEmptyView.setVisibility(View.VISIBLE);
                final Snackbar snackbar = Snackbar
                        .make(mRootView, getResources().getString(R.string.please_try_again), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                runNetworkTask();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.accent));
                snackbar.show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this.getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArticleAdapter adapter = new ArticleAdapter(data);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        int columnCount = getResources().getInteger(R.integer.article_list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        updateWidget();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    public void updateWidget() {
        Log.d(TAG + "updateWidget", "is called");
        ComponentName name = new ComponentName(this.getContext(), MonolithWidget.class);
        int[] ids = AppWidgetManager.getInstance(this.getContext()).getAppWidgetIds(name);
        Intent intent = new Intent(this.getContext(), MonolithWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getContext().sendBroadcast(intent);
    }
}
