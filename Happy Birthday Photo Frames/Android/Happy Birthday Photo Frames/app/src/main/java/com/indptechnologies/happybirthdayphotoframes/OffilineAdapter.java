package com.indptechnologies.happybirthdayphotoframes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sunny on 03/11/17.
 */

class OffilineAdapter extends RecyclerView.Adapter<OffilineAdapter.MyContactAdapterView> {

    private ArrayList<Demo> ar;
    private Context context;
    // Keep all Images in array
    private Integer[] imagesArray = {

    };

    public OffilineAdapter(Context context, ArrayList<Demo> ar) {
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
        holder.textView.setText(name.substring(0, 5) + "...");
        holder.imageView.setImageResource(imagesArray[position]);
        //Picasso.with(context).load(ar.get(position).getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imageView);
        //Toast.makeText(context, ""+ar.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class MyContactAdapterView extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public MyContactAdapterView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Toast.makeText(context, "Check your Internet connection. ", Toast.LENGTH_SHORT).show();
            //final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
//            try {
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ar.get(pos).getUrl())));
//            } catch (android.content.ActivityNotFoundException anfe) {
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ar.get(pos).getUrl())));
//            }
        }
    }
}
