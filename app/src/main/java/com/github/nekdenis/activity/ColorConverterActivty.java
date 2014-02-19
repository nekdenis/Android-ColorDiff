package com.github.nekdenis.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;


/**
 * Activity for producing some color conversion tests
 */
public class ColorConverterActivty extends Activity {

    private View preview;
    private TextView colorText;
    private SeekBar horizontalBar;
    private SeekBar verticalBar;
    private SeekBar horizontalBar2;
    private SeekBar verticalBar2;
    private ColorObj color;
    private ColorObj originalColor;

    public static void startActivity(Context context) {
        Intent i = new Intent(context, ColorConverterActivty.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_converter);
        initView();
        initListeners();
        originalColor = new ColorObj("", 70, 60, 70);
        color = new ColorObj("", originalColor);
        updatePreview();
    }

    private void initView() {
        preview = findViewById(R.id.color_converter_preview);
        colorText = (TextView) findViewById(R.id.color_converter_text);
        horizontalBar = (SeekBar) findViewById(R.id.color_converter_horizontal);
        verticalBar = (SeekBar) findViewById(R.id.color_converter_vertical);
        horizontalBar2 = (SeekBar) findViewById(R.id.color_converter_horizontal2);
        verticalBar2 = (SeekBar) findViewById(R.id.color_converter_vertical2);
    }

    private void initListeners() {
        horizontalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    color.setA((i * 240 / 100) - 120);
                    updatePreview();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        verticalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    color.setB((i * 240 / 100) - 120);
                    updatePreview();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        horizontalBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
//                    double[] lch = color.getLCH();
//                    lch[1] = i;
//                    color.setLCH(lch);
                    int[] ab = convertAngleAndRadiusToAB(i * 360 / 100, verticalBar2.getProgress());
                    color.setA(ab[0]);
                    color.setB(ab[1]);
                    updatePreview();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        verticalBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
//                    double[] lch = color.getLCH();
//                    lch[2] = i * 360 / 100;
//                    color.setLCH(lch);
                    int[] ab = convertAngleAndRadiusToAB(horizontalBar2.getProgress() * 360 / 100, i);
                    color.setA(ab[0]);
                    color.setB(ab[1]);
                    updatePreview();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updatePreview() {
        horizontalBar.setProgress((int) ((color.getA() + 120) * 100 / 240));
        verticalBar.setProgress((int) ((color.getB() + 120) * 100 / 240));
        double[] lch = color.getLCH();
        //horizontalBar2.setProgress((int) lch[1]);
        //verticalBar2.setProgress((int) (lch[2] * 100d / 360d));
        preview.setBackgroundColor(color.getRGBint());
        colorText.setText(color.getLABString() + "\n" + color.getLCHString());
    }


    private int[] convertAngleAndRadiusToAB(int angle, int radius) {
        int a = (int) (originalColor.getA() + radius * Math.cos(Math.toRadians(angle)));
        int b = (int) (originalColor.getB() + radius * Math.sin(Math.toRadians(angle)));
        int[] result = {a, b};
        return result;
    }

}
