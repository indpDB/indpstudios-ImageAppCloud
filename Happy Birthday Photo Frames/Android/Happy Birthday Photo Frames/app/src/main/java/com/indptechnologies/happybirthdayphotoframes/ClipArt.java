package com.indptechnologies.happybirthdayphotoframes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ClipArt extends RelativeLayout {

	int baseh;
	int basew;
	int basex;
	int basey;
	ImageButton btndel;
	ImageButton btnrot;
	ImageButton btnscl;
	RelativeLayout clip;
	Context cntx;
	boolean freeze = false;
	int h;
	int i;
	ImageView image;
	String imageUri;
	ImageView imgring;
	boolean isShadow;
	int iv;
	RelativeLayout layBg;
	RelativeLayout layGroup;
	LayoutParams layoutParams;
	public LayoutInflater mInflater;
	int margl;
	int margt;
	// DisplayImageOptions op;
	float opacity = 1.0F;
	Bitmap originalBitmap;
	int pivx;
	int pivy;
	int pos;
	Bitmap shadowBitmap;
	float startDegree;


	public ClipArt(Context paramContext) {
		super(paramContext);
		cntx = paramContext;
		layGroup = this;
		// this.clip = paramRelativeLayout;
		basex = 0;
		basey = 0;
		pivx = 0;
		pivy = 0;
		// .v = paramArrayOfString;
		// this.op = paramDisplayImageOptions;
		mInflater = ((LayoutInflater) paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		mInflater.inflate(R.layout.clipart, this, true);
		btndel = ((ImageButton) findViewById(R.id.del));
		btnrot = ((ImageButton) findViewById(R.id.rotate));
		btnscl = ((ImageButton) findViewById(R.id.sacle));
		imgring = ((ImageView) findViewById(R.id.image));
		// imageUri = ("assets://Cliparts/" + paramArrayOfString[paramInt1]);
		layoutParams = new LayoutParams(250, 250);
		layGroup.setLayoutParams(layoutParams);
		image = ((ImageView) findViewById(R.id.clipart));
		image.setImageBitmap(NewHolder.sticker_Image);
		// ImageLoader.getInstance().displayImage(this.imageUri, this.image,
		// paramDisplayImageOptions);
		image.setTag(Integer.valueOf(0));
		setOnTouchListener(new OnTouchListener() {
			final GestureDetector gestureDetector = new GestureDetector(ClipArt.this.cntx,
					new GestureDetector.SimpleOnGestureListener() {
						public boolean onDoubleTap(MotionEvent paramAnonymous2MotionEvent) {
							return false;
						}
					});

			public boolean onTouch(View paramAnonymousView, MotionEvent event) {
				ClipArt.this.visiball();
				if (!ClipArt.this.freeze) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						layGroup.invalidate();
						gestureDetector.onTouchEvent(event);

						layGroup.bringToFront();

						layGroup.performClick();
						basex = ((int) (event.getRawX() - layoutParams.leftMargin));
						basey = ((int) (event.getRawY() - layoutParams.topMargin));
						break;
					case MotionEvent.ACTION_MOVE:
						int i = (int) event.getRawX();
						int j = (int) event.getRawY();
						layBg = (RelativeLayout) (getParent());
						if ((i - basex > -(layGroup.getWidth() * 2 / 3))
								&& (i - basex < layBg.getWidth() - layGroup.getWidth() / 3)) {
							layoutParams.leftMargin = (i - basex);
						}
						if ((j - basey > -(layGroup.getHeight() * 2 / 3))
								&& (j - basey < layBg.getHeight() - layGroup.getHeight() / 3)) {
							layoutParams.topMargin = (j - basey);
						}
						layoutParams.rightMargin = -9999999;
						layoutParams.bottomMargin = -9999999;
						layGroup.setLayoutParams(layoutParams);
						break;

					}

					return true;
				}
				return true;
				// freeze;
			}
		});

		this.btnscl.setOnTouchListener(new OnTouchListener() {
			@SuppressLint({ "NewApi" })
			public boolean onTouch(View paramAnonymousView, MotionEvent event) {
				if (!ClipArt.this.freeze) {
					int j = (int) event.getRawX();
					int i = (int) event.getRawY();
					layoutParams = (LayoutParams) layGroup.getLayoutParams();
					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						ClipArt.this.layGroup.invalidate();
						ClipArt.this.basex = j;
						ClipArt.this.basey = i;
						ClipArt.this.basew = ClipArt.this.layGroup.getWidth();
						ClipArt.this.baseh = ClipArt.this.layGroup.getHeight();
						int[] loaction = new int[2];
						layGroup.getLocationOnScreen(loaction);
						margl = layoutParams.leftMargin;
						margt = layoutParams.topMargin;
						break;
					case MotionEvent.ACTION_MOVE:
						float f2 = (float) Math.toDegrees(Math.atan2(i - ClipArt.this.basey, j - ClipArt.this.basex));
						float f1 = f2;
						if (f2 < 0.0F) {
							f1 = f2 + 360.0F;
						}
						j -= ClipArt.this.basex;
						int k = i - ClipArt.this.basey;
						i = (int) (Math.sqrt(j * j + k * k) * Math.cos(Math.toRadians(f1
								- ClipArt.this.layGroup.getRotation())));
						j = (int) (Math.sqrt(i * i + k * k) * Math.sin(Math.toRadians(f1
								- ClipArt.this.layGroup.getRotation())));
						k = i * 2 + ClipArt.this.basew;
						int m = j * 2 + ClipArt.this.baseh;
						if (k > 150) {
							layoutParams.width = k;
							layoutParams.leftMargin = (ClipArt.this.margl - i);
						}
						if (m > 150) {
							layoutParams.height = m;
							layoutParams.topMargin = (ClipArt.this.margt - j);
						}
						ClipArt.this.layGroup.setLayoutParams(layoutParams);
						ClipArt.this.layGroup.performLongClick();
						break;
					}
					return true;
				}
				return ClipArt.this.freeze;
			}
		});
		this.btnrot.setOnTouchListener(new OnTouchListener() {
			@SuppressLint({ "NewApi" })
			public boolean onTouch(View paramAnonymousView, MotionEvent event) {
				if (!ClipArt.this.freeze) {
					layoutParams = (LayoutParams) ClipArt.this.layGroup.getLayoutParams();
					ClipArt.this.layBg = ((RelativeLayout) ClipArt.this.getParent());
					int[] arrayOfInt = new int[2];
					layBg.getLocationOnScreen(arrayOfInt);
					int i = (int) event.getRawX() - arrayOfInt[0];
					int j = (int) event.getRawY() - arrayOfInt[1];
					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						layGroup.invalidate();
						startDegree = layGroup.getRotation();
						pivx = (layoutParams.leftMargin + ClipArt.this.getWidth() / 2);
						pivy = (layoutParams.topMargin + ClipArt.this.getHeight() / 2);
						basex = (i - ClipArt.this.pivx);
						basey = (ClipArt.this.pivy - j);
						break;

					case MotionEvent.ACTION_MOVE:
						int k = ClipArt.this.pivx;
						int m = ClipArt.this.pivy;
						j = (int) (Math.toDegrees(Math.atan2(ClipArt.this.basey, ClipArt.this.basex)) - Math
								.toDegrees(Math.atan2(m - j, i - k)));
						i = j;
						if (j < 0) {
							i = j + 360;
						}
						ClipArt.this.layGroup.setRotation((ClipArt.this.startDegree + i) % 360.0F);
						break;
					}

					return true;
				}
				return ClipArt.this.freeze;
			}
		});
		this.btndel.setOnClickListener(new OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (!ClipArt.this.freeze) {
					layBg = ((RelativeLayout) getParent());
					layBg.performClick();
//					MainActivity.index = MainActivity.index-1;
					layBg.removeView(layGroup);
//					layBg.getChildAt(MainActivity.index).setVisibility(GONE);
//					layBg.indexOfChild(layBg.getChildAt(index))
//					MainActivity.index = MainActivity.index-1;
				}
			}
		});

	}

	public void disableAll() {
		btndel.setVisibility(View.INVISIBLE);
		btnrot.setVisibility(View.INVISIBLE);
		btnscl.setVisibility(View.INVISIBLE);
		imgring.setVisibility(View.INVISIBLE);
	}

	public ImageView getImageView() {
		return this.image;
	}

	public float getOpacity() {
		return this.image.getAlpha();
	}

	public void resetImage() {
		originalBitmap = null;
		layGroup.performLongClick();
	}

	public void setColor(int paramInt) {
		image.getDrawable().setColorFilter(null);
		ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(new float[] { 0.33F, 0.33F,
				0.33F, 0.0F, Color.red(paramInt), 0.33F, 0.33F, 0.33F, 0.0F, Color.green(paramInt), 0.33F, 0.33F,
				0.33F, 0.0F, Color.blue(paramInt), 0.0F, 0.0F, 0.0F, 1.0F, 0.0F });
		image.getDrawable().setColorFilter(localColorMatrixColorFilter);
		image.setTag(Integer.valueOf(paramInt));
		layGroup.performLongClick();
	}

	public void setFreeze(boolean paramBoolean) {
		this.freeze = paramBoolean;
	}

	public void setImageId() {
		image.setId(layGroup.getId() +i);
		i += 1;
	}
	public void setImageId(int imageId) {
		image.setId(imageId);
//		i += 1;
	}
	public int getImageId(){
		return image.getId();
	}

	public void setLocation() {
		this.layBg = ((RelativeLayout) getParent());
		LayoutParams localLayoutParams = (LayoutParams) this.layGroup.getLayoutParams();
		localLayoutParams.topMargin = ((int) (Math.random() * (this.layBg.getHeight() - 400)));
		localLayoutParams.leftMargin = ((int) (Math.random() * (this.layBg.getWidth() - 400)));
		this.layGroup.setLayoutParams(localLayoutParams);
	}

	public void visiball() {
		this.btndel.setVisibility(View.VISIBLE);
		this.btnrot.setVisibility(View.VISIBLE);
		this.btnscl.setVisibility(View.VISIBLE);
		this.imgring.setVisibility(View.VISIBLE);
	}

	public static abstract interface DoubleTapListener {
		public abstract void onDoubleTap();
	}

}
