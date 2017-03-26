package com.example.prashant.monolith.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.ArticleAdapter;
import com.example.prashant.monolith.articleData.ArticleContract;
import com.example.prashant.monolith.articleData.ArticleLoader;
import com.example.prashant.monolith.articleObject.ArticleInterface;
import com.example.prashant.monolith.articleObject.Rss;
import com.example.prashant.monolith.widget.MonolithWidget;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ArticleFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = ArticleFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private Cursor mCursor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize loader here
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_article);
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArticleFetchTask();
    }

    public void ArticleFetchTask() {
        String API_BASE_URL = "https://rss.sciencedaily.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ArticleInterface service = retrofit.create(ArticleInterface.class);

        Call<Rss> call = service.results();

        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG + " Article response ", response.message());
                Log.d(TAG + " Article response size", String.valueOf(response.body().getChannel().getItem().size()));

                String title;
                String description;
                String image_url;
                String pub_date;
                String link;

                int deleteRows = getActivity().getContentResolver()
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
                    final ContentResolver resolver = getActivity().getContentResolver();

                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_TITLE, title);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION, description);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_IMAGE_URL, image_url);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE, pub_date);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_LINK, link);

                    resolver.insert(uri, contentValues);
                    mCursor = resolver.query(uri, new String[]{
                                    ArticleContract.ArticleEntry.COLUMN_TITLE,
                                    ArticleContract.ArticleEntry.COLUMN_DESCRIPTION,
                                    ArticleContract.ArticleEntry.COLUMN_IMAGE_URL,
                                    ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE,
                                    ArticleContract.ArticleEntry.COLUMN_LINK},
                            null,
                            null,
                            null);
                }
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.e(TAG + " failed response from ", t.getLocalizedMessage());
            }
        });
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
        udpateWidget();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    public void udpateWidget() {
        Log.d(TAG + "udpateWidget", "is called");
        ComponentName name = new ComponentName(this.getContext(), MonolithWidget.class);
        int[] ids = AppWidgetManager.getInstance(this.getContext()).getAppWidgetIds(name);
        Intent intent = new Intent(this.getContext(), MonolithWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getContext().sendBroadcast(intent);
    }
}
