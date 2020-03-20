package com.indptechnologies.happybirthdayphotoframes;

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


public class Smile extends Fragment {

    ArrayList<String> smiles ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recent_frag, container, false);
        String[] images = new String[0];
        try {
            images = getContext().getAssets().list("balloon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        smiles = new ArrayList<String>(Arrays.asList(images));
        CustomGrid adapter = new CustomGrid(getActivity(), smiles);

        GridView grid = (GridView) rootView.findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                int drawable = imageId[position];
                InputStream inputstream= null;
                try {
                    inputstream = getContext().getAssets().open("balloon/"
                            +smiles.get(position));
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

        ArrayList<String> smiles;

        public CustomGrid(Context c, ArrayList<String> smiles ) {
            mContext = c;
            this.smiles = smiles;

        }

        @Override
        public int getCount() {

            return smiles.size();
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
            InputStream inputstream= null;
            try {
                inputstream = mContext.getAssets().open("balloon/"
                        +smiles.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageView.setImageDrawable(drawable);
            return grid;
        }
    }
}
