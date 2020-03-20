package com.indptechnologies.happybirthdayphotoframes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Frames extends AppCompatActivity {

    private GridView gridView;
    CustomAdapter adapter;
    public Integer[] mThumbIds = new Integer[]
            {
                    R.drawable.frame1,
                    R.drawable.frame2,
                    R.drawable.frame3,
                    R.drawable.frame4,
                    R.drawable.frame5,
                    R.drawable.frame6,
                    R.drawable.frame7,
                    R.drawable.frame8,
                    R.drawable.frame9,
                    R.drawable.frame10,
                    R.drawable.frame11,
                    R.drawable.frame12,
                    R.drawable.frame13,
                    R.drawable.frame14,
                    R.drawable.frame15,
                    R.drawable.frame16,
                    R.drawable.frame17,
                    R.drawable.frame18,
                    R.drawable.frame19,
                    R.drawable.frame20,
                    R.drawable.frame21,
                    R.drawable.frame22,
                    R.drawable.frame23,
                    R.drawable.frame24,
                    R.drawable.frame25,
                    R.drawable.frame26,
                    R.drawable.frame27,
                    R.drawable.frame28,
                    R.drawable.frame29,
                    R.drawable.frame30,
                    R.drawable.frame31,
                    R.drawable.frame32,
                    R.drawable.frame33,
                    R.drawable.frame34,
                    R.drawable.frame35,
                    R.drawable.frame36,
                    R.drawable.frame37,
                    R.drawable.frame38,
                    R.drawable.frame39,
                    R.drawable.frame40,
                    R.drawable.frame41,
                    R.drawable.frame42,
                    R.drawable.frame43,
                    R.drawable.frame44,
                    R.drawable.frame45,
                    R.drawable.frame46,
                    R.drawable.frame47,
                    R.drawable.frame48,
                    R.drawable.frame49,
                    R.drawable.frame50,
                    R.drawable.frame51,
                    R.drawable.frame52,
                    R.drawable.frame53,
                    R.drawable.frame54,
                    R.drawable.frame55,
                    R.drawable.frame56,
                    R.drawable.frame57,
                    R.drawable.frame58,
                    R.drawable.frame59,
                    R.drawable.frame60,
                    R.drawable.frame61,
                    R.drawable.frame62,
                    R.drawable.frame63,
                    R.drawable.frame64,
                    R.drawable.frame65,
                    R.drawable.frame66,
                    R.drawable.frame67,
                    R.drawable.frame68,
                    R.drawable.frame69,
                    R.drawable.frame70,
                    R.drawable.frame71,
                    R.drawable.frame72,
                    R.drawable.frame73,
                    R.drawable.frame74,
                    R.drawable.frame75,
                    R.drawable.frame76,
                    R.drawable.frame77,
                    R.drawable.frame78,
                    R.drawable.frame79,
                    R.drawable.frame80,
                    R.drawable.frame81,
                    R.drawable.frame82,
                    R.drawable.frame83,
                    R.drawable.frame84,
                    R.drawable.frame85,
                    R.drawable.frame86,
                    R.drawable.frame87,
                    R.drawable.frame88,
                    R.drawable.frame89,
                    R.drawable.frame90,
                    R.drawable.frame91,
                    R.drawable.frame92,
                    R.drawable.frame93,
                    R.drawable.frame94,
                    R.drawable.frame95,
                    R.drawable.frame96,
                    R.drawable.frame97,
                    R.drawable.frame98,
                    R.drawable.frame99,
                    R.drawable.frame100,
                    R.drawable.frame101,
                    R.drawable.frame102,
                    R.drawable.frame103,
                    R.drawable.frame104,
                    R.drawable.frame105,
                    R.drawable.frame106,
                    R.drawable.frame107,
                    R.drawable.frame108,
                    R.drawable.frame109,
                    R.drawable.frame110,
                    R.drawable.frame111,
                    R.drawable.frame112,
                    R.drawable.frame113,
                    R.drawable.frame114,
                    R.drawable.frame115,
                    R.drawable.frame116,
                    R.drawable.frame117,
                    R.drawable.frame118,
                    R.drawable.frame119

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frames);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new CustomAdapter(this, mThumbIds);
        getSupportActionBar().setTitle("Select a Frame");
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int imageRes = mThumbIds[i];
                Intent intent = new Intent(Frames.this, EditFrames.class);
                intent.putExtra("imageId", imageRes);
                Frames.this.startActivity(intent);
            }
        });

    }
}
