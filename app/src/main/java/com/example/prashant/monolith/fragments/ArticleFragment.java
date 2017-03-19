package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.ArticleAdapter;

public class ArticleFragment extends Fragment {

    private final String TAG = ArticleFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    public ArticleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_article, container, false);
        adapter = new ArticleAdapter();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_article);
        mRecyclerView.setAdapter(adapter);

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
