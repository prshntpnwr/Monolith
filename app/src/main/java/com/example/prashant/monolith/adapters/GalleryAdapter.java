package com.example.prashant.monolith.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.data.GalleryContract;
import com.example.prashant.monolith.data.GalleryLoader;
import com.squareup.picasso.Picasso;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public GalleryAdapter(Context context, Cursor cursor) {
        mContext = context;
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
        //
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view= inflater.inflate(R.layout.gridview_item, parent, false);
        final ViewHolder vh = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        GalleryContract.GalleryEntry.buildGalleryUri(
                                getItemId(vh.getAdapterPosition())));

                mContext.startActivity(intent);
                }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        ImageView imageView = holder.image;

        //loading images using picasso
        Picasso.with(mContext).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
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

