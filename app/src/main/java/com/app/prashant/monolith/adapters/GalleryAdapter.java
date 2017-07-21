package com.app.prashant.monolith.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.prashant.monolith.R;
import com.app.prashant.monolith.galleryData.GalleryContract;
import com.app.prashant.monolith.galleryData.GalleryLoader;
import com.app.prashant.monolith.ui.ImageDetailActivity;
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

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_item, parent, false);

        final ViewHolder vh = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        GalleryContract.GalleryEntry.buildGalleryUri(getItemId(vh.getAdapterPosition())));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    final ImageView image = (ImageView) view.findViewById(R.id.picture);

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                            ((Activity) view.getContext(), image, view.getContext()
                                    .getResources().getString(R.string.transition_gallery_photo) + String.valueOf(vh.getAdapterPosition()));
                    view.getContext().startActivity(intent, optionsCompat.toBundle());
                } else {
                    view.getContext().startActivity(intent);
                }
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        ImageView imageView = holder.image;

        //loading images using picasso
        Picasso.with(imageView.getContext()).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
                .placeholder(R.color.photo_placeholder)
                .error(R.color.primary_dark)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.picture);
        }
    }
}

