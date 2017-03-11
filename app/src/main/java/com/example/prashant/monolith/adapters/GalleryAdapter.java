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
import com.example.prashant.monolith.ImageActivity;
import com.example.prashant.monolith.R;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view= inflater.inflate(R.layout.gridview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {

        // will fix this soon
        ImageView imageView=holder.image;

//        final Bundle bundle = new Bundle();
////        //put some tag and ?
////        bundle.putSerializable(IMAGE_TAG, "");
//        bundle.putInt(POSITION, position);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(view.getContext(), ImageActivity.class);
//                intent.putExtras(bundle);
//                view.getContext().startActivity(intent);
//            }
//        });

        //load images using picasso
        Picasso.with(mContext).load(mCursor.getString(position))
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
            image=(ImageView) itemView.findViewById(R.id.image);
        }
    }
}

