package com.example.prashant.monolith.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.prashant.monolith.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<String> array = null;

    public GalleryAdapter(Context c, ArrayList<String> images) {
        mContext= c;
        array = images;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null)
        {
            imageView = new ImageView(mContext);
        }
        else{
            imageView = (ImageView) convertView;
        }

        imageView.setAdjustViewBounds(true);

        Picasso.with(mContext)
                .load(array.get(position))
                .placeholder(R.drawable.bg)
                .into(imageView);

        return imageView;
    }
}
