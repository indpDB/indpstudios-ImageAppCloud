package com.indptechnologies.happybirthdayphotoframes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView editBtn,share,moreapps;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        getSupportActionBar().setTitle("Happy Birthday  Photo Frames");
        findViewById(R.id.imageView).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.moreapps).setOnClickListener(this);

        mAdView = (AdView)findViewById(R.id.banner_AdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
         }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            cursor.close();

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imageView:
                startActivity(new Intent(MainActivity.this, Frames.class));
                break;

            case R.id.share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;

            case R.id.moreapps:
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/dev?id=5079511223465543465"));
                startActivity(i);
                break;
        }
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


  /*  @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(isOnline()) {
            Intent i = new Intent(MainActivity.this, BackPressedActivity.class);
            startActivity(i);
        }
        else{
            finish();

        }

    }*/
}




 /*pic1.setOnTouchListener(new View.OnTouchListener() {
@Override
public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
        savedMatrix.set(matrix);
        start.set(event.getX(), event.getY());
//                        Log.d(TAG, "mode=DRAG");
        mode = DRAG;
        break;
        case MotionEvent.ACTION_POINTER_DOWN:
        oldDist = spacing(event);
//                        Log.d(TAG, "oldDist=" + oldDist);
        if (oldDist > 10f) {
        savedMatrix.set(matrix);
        midPoint(mid, event);
        mode = ZOOM;
//                            Log.d(TAG, "mode=ZOOM");
        }
        break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        mode = NONE;
//                        Log.d(TAG, "mode=NONE");
        break;
        case MotionEvent.ACTION_MOVE:
        if (mode == DRAG) {
        // ...
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - start.x, event.getY()
        - start.y);
        } else if (mode == ZOOM) {
        float newDist = spacing(event);
//                            Log.d(TAG, "newDist=" + newDist);
        if (newDist > 10f) {
        matrix.set(savedMatrix);
        float scale = newDist / oldDist;
        matrix.postScale(scale, scale, mid.x, mid.y);
        }
        }
        break;
        }

        view.setImageMatrix(matrix);
        return true;
        }

private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
        "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
        || actionCode == MotionEvent.ACTION_POINTER_UP) {
        sb.append("(pid ").append(
        action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
        sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
        sb.append("#").append(i);
        sb.append("(pid ").append(event.getPointerId(i));
        sb.append(")=").append((int) event.getX(i));
        sb.append(",").append((int) event.getY(i));
        if (i + 1 < event.getPointerCount())
        sb.append(";");
        }
        sb.append("]");
//                Log.d(TAG, sb.toString());
        }

*//**
 * Determine the space between the first two fingers
 *//*
private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
        }

*//**
 * Calculate the mid point of the first two fingers
 *//*
private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
        }


        });
        pic2.setOnTouchListener(new View.OnTouchListener() {
@Override
public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
        savedMatrix.set(matrix);
        start.set(event.getX(), event.getY());
//                        Log.d(TAG, "mode=DRAG");
        mode = DRAG;
        break;
        case MotionEvent.ACTION_POINTER_DOWN:
        oldDist = spacing(event);
//                        Log.d(TAG, "oldDist=" + oldDist);
        if (oldDist > 10f) {
        savedMatrix.set(matrix);
        midPoint(mid, event);
        mode = ZOOM;
//                            Log.d(TAG, "mode=ZOOM");
        }
        break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        mode = NONE;
//                        Log.d(TAG, "mode=NONE");
        break;
        case MotionEvent.ACTION_MOVE:
        if (mode == DRAG) {
        // ...
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - start.x, event.getY()
        - start.y);
        } else if (mode == ZOOM) {
        float newDist = spacing(event);
//                            Log.d(TAG, "newDist=" + newDist);
        if (newDist > 10f) {
        matrix.set(savedMatrix);
        float scale = newDist / oldDist;
        matrix.postScale(scale, scale, mid.x, mid.y);
        }
        }
        break;
        }

        view.setImageMatrix(matrix);
        return true;
        }


        });*/