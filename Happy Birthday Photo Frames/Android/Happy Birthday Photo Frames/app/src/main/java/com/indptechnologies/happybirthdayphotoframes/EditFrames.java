package com.indptechnologies.happybirthdayphotoframes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFrames extends AppCompatActivity implements View.OnClickListener{
    AdRequest adRequest;
    private InterstitialAd interstitial,saveinterstitial;
    public static int count1=0;

    private static final int RESULT_LOAD_IMAGE2 =2 ;
    private static final int RESULT_LOAD_TEXT = 300;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_LOAD_STICKER = 4;
    private ImageView pic1,  textImage;
    private ImageView galleryBtn, font, frames, saveImage,bgFrame,text6;
    private HorizontalScrollView bgScroll;
    RelativeLayout addText;
    private static final String TAG1 = "EditFrames Activity";

    private ProgressDialog mSaveProgressDialog;
    private AlertDialog mSaveSuccessDialog;
    private Uri mSavedImageUri;
    private Handler mHandler;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    Matrix matrix1 = new Matrix();
    Matrix savedMatrix1 = new Matrix();

    //3 states of mode on touch
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    // Mime type / format / extension we use (must be self-consistent!)
    private static final String SAVED_IMAGE_EXTENSION = ".png";
    private static final Bitmap.CompressFormat SAVED_IMAGE_COMPRESS_FORMAT =
            Bitmap.CompressFormat.PNG;
    private static final String SAVED_IMAGE_MIME_TYPE = "image/png";
    private RelativeLayout frameLayout;
    private String mSavedImageFilename;
    private MediaScannerConnection mMediaScannerConnection;

    ClipArt ca;
    int count = 1000;
    public Dialog dialog;
public static int count6=0;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_frames);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
       initViews();
        mHandler = new Handler();
        if(count6==0) {
            showOverLay();
            count6++;
        }
        else{

        }
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial= new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6443269753862035/2469578379");

        interstitial.loadAd(adRequest);
        saveinterstitial = new InterstitialAd(this);
        saveinterstitial.setAdUnitId("ca-app-pub-6443269753862035/2469578379");

        saveinterstitial.loadAd(adRequest);
    }
    private void showOverLay(){

        final Dialog dialog = new Dialog(context, R.style.Transparent);

        dialog.setContentView(R.layout.transparent);

        RelativeLayout layout = (RelativeLayout) dialog.findViewById(R.id.transparent);

        layout.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                dialog.dismiss();

            }

        });

        dialog.show();

    }
    private void initViews() {
        pic1 = (ImageView) findViewById(R.id.pic1);
       // pic2 = (ImageView) findViewById(R.id.pic2);
        galleryBtn = (ImageView) findViewById(R.id.galleryBtn);
        font = (ImageView) findViewById(R.id.font_set);
        text6 = (ImageView) findViewById(R.id.font_set6);
        saveImage = (ImageView) findViewById(R.id.saveImage);
        frames = (ImageView) findViewById(R.id.frameButton);
        bgFrame = (ImageView) findViewById(R.id.bgFrame);
       // stickerImage6 = (ImageView) findViewById(R.id.stickerImage6);
        frameLayout = (RelativeLayout) findViewById(R.id.frameLayout);
        bgScroll = (HorizontalScrollView) findViewById(R.id.bgScroll);
        addText = (RelativeLayout) findViewById(R.id.addText);
        findViewById(R.id.shareImage).setOnClickListener(this);
        bgFrame.setImageResource(getIntent().getExtras().getInt("imageId"));
        bgFrame.setScaleType(ImageView.ScaleType.FIT_XY);
        pic1.setOnClickListener(this);
       // pic2.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);
        font.setOnClickListener(this);
        saveImage.setOnClickListener(this);
       text6.setOnClickListener(this);
        frames.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fr1:
                this.bgFrame.setImageResource(R.drawable.frame1);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr2:
                this.bgFrame.setImageResource(R.drawable.frame2);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr3:
                this.bgFrame.setImageResource(R.drawable.frame3);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr4:
                this.bgFrame.setImageResource(R.drawable.frame4);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr5:
                this.bgFrame.setImageResource(R.drawable.frame5);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr6:
                this.bgFrame.setImageResource(R.drawable.frame6);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr7:
                this.bgFrame.setImageResource(R.drawable.frame7);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr8:
                this.bgFrame.setImageResource(R.drawable.frame8);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr9:
                this.bgFrame.setImageResource(R.drawable.frame9);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr10:
                this.bgFrame.setImageResource(R.drawable.frame10);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr11:
                this.bgFrame.setImageResource(R.drawable.frame11);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr12:
                this.bgFrame.setImageResource(R.drawable.frame12);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr13:
                this.bgFrame.setImageResource(R.drawable.frame13);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr14:
                this.bgFrame.setImageResource(R.drawable.frame14);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr15:
                this.bgFrame.setImageResource(R.drawable.frame15);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr16:
                this.bgFrame.setImageResource(R.drawable.frame16);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr17:
                this.bgFrame.setImageResource(R.drawable.frame17);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr18:
                this.bgFrame.setImageResource(R.drawable.frame18);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr19:
                this.bgFrame.setImageResource(R.drawable.frame19);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr20:
                this.bgFrame.setImageResource(R.drawable.frame20);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr21:
                this.bgFrame.setImageResource(R.drawable.frame21);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr22:
                this.bgFrame.setImageResource(R.drawable.frame22);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr23:
                this.bgFrame.setImageResource(R.drawable.frame23);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr24:
                this.bgFrame.setImageResource(R.drawable.frame24);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr25:
                this.bgFrame.setImageResource(R.drawable.frame25);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr26:
                this.bgFrame.setImageResource(R.drawable.frame26);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr27:
                this.bgFrame.setImageResource(R.drawable.frame27);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr28:
                this.bgFrame.setImageResource(R.drawable.frame28);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr29:
                this.bgFrame.setImageResource(R.drawable.frame29);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr30:
                this.bgFrame.setImageResource(R.drawable.frame30);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr31:
                this.bgFrame.setImageResource(R.drawable.frame31);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr32:
                this.bgFrame.setImageResource(R.drawable.frame32);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr33:
                this.bgFrame.setImageResource(R.drawable.frame33);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr34:
                this.bgFrame.setImageResource(R.drawable.frame34);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr35:
                this.bgFrame.setImageResource(R.drawable.frame35);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr36:
                this.bgFrame.setImageResource(R.drawable.frame36);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr37:
                this.bgFrame.setImageResource(R.drawable.frame37);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr38:
                this.bgFrame.setImageResource(R.drawable.frame38);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr39:
                this.bgFrame.setImageResource(R.drawable.frame39);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr40:
                this.bgFrame.setImageResource(R.drawable.frame40);
                this.bgScroll.setVisibility(View.GONE);
                return;

            case R.id.fr41:
                this.bgFrame.setImageResource(R.drawable.frame41);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr42:
                this.bgFrame.setImageResource(R.drawable.frame42);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr43:
                this.bgFrame.setImageResource(R.drawable.frame43);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr44:
                this.bgFrame.setImageResource(R.drawable.frame44);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr45:
                this.bgFrame.setImageResource(R.drawable.frame45);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr46:
                this.bgFrame.setImageResource(R.drawable.frame46);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr47:
                this.bgFrame.setImageResource(R.drawable.frame47);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr48:
                this.bgFrame.setImageResource(R.drawable.frame48);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr49:
                this.bgFrame.setImageResource(R.drawable.frame49);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr50:
                this.bgFrame.setImageResource(R.drawable.frame50);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr51:
                this.bgFrame.setImageResource(R.drawable.frame51);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr52:
                this.bgFrame.setImageResource(R.drawable.frame52);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr53:
                this.bgFrame.setImageResource(R.drawable.frame53);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr54:
                this.bgFrame.setImageResource(R.drawable.frame54);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr55:
                this.bgFrame.setImageResource(R.drawable.frame55);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr56:
                this.bgFrame.setImageResource(R.drawable.frame56);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr57:
                this.bgFrame.setImageResource(R.drawable.frame57);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr58:
                this.bgFrame.setImageResource(R.drawable.frame58);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr59:
                this.bgFrame.setImageResource(R.drawable.frame59);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr60:
                this.bgFrame.setImageResource(R.drawable.frame60);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr61:
                this.bgFrame.setImageResource(R.drawable.frame61);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr62:
                this.bgFrame.setImageResource(R.drawable.frame62);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr63:
                this.bgFrame.setImageResource(R.drawable.frame63);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr64:
                this.bgFrame.setImageResource(R.drawable.frame64);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr65:
                this.bgFrame.setImageResource(R.drawable.frame65);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr66:
                this.bgFrame.setImageResource(R.drawable.frame66);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr67:
                this.bgFrame.setImageResource(R.drawable.frame67);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr68:
                this.bgFrame.setImageResource(R.drawable.frame68);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr69:
                this.bgFrame.setImageResource(R.drawable.frame69);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr70:
                this.bgFrame.setImageResource(R.drawable.frame70);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr71:
                this.bgFrame.setImageResource(R.drawable.frame71);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr72:
                this.bgFrame.setImageResource(R.drawable.frame72);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr73:
                this.bgFrame.setImageResource(R.drawable.frame73);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr74:
                this.bgFrame.setImageResource(R.drawable.frame74);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr75:
                this.bgFrame.setImageResource(R.drawable.frame75);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr76:
                this.bgFrame.setImageResource(R.drawable.frame76);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr77:
                this.bgFrame.setImageResource(R.drawable.frame77);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr78:
                this.bgFrame.setImageResource(R.drawable.frame78);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr79:
                this.bgFrame.setImageResource(R.drawable.frame79);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr80:
                this.bgFrame.setImageResource(R.drawable.frame80);
                this.bgScroll.setVisibility(View.GONE);
                return;

            case R.id.fr81:
                this.bgFrame.setImageResource(R.drawable.frame81);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr82:
                this.bgFrame.setImageResource(R.drawable.frame82);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr83:
                this.bgFrame.setImageResource(R.drawable.frame83);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr84:
                this.bgFrame.setImageResource(R.drawable.frame84);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr85:
                this.bgFrame.setImageResource(R.drawable.frame85);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr86:
                this.bgFrame.setImageResource(R.drawable.frame86);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr87:
                this.bgFrame.setImageResource(R.drawable.frame87);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr88:
                this.bgFrame.setImageResource(R.drawable.frame88);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr89:
                this.bgFrame.setImageResource(R.drawable.frame89);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr90:
                this.bgFrame.setImageResource(R.drawable.frame90);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr91:
                this.bgFrame.setImageResource(R.drawable.frame91);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr92:
                this.bgFrame.setImageResource(R.drawable.frame92);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr93:
                this.bgFrame.setImageResource(R.drawable.frame93);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr94:
                this.bgFrame.setImageResource(R.drawable.frame94);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr95:
                this.bgFrame.setImageResource(R.drawable.frame95);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr96:
                this.bgFrame.setImageResource(R.drawable.frame96);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr97:
                this.bgFrame.setImageResource(R.drawable.frame97);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr98:
                this.bgFrame.setImageResource(R.drawable.frame98);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr99:
                this.bgFrame.setImageResource(R.drawable.frame99);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.fr100:
                this.bgFrame.setImageResource(R.drawable.frame100);
                this.bgScroll.setVisibility(View.GONE);
                return;
            case R.id.galleryBtn:

                attachPhotosClick(RESULT_LOAD_IMAGE);
                //this.dialog.cancel();
                return;

               /* this.bgScroll.setVisibility(View.GONE);
                customDialog();
                return;*/

            case R.id.frameButton:
                this.bgScroll.setVisibility(View.VISIBLE);
                return;

            case R.id.font_set:

                this.bgScroll.setVisibility(View.GONE);
                startActivityForResult(new Intent(EditFrames.this, StickersActivity.class), RESULT_LOAD_STICKER);
                return;

            case R.id.font_set6:
                this.bgScroll.setVisibility(View.GONE);
                startActivityForResult(new Intent(EditFrames.this, FontSettings.class), RESULT_LOAD_TEXT);
                return;
          /*  case R.id.stickerImage6:
                this.bgScroll.setVisibility(View.GONE);
                startActivityForResult(new Intent(EditFrames.this, FontSettings.class), RESULT_LOAD_TEXT);
             //   startActivityForResult(new Intent(EditFrames.this, StickersActivity.class), RESULT_LOAD_STICKER);*/
            case R.id.saveImage:
                displaysavead();

                disableall();
                this.bgScroll.setVisibility(View.GONE);
                NewHolder.saveBitmap = getImageFromView(this.frameLayout);
                saveImageToExternalStorage();
                return;
            case R.id.dialog_pic1:
                attachPhotosClick(RESULT_LOAD_IMAGE);
                this.dialog.cancel();
                return;
            /*case R.id.dialog_pic2:
                attachPhotosClick(RESULT_LOAD_IMAGE2);
                this.dialog.cancel();
                return;*/
            case R.id.shareImage:


                NewHolder.saveBitmap = getImageFromView(this.frameLayout);
                Bitmap bMap = NewHolder.saveBitmap;

                shareImage(bMap);

              //  shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+data.get(pos).getUrl()));

/*
                Intent share = new Intent("android.intent.action.SEND");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bMap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "Photo Frames.png");
                try {
                    f.createNewFile();
                    new FileOutputStream(f).write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                share.putExtra("android.intent.extra.STREAM", Uri.parse("file:///sdcard/Photo Frames.png"));
                startActivity(Intent.createChooser(share, "Share Image"));
                return;
            default:*/
                return;
        }

    }
    private void shareImage(Bitmap bitmap){
        // save bitmap to cache directory
        try {
            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }
    private void saveImageToExternalStorage() {
        saveImage("Img" + new SimpleDateFormat("yyyy-mm-dd-hh.mm").format(new Date()) + ".png");
    }
    public void disableall() {
        for (int i = 0; i < this.addText.getChildCount(); i++) {
            if (this.addText.getChildAt(i) instanceof ClipArt) {
                ((ClipArt) this.addText.getChildAt(i)).disableAll();
            }
        }
    }
    private void saveImage(String fileName) {
        fileName = fileName.concat(".png");
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            Bitmap bMap = NewHolder.saveBitmap;
            File completeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + File.separator + "Photo Frames");
            completeDir.mkdirs();
            File file = new File(completeDir, fileName);
            try {
                OutputStream fOut = new FileOutputStream(file);
                bMap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshGallery1(file);
        } else if ("mounted_ro".equals(state)) {
            Toast.makeText(this, "Oops, the media card is not available for writing.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Problem! The media card is not available. Is it in the phone and mounted?", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshGallery(File createFile) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(createFile));
      // intent.setData(FileProvider.getUriForFile(EditFrames.this, BuildConfig.APPLICATION_ID + ".provider",createFile));

        sendBroadcast(intent);
    }
    private void refreshGallery1(File createFile) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(createFile));
       // intent.setData(FileProvider.getUriForFile(EditFrames.this, BuildConfig.APPLICATION_ID + ".provider",createFile));

        sendBroadcast(intent);
    }
    private Bitmap getImageFromView(RelativeLayout frameLayout) {
        Bitmap bitmap = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Bitmap.Config.ARGB_8888);
        frameLayout.draw(new Canvas(bitmap));
        frameLayout.invalidate();
        return bitmap;
    }

    private void attachPhotosClick(int id) {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), id);
    }


    private void customDialog() {
        dialog = new Dialog(EditFrames.this);
        // it remove the dialog title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set the laytout in the dialog
        dialog.setContentView(R.layout.dialog_layout);
        // set the background partial transparent
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        param.gravity = Gravity.CENTER;

        dialog.setCanceledOnTouchOutside(true);

        View d_pic1 =(View) dialog.findViewById(R.id.dialog_pic1);
        View d_pic2 =(View) dialog.findViewById(R.id.dialog_pic2);
        d_pic1.setOnClickListener(this);
        d_pic2.setOnClickListener(this);
        // it show the dialog box
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.pic1);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    // Handle touch events here...
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            savedMatrix1.set(matrix1);
                            start.set(event.getX(), event.getY());
                            mode = DRAG;
                            lastEvent = null;
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                savedMatrix1.set(matrix1);
                                midPoint(mid, event);
                                mode = ZOOM;
                            }
                            lastEvent = new float[4];
                            lastEvent[0] = event.getX(0);
                            lastEvent[1] = event.getX(1);
                            lastEvent[2] = event.getY(0);
                            lastEvent[3] = event.getY(1);
                            d = rotation(event);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = NONE;
                            lastEvent = null;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mode == DRAG) {
                                // ...
                                matrix1.set(savedMatrix1);
                                matrix1.postTranslate(event.getX() - start.x, event.getY()
                                        - start.y);
                            } else if (mode == ZOOM && event.getPointerCount() == 2) {
                                float newDist = spacing(event);
                                matrix1.set(savedMatrix1);
                                if (newDist > 10f) {
                                    float scale = newDist / oldDist;
                                    matrix1.postScale(scale, scale, mid.x, mid.y);
                                }
                                if (lastEvent != null) {
                                    newRot = rotation(event);
                                    float r = newRot - d;
                                    matrix1.postRotate(r, view.getMeasuredWidth() / 2,
                                            view.getMeasuredHeight() / 2);
                                }
                            }
                            break;
                    }
                    view.setImageMatrix(matrix1);

                    return true;
                }
            });
        }

       /* if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.pic2);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    // Handle touch events here...
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            savedMatrix.set(matrix);
                            start.set(event.getX(), event.getY());
                            mode = DRAG;
                            lastEvent = null;
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                savedMatrix.set(matrix);
                                midPoint(mid, event);
                                mode = ZOOM;
                            }
                            lastEvent = new float[4];
                            lastEvent[0] = event.getX(0);
                            lastEvent[1] = event.getX(1);
                            lastEvent[2] = event.getY(0);
                            lastEvent[3] = event.getY(1);
                            d = rotation(event);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = NONE;
                            lastEvent = null;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mode == DRAG) {
                                // ...
                                matrix.set(savedMatrix);
                                matrix.postTranslate(event.getX() - start.x, event.getY()
                                        - start.y);
                            } else if (mode == ZOOM && event.getPointerCount() == 2) {
                                float newDist = spacing(event);
                                matrix.set(savedMatrix);
                                if (newDist > 10f) {
                                    float scale = newDist / oldDist;
                                    matrix.postScale(scale, scale, mid.x, mid.y);
                                }
                                if (lastEvent != null) {
                                    newRot = rotation(event);
                                    float r = newRot - d;
                                    matrix.postRotate(r, view.getMeasuredWidth() / 2,
                                            view.getMeasuredHeight() / 2);
                                }
                            }
                            break;
                    }
                    view.setImageMatrix(matrix);
                    return true;
                }
            });
        }*/
        if(requestCode == RESULT_LOAD_TEXT && resultCode == RESULT_OK ){
            ca = new ClipArt(this);
            addText.addView(ca);
            addText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EditFrames.this.disableall();
                    return false;
                }
            });
            ca.setId(count++);
            ca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditFrames.this.disableall();
                }
            });

        }
        if (requestCode == RESULT_LOAD_STICKER && resultCode == RESULT_OK) {
            this.ca = new ClipArt(this);
            this.addText.addView(this.ca);
            this.addText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EditFrames.this.disableall();
                    return false;
                }
            });
            ca.setId(count++);
            ca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditFrames.this.disableall();
                }
            });
        }

    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
    @Override
    public void onBackPressed() {
        if(count1==0){
        if (interstitial.isLoaded()) {
            interstitial.show();
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        count1++;
        }
        else{

        }
        }else{
            super.onBackPressed();
        }

    }
    public void displaysavead() {

        if (saveinterstitial.isLoaded()) {

            saveinterstitial.show();


        }
        else{

        }
    }

}
