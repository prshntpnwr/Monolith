package com.example.prashant.monolith.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.articleData.ArticleContract;
import com.example.prashant.monolith.articleData.ArticleLoader;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final String TAG = ArticleAdapter.class.getSimpleName();

    private Cursor mCursor;

    public ArticleAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query.COLUMN_ARTICLE_ID);
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);

        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        ArticleContract.ArticleEntry.buildArticleUri(getItemId(vh.getAdapterPosition())));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    final ImageView image = (ImageView) view.findViewById(R.id.thumbnail);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                            ((Activity) view.getContext(), image, view.getContext()
                                    .getResources().getString(R.string.transition_gallery_photo)+String.valueOf(vh.getAdapterPosition()));
                    view.getContext().startActivity(intent, optionsCompat.toBundle());
                }
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));

        // TODO: format date
        holder.subtitleView.setText(
                mCursor.getString(ArticleLoader.Query.COLUMN_PUBLISH_DATE));

        ImageView imageView = holder.thumbnail;
        //loading images using glide
        Glide.with(imageView.getContext()).load(mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL))
                .placeholder(R.color.accent)
                .error(R.color.primary_dark)
                .into(imageView);

        Log.d(TAG + "Image_url", mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL));
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
