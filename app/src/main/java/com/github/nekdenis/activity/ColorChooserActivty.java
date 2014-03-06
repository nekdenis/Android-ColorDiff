package com.github.nekdenis.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.fragment.ColorPalleteFragment;


/**
 * Activity for choosing custom color
 */
public class ColorChooserActivty extends Activity {

    private static final int COLOR_SPACE_RGB = 0;
    private static final int COLOR_SPACE_LAB = 1;
    private static final int COLOR_SPACE_LCH = 2;

    private int selectedColorSpace = COLOR_SPACE_LAB;

    private View preview;
    private EditText rgbText;
    private EditText labText;
    private EditText lchText;
    private SeekBar aBar;
    private SeekBar bBar;
    private SeekBar cBar;
    private Button selectButton;
    private ColorObj color;
    private RadioButton radioRGB;
    private RadioButton radioLAB;
    private RadioButton radioLCH;

    public static void startActivity(Context context) {
        Intent i = new Intent(context, ColorChooserActivty.class);
        context.startActivity(i);
    }

    public static void startActivityForResultColor(Fragment fragment) {
        Intent i = new Intent(fragment.getActivity(), ColorChooserActivty.class);
        fragment.startActivityForResult(i, ColorPalleteFragment.REQUEST_COLOR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_chooser);
        initView();
        initListeners();
        color = new ColorObj("", 70, 128, 128);
        aBar.setProgress(70);
        bBar.setProgress(100);
        cBar.setProgress(100);
        updatePreview();
    }

    private void initView() {
        preview = findViewById(R.id.color_chooser_preview);
        rgbText = (EditText) findViewById(R.id.color_chooser_rgb_text);
        labText = (EditText) findViewById(R.id.color_chooser_lab_text);
        lchText = (EditText) findViewById(R.id.color_chooser_lch_text);
        aBar = (SeekBar) findViewById(R.id.color_chooser_seekbar_a);
        bBar = (SeekBar) findViewById(R.id.color_chooser_seekbar_b);
        cBar = (SeekBar) findViewById(R.id.color_chooser_seekbar_c);
        selectButton = (Button) findViewById(R.id.color_chooser_select_button);
        radioRGB = (RadioButton) findViewById(R.id.color_chooser_radio_rgb);
        radioLAB = (RadioButton) findViewById(R.id.color_chooser_radio_lab);
        radioLCH = (RadioButton) findViewById(R.id.color_chooser_radio_lch);
    }

    private void initListeners() {
        radioRGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioLAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        radioLCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        aBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    onASeekProgressChanged(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        bBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    onBSeekProgressChanged(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        cBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    onCSeekProgressChanged(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectClick();
            }
        });
    }

    private void onASeekProgressChanged(int i) {
        switch (selectedColorSpace) {
            case COLOR_SPACE_RGB:
                int[] rgb = color.getRGB();
                rgb[0] = i * 255 / 100;
                color.setRGB(rgb);
                updatePreview();
                break;
            case COLOR_SPACE_LAB:
                color.setL(i);
                updatePreview();
                break;
            case COLOR_SPACE_LCH:
                color.setL(i);
                updatePreview();
                break;
        }
    }

    private void onBSeekProgressChanged(int i) {
        switch (selectedColorSpace) {
            case COLOR_SPACE_RGB:
                int[] rgb = color.getRGB();
                rgb[1] = i * 255 / 100;
                color.setRGB(rgb);
                updatePreview();
                break;
            case COLOR_SPACE_LAB:
                color.setA((i * 240 / 100) - 120);
                updatePreview();
                break;
            case COLOR_SPACE_LCH:
                double[] lch = color.getLCH();
                lch[1] = (i);
                color.setLCH(lch);
                updatePreview();
                break;
        }
    }

    private void onCSeekProgressChanged(int i) {
        switch (selectedColorSpace) {
            case COLOR_SPACE_RGB:
                int[] rgb = color.getRGB();
                rgb[2] = i * 255 / 100;
                color.setRGB(rgb);
                updatePreview();
                break;
            case COLOR_SPACE_LAB:
                color.setB((i * 240 / 100) - 120);
                updatePreview();
                break;
            case COLOR_SPACE_LCH:
                double[] lch = color.getLCH();
                lch[2] = ((i * 360 / 100) - 180);
                color.setLCH(lch);
                updatePreview();
                break;
        }
    }

    private void onSelectClick() {
        Intent i = getIntent().putExtra(ColorPalleteFragment.EXTRA_COLOR, color);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.color_chooser_radio_rgb:
                if (checked) {
                    selectedColorSpace = COLOR_SPACE_RGB;
                    return;
                }
                break;
            case R.id.color_chooser_radio_lab:
                if (checked) {
                    selectedColorSpace = COLOR_SPACE_LAB;
                    return;
                }
                break;
            case R.id.color_chooser_radio_lch:
                if (checked) {
                    selectedColorSpace = COLOR_SPACE_LCH;
                    return;
                }
                break;
        }
    }

    private void updatePreview() {
        preview.setBackgroundColor(color.getRGBint());
        rgbText.setText(color.getRGBString());
        labText.setText(color.getLABString());
        lchText.setText(color.getLCHString());
    }
}