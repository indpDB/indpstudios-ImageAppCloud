package com.indptechnologies.happybirthdayphotoframes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SINarayanReddy on 01-11-2017.
 */

class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.MyContactAdapterView> {

    private ArrayList<Demo> ar;
    private Context context;

    public MyContactAdapter(Context context, ArrayList<Demo> ar) {
        this.context = context;
        this.ar = ar;

    }

    @Override
    public MyContactAdapterView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_item, parent, false);

        return new MyContactAdapterView(view);
    }

    @Override
    public void onBindViewHolder(MyContactAdapterView holder, int position) {
        String name = ar.get(position).getName();
        //  Only 9 characters displayed...
        holder.textView.setText(name.substring(0, 16) + "...");
        //  All characters displayed...
       // holder.textView.setText(name);
        Picasso.with(context).load(ar.get(position).getImage()).placeholder(R.mipmap.noimg).error(R.mipmap.noimg).into(holder.imageView);
        //Toast.makeText(context, ""+ar.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class MyContactAdapterView extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
       // RotateAnimation anim;
        Animation ani;
        public MyContactAdapterView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);

            ani = new RotateAnimation(
                    0, /* from degree*/
                    -39, /* to degree */
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ani.setDuration(10000);
            imageView.startAnimation(ani);


            /*imageView.animate().rotation(90).setDuration(10000);
            imageView.animate().rotation(-90).setDuration(10000);*/
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
           try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ar.get(pos).getUrl())));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ar.get(pos).getUrl())));
            }
        }
    }
}
