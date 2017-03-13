package com.example.prashant.monolith.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.monolith.ImageDetailActivity;
import com.example.prashant.monolith.ImageDetailFragment;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.data.GalleryContract;
import com.example.prashant.monolith.data.GalleryLoader;
import com.squareup.picasso.Picasso;

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

    public String getImageUrl(int position){
        mCursor.moveToPosition(position);
        return mCursor.getString(mCursor.getColumnIndex(GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH));
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_item, parent, false);

        final ViewHolder vh = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(vh.getAdapterPosition());
                Intent intent = new Intent(parent.getContext(), ImageDetailActivity.class);
                intent.putExtra("URL", mCursor.getString(mCursor.getColumnIndex(GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH)));
                intent.putExtra("Postion" , GalleryContract.GalleryEntry.buildGalleryUri(
                               getItemId(vh.getAdapterPosition())));
                parent.getContext().startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        GalleryContract.GalleryEntry.buildGalleryUri(
//                                getItemId(vh.getAdapterPosition())));
//                parent.getContext().startActivity(intent);

//                Log.d("hello there ", Long.toString(vh.getAdapterPosition()));
//
//                Log.d("again -------- ", Long.toString(getItemId(vh.getAdapterPosition())));
////

                }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        ImageView imageView = holder.image;

        //loading images using picasso
        Picasso.with(holder.image.getContext()).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
                .placeholder(R.color.accent)
                .error(R.color.primary_dark)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.picture);
        }
    }
}

