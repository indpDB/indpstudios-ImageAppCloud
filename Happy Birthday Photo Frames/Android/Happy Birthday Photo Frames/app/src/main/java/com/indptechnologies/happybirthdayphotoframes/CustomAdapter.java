package com.indptechnologies.happybirthdayphotoframes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    Integer[] mThumbIds;

    public CustomAdapter(Context frames, Integer[] mThumbIds) {
        mContext = frames;
        this.mThumbIds = mThumbIds;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            row = new View(this.mContext);
            this.inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = this.inflater.inflate(R.layout.grid_row, parent, false);
        } else {
            row = convertView;
        }
        ImageView imageView = (ImageView) row.findViewById(R.id.grid_image);
        Glide.with(mContext).load(mThumbIds[position]).into(imageView);
        return row;
    }

}
