package com.indptechnologies.happybirthdayphotoframes;

/**
 * Created by DDR INFO SYSTEM on 16-06-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Candles extends Fragment {
    /*Integer[] imageId = {
    R.drawable.c1,R.drawable.c2,R.drawable.c6,R.drawable.c9,
    R.drawable.c14,R.drawable.c16,R.drawable.c19,R.drawable.cb1,
            R.drawable.cb3,
            R.drawable.bf3,R.drawable.bf7,
            R.drawable.bf10,R.drawable.bf16,R.drawable.bf18,
    };*/
    ArrayList<String> animals;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recent_frag, container, false);
        String[] images = new String[0];
        try {
            images = getContext().getAssets().list("flowers");
        } catch (IOException e) {
            e.printStackTrace();
        }
        animals = new ArrayList<String>(Arrays.asList(images));
        CustomGrid adapter = new CustomGrid(getActivity(), animals);

        GridView grid = (GridView) rootView.findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//        int drawable = imageId[position];
                InputStream inputstream= null;
                try {
                    inputstream = getContext().getAssets().open("flowers/"
                            +animals.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable drawable = Drawable.createFromStream(inputstream, null);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawable);
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                NewHolder.sticker_Image = bitmap;
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();

            }
        });
        return rootView;
    }

    public static class CustomGrid extends BaseAdapter {
        private Context mContext;

        ArrayList<String> animals;

        public CustomGrid(Context c, ArrayList<String> animals ) {
            mContext = c;
            this.animals = animals;

        }

        @Override
        public int getCount() {

            return animals.size();
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

            View grid;
            LayoutInflater inflater;

            if (convertView == null) {
                grid = new View(mContext);
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grid = inflater.inflate(R.layout.sticker_grid, null);

            } else {
                grid = (View) convertView;
            }
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
//            imageView.setImageResource(imageId[position]);
            InputStream inputstream= null;
            try {
                inputstream = mContext.getAssets().open("flowers/"
                        +animals.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageView.setImageDrawable(drawable);
            return grid;
        }
    }
}
