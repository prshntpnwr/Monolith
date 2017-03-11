package com.example.prashant.monolith.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.data.GalleryLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Cursor mCursor;

    public GalleryAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(GalleryLoader.Query.COLUMN_IMAGE_ID);
    }

    private static final String IMAGE_TAG = "Id";
    private static final String POSITION = "position";

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from();

        View view= inflater.inflate(R.layout.gridview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        // will fix this soon
//        ImageView imageView=holder.image;
//        final Bundle args = new Bundle();
//        args.putSerializable(IMAGE_TAG, "");
//        args.putInt(POSITION, position);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(view.getContext(), ImageActivity.class);
//                intent.putExtras(args);
//                view.getContext().startActivity(intent);
//            }
//        });
//
//        //load images using picasso
//        Picasso.with().load(mCursor.get(position))
//                .error(R.drawable.sample)
//                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image=(ImageView) itemView.findViewById(R.id.image);
        }

    }
}

