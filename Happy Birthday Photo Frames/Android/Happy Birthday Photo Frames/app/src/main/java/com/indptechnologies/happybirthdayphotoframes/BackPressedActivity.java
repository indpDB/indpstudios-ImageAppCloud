package com.indptechnologies.happybirthdayphotoframes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BackPressedActivity extends Activity implements View.OnClickListener {

    private Dialog dialogDel;
    private ArrayList<Demo> contactList;
    private MyContactAdapter adapter;
    private OffilineAdapter adapterOffLine;
    private static final String ROOT_URL = "http://www.npkrtech.in";
    private RecyclerView rcy;
    private TextView txt;

    private RelativeLayout progressbarRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up1);
        popup();
      /*  txt = (TextView)findViewById(R.id.close1);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent close1= new Intent(BackPressedActivity.this, MainActivity.class);
                startActivity(close1);
            }
        });*/
    }


    private void popup() {

        progressbarRL = (RelativeLayout) findViewById(R.id.progressbarRL);

        rcy = (RecyclerView) findViewById(R.id.recycler);
        Button exitBtn = (Button) findViewById(R.id.exit_Btn);
        Button rateBtn = (Button) findViewById(R.id.rate_Btn);
        Button cancelBtn = (Button) findViewById(R.id.cancel_Btn);
        rcy.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcy.setLayoutManager(layoutManager);
        if (isOnline())
            loadData();
        else
            loadOfflineMode();

        exitBtn.setOnClickListener(this);
        rateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    private void loadOfflineMode() {
        loadOfflineUI();

        /**
         * Binding that List to Adapter
         */
        adapterOffLine = new OffilineAdapter(BackPressedActivity.this, contactList);
        rcy.setAdapter(adapterOffLine);
    }

    private void loadOfflineUI() {
        ArrayList<String> names = new ArrayList<>();

        // 1. Garden Photo Frames New
        names.add("Garden");

        // 2. Photo Cut-Paste Editor & Photo Background Changer
        names.add("Photo");

        // 3. Upgrade Your Androidô Device
        names.add("Upgrade");

        // 4. Flash Alerts On Call And Sms
        names.add("Flash Alerts");

        // 5. Instant Photo Collage Maker
        names.add("Instant Photo");

        // 6. Tattoo My Photo Maker
        names.add("Tattoo My Photo");

        contactList = new ArrayList<>();
        for (String name : names) {
            Demo d = new Demo();
            d.setId(1);
            d.setCreated("");
            d.setImage("");
            d.setModified("");
            d.setName(name.toString());
            d.setStatus(true);
            d.setUrl("");
            d.setUserId(1);

            contactList.add(d);
        }
    }

    private void loadData() {

        progressbarRL.setVisibility(View.VISIBLE);
        /**
         * Get Retrofit Instance
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DemoInterface request = retrofit.create(DemoInterface.class);
        Call<DemoData> call = request.getMyJSON();
        call.enqueue(new Callback<DemoData>() {
            @Override
            public void onResponse(Call<DemoData> call, Response<DemoData> response) {
                //Dismiss Dialog
                //dailog.dismiss();

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    DemoData list = response.body();
                    contactList = new ArrayList<>(Arrays.asList(list.getData()));
                    //txt.setText((CharSequence) response.body());

                    /**
                     * Binding that List to Adapter
                     */
                    adapter = new MyContactAdapter(BackPressedActivity.this, contactList);
                    rcy.setAdapter(adapter);
                    progressbarRL.setVisibility(View.GONE);

                } else {
                    // Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DemoData> call, Throwable t) {

                //dailog.dismiss();
                progressbarRL.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_Btn:
                if (Build.VERSION.SDK_INT >= 16) {
                    finishAffinity();
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
              this.finish();*/

              // System.exit(0);
                //Toast.makeText(this, "You clicked on Exit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate_Btn:
              //  Toast.makeText(this, "You clicked on Rate", Toast.LENGTH_SHORT).show();

                Intent rate = new Intent(Intent.ACTION_VIEW);
                rate.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.npkrtech1.aquariumphotoframes&hl=en"));
                startActivity(rate);
                break;
            case R.id.cancel_Btn:
                Intent close1= new Intent(BackPressedActivity.this, MainActivity.class);
                startActivity(close1);
                break;

            default:
                break;
        }
    }

    /*
* isOnline - Check if there is a NetworkConnection
* @return boolean
*/
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
