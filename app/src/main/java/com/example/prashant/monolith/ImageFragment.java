package com.example.prashant.monolith;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.monolith.data.GalleryContract;
import com.squareup.picasso.Picasso;

public class ImageFragment extends Fragment {

    private static final String TAG = ImageFragment.class.getSimpleName();


    public ImageFragment() {
    }

    public ImageView imageView;
    public View mRootView;
    public Toolbar toolbar;
    public Intent intent;
    public String image;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            if (!image.isEmpty()) {
                savedInstanceState.getSerializable("IMAGE");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
        image = intent.getStringExtra("image");
        outState.putSerializable("IMAGE", image);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_image, container, false);
            toolbar = (Toolbar) mRootView.findViewById(R.id.detail_toolbar);

            imageView = (ImageView) mRootView.findViewById(R.id.image);

            final FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like_outline));

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: add image/details to db
                   // loadDataToDB();
                    fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_like));
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

        intent = getActivity().getIntent();

        if (intent != null) {
            image = intent.getStringExtra("image");

            Picasso.with(getActivity())
                    .load(image)
                    .into(imageView);
        }
    }

    private void setupToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(ContextCompat
                    .getDrawable(getActivity(), R.drawable.ic_back));
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
