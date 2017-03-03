package com.example.prashant.monolith;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter
{
    private Context mContext;

    public MyAdapter(Context c) {
        mContext= c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
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

        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(mThumbIds[position]);
        return imageView;

    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.bg, R.drawable.bg,
            R.drawable.bg, R.drawable.bg,
            R.drawable.bg, R.drawable.bg,
            R.drawable.bg, R.drawable.bg,

    };
}
