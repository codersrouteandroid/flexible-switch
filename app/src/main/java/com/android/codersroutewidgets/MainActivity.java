package com.android.codersroutewidgets;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.codersroute.flexiblewidgets.FlexibleSwitch;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean toggle = false;
    FlexibleSwitch flexibleSwitch;
    AppCompatButton textToggleBtn;
    SeekBar strokeWidthSeekbar, speedSeekBar;
    int width = 0, height = 0;
    AppCompatEditText switchWidth, switchHeight;
    AppCompatTextView bgColorOffText, bgColorOnText, thumbColorOffText, thumbColorOnText,
            textColorOffText, textColorOnText, strokeColorOffText, strokeColorOnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flexibleSwitch = findViewById(R.id.flex_switch);
        strokeWidthSeekbar = findViewById(R.id.stroke_seekbar);
        speedSeekBar = findViewById(R.id.speed_seekbar);
        switchWidth = findViewById(R.id.switch_width);
        switchHeight = findViewById(R.id.switch_height);
        textToggleBtn = findViewById(R.id.toggle_btn);
        bgColorOffText = findViewById(R.id.switch_bg_off_value);
        bgColorOnText = findViewById(R.id.switch_bg_on_value);
        thumbColorOffText = findViewById(R.id.switch_thumb_off_value);
        thumbColorOnText = findViewById(R.id.switch_thumb_on_value);
        textColorOffText = findViewById(R.id.switch_text_off_value);
        textColorOnText = findViewById(R.id.switch_text_on_value);
        strokeColorOffText = findViewById(R.id.switch_stroke_off_value);
        strokeColorOnText = findViewById(R.id.switch_stroke_on_value);

        strokeWidthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                flexibleSwitch.setStrokeWidth(progressChangedValue);
            }
        });

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                flexibleSwitch.setSpeed(progressChangedValue);
            }
        });

        switchWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    width = Integer.parseInt(editable.toString());
                    if(width > 0 && height > 0){
                        ConstraintLayout.LayoutParams parms = (ConstraintLayout.LayoutParams) flexibleSwitch.getLayoutParams();
                        parms.width = width;
                        parms.height = height;
                        flexibleSwitch.setLayoutParams(parms);
                    }
                }

            }
        });

        switchHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    height = Integer.parseInt(editable.toString());
                    if(width > 0 && height > 0){
                        ConstraintLayout.LayoutParams parms = (ConstraintLayout.LayoutParams) flexibleSwitch.getLayoutParams();
                        parms.width = width;
                        parms.height = height;
                        flexibleSwitch.setLayoutParams(parms);
                    }
                }

            }
        });


        textToggleBtn.setOnClickListener(this);
        bgColorOffText.setOnClickListener(this);
        bgColorOnText.setOnClickListener(this);
        thumbColorOffText.setOnClickListener(this);
        thumbColorOnText.setOnClickListener(this);
        textColorOffText.setOnClickListener(this);
        textColorOnText.setOnClickListener(this);
        strokeColorOffText.setOnClickListener(this);
        strokeColorOnText.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ui_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flex_switch_gallery:
                startActivity(new Intent(this, FlexibleSwitches.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.toggle_btn:{

                if(toggle){
                    toggle = false;
                    flexibleSwitch.setShowText(toggle);
                    textToggleBtn.setText("TEXT OFF");
                }else{
                    toggle = true;
                    flexibleSwitch.setShowText(toggle);
                    textToggleBtn.setText("TEXT ON");
                }
                break;
            }
            case R.id.switch_bg_off_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> bgColorOffText.setText(getResources().getString(R.string.background_color_off)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setBackgroundColorOnSwitchOff(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_bg_on_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> bgColorOnText.setText(getResources().getString(R.string.background_color_on)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setBackgroundColorOnSwitchOn(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_thumb_off_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> thumbColorOffText.setText(getResources().getString(R.string.thumb_color_off)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setThumbColorOnSwitchOff(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_thumb_on_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> thumbColorOnText.setText(getResources().getString(R.string.thumb_color_on)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setThumbColorOnSwitchOn(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_text_off_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> textColorOffText.setText(getResources().getString(R.string.text_color_off)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setTextColorOnSwitchOff(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_text_on_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> textColorOnText.setText(getResources().getString(R.string.text_color_on)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setTextColorOnSwitchOn(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_stroke_off_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> strokeColorOffText.setText(getResources().getString(R.string.stroke_color_off)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setStrokeColorOnSwitchOff(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }

            case R.id.switch_stroke_on_value:{

                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(com.google.android.material.R.color.design_default_color_primary)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> strokeColorOnText.setText(getResources().getString(R.string.stroke_color_on)+": "+Integer.toHexString(selectedColor)))
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> flexibleSwitch.setStrokeColorOnSwitchOn(selectedColor))
                        .setNegativeButton("cancel", (dialog, which) -> {
                        })
                        .build()
                        .show();
                break;
            }



            default:{}
        }
    }
}