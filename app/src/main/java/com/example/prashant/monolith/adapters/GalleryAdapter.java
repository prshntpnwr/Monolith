package com.example.prashant.monolith.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.galleryData.GalleryContract;
import com.example.prashant.monolith.galleryData.GalleryLoader;
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
    public GalleryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_item, parent, false);

        final ViewHolder vh = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //explicit intent implementation
//                mCursor.moveToPosition(vh.getAdapterPosition());
//                Intent intent = new Intent(parent.getContext(), ImageDetailActivity.class);
//                intent.putExtra("URL",mCursor.getString(mCursor.getColumnIndex(GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH)));
//                parent.getContext().startActivity(intent);

//                String path = mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_PATH);
//
//                Log.d("image url goes here", path);
                parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        GalleryContract.GalleryEntry.buildGalleryUri(getItemId(vh.getAdapterPosition()))));
//                Log.d("onclick image goes here", mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_PATH));

            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        ImageView imageView = holder.image;

        //loading images using picasso
        Picasso.with(imageView.getContext()).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
                .placeholder(R.color.accent)
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

