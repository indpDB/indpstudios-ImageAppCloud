package com.indptechnologies.happybirthdayphotoframes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by DDR INFO SYSTEM on 19-04-2017.
 */

public class ColorPickerDialog extends AlertDialog implements ColorPickerView.OnColorChangedListener{

    private ColorPickerView mColorPicker;
    private ColorPanelView mOldColor;
    private ColorPanelView mNewColor;
    private ColorPickerView.OnColorChangedListener mListener;

    protected ColorPickerDialog(Context context, int initialColor) {
        super(context);

        init(initialColor);
    }

    private void init(int initialColor) {
        getWindow().setFormat(PixelFormat.RGBA_8888);

        setUp(initialColor);
    }
    private void setUp(int color) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_color_set, null);

        setView(layout);

        setTitle("Pick a Color");
        // setIcon(android.R.drawable.ic_dialog_info);

        mColorPicker = (ColorPickerView) layout
                .findViewById(R.id.color_picker_view);
        mOldColor = (ColorPanelView) layout.findViewById(R.id.old_color_panel);
        mNewColor = (ColorPanelView) layout.findViewById(R.id.new_color_panel);

        ((LinearLayout) mOldColor.getParent()).setPadding(Math
                .round(mColorPicker.getDrawingOffset()), 0, Math
                .round(mColorPicker.getDrawingOffset()), 0);

        mColorPicker.setOnColorChangedListener(this);

        mOldColor.setColor(color);
        mColorPicker.setColor(color, true);

    }

    @Override
    public void onColorChanged(int color) {

        mNewColor.setColor(color);

        if (mListener != null) {
            mListener.onColorChanged(color);
        }

    }

    public void setAlphaSliderVisible(boolean visible) {
        mColorPicker.setAlphaSliderVisible(visible);
    }

    public int getColor() {
        return mColorPicker.getColor();
    }


}
