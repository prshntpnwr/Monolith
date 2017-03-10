package com.example.prashant.monolith;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageFragment extends Fragment{

    public ImageFragment() {
    }

    public ImageView imageView;
    public View mRootView;
    public Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_image, container, false);
        toolbar = (Toolbar) mRootView.findViewById(R.id.detail_toolbar);

        imageView = (ImageView)mRootView.findViewById(R.id.image);

        mRootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: image sharing
            }
        });

        bindViews();
        setupToolbar();

        return mRootView;
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            String image = intent.getStringExtra("image");

            Picasso.with(getActivity())
                    .load(image)
                    .into(imageView);
        }
    }

    private void setupToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            toolbar.setTitle("");
        }
    }
}
