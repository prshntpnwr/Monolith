package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.ArticleAdapter;
import com.example.prashant.monolith.articleObject.ArticleInterface;
import com.example.prashant.monolith.articleObject.Rss;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ArticleFragment extends Fragment {

    private final String TAG = ArticleFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    public ArticleAdapter adapter;
    public ArrayList<String> imageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_article, container, false);
        adapter = new ArticleAdapter();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_article);
        mRecyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);

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
                Log.d(TAG + " Article response ", String.valueOf(response.body().getChannel().getItem().size()));
//                Log.d(TAG + "Response data :", String.valueOf(response.body().getChannel().getItem()[0].getthumbnail().getUrl()));
//                Log.d(TAG + "Response data :", response.body().getChannel().getItem()[0].getTitle());
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG + " failed response from ", "ArticleFetchTask");
            }
        });
    }
}
