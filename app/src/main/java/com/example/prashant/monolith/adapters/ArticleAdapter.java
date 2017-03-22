package com.example.prashant.monolith.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.articleData.ArticleLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private Cursor mCursor;

    public ArticleAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);

        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "this is an article", Toast.LENGTH_SHORT).show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));

        // TODO: add date here
        holder.subtitleView.setText("Subtitle");

        ImageView imageView = holder.thumbnail;
        Picasso.with(imageView.getContext()).load(mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL))
                .placeholder(R.color.accent)
                .error(R.color.primary_dark)
                .into(imageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
         TextView titleView;
         TextView subtitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            titleView = (TextView) itemView.findViewById(R.id.article_title);
            subtitleView = (TextView) itemView.findViewById(R.id.article_subtitle);
        }
    }
}
