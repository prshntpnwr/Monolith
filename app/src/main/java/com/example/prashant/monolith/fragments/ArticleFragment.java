package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.ArticleAdapter;
import com.example.prashant.monolith.articleObject.ArticleInterface;
import com.example.prashant.monolith.articleObject.RelatedTopic;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        String API_BASE_URL = "https://duckduckgo.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleInterface service = retrofit.create(ArticleInterface.class);

        Call<RelatedTopic> call = service.results("nasa");

        call.enqueue(new Callback<RelatedTopic>() {

            @Override
            public void onResponse(Call<RelatedTopic> call, Response<RelatedTopic> response) {
                Log.d(TAG + "DDG Response goes here ", response.toString());
                int length = response.body().getTopics().size();
                Log.d(TAG + "response length is - ", String.valueOf(length));
//
//                imageList.add(response.body().getTopics().get(0).getResult());
//                Log.d(TAG + "response list is - ", String.valueOf(imageList));
            }

            @Override
            public void onFailure(Call<RelatedTopic> call, Throwable t) {
                Log.d(TAG + " Fail response from ", "duckduckgo");
            }
        });
    }
}
