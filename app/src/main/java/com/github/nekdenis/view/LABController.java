package com.github.nekdenis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;


/**
 * Controller that allows to change L A B parameters of color
 */
public class LABController extends ColorController {

    public static final int A_STEP = 10;

    private SeekBar seekBarL;
    private SeekBar seekBarA;
    private SeekBar seekBarB;
    private Button buttonDecL;
    private Button buttonIncL;
    private Button buttonDecA;
    private Button buttonIncA;
    private Button buttonDecB;
    private Button buttonIncB;

    private ColorObj modifiedColor;



    public LABController(Context context) {
        super(context);
        initView();
    }

    public LABController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LABController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    public void updateCurrentColor(ColorObj modifiedColor) {
        this.modifiedColor = modifiedColor;
        seekBarL.setProgress((int) modifiedColor.getL());
        seekBarA.setProgress((int) ((modifiedColor.getA() + 128) * 100 / 256));
        seekBarB.setProgress((int) ((modifiedColor.getB() + 128) * 100 / 256));
    }

    private void initView() {
        View view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.lab_controller_view, this);

        seekBarL = (SeekBar) view.findViewById(R.id.color_matcher_light_controller);
        seekBarA = (SeekBar) view.findViewById(R.id.color_matcher_a_controller);
        seekBarB = (SeekBar) view.findViewById(R.id.color_matcher_b_controller);
        buttonDecL = (Button) view.findViewById(R.id.color_matcher_button_dec_l);
        buttonIncL = (Button) view.findViewById(R.id.color_matcher_button_inc_l);
        buttonDecA = (Button) view.findViewById(R.id.color_matcher_button_dec_a);
        buttonIncA = (Button) view.findViewById(R.id.color_matcher_button_inc_a);
        buttonDecB = (Button) view.findViewById(R.id.color_matcher_button_dec_b);
        buttonIncB = (Button) view.findViewById(R.id.color_matcher_button_inc_b);
        initListeners();
    }

    private void initListeners() {

        buttonDecL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecLClicked();
            }
        });

        buttonIncL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncLClicked();
            }
        });

        buttonDecA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecAClicked();
            }
        });

        buttonIncA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncAClicked();
            }
        });

        buttonDecB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecBClicked();
            }
        });

        buttonIncB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncBClicked();
            }
        });

        seekBarL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateColorLPercent(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        seekBarA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateColorAPercent(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateColorBPercent(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void onDecLClicked() {
        updateColorL(-A_STEP);
    }

    private void onIncLClicked() {
        updateColorL(A_STEP);
    }

    private void onDecAClicked() {
        updateColorA(-A_STEP);
    }

    private void onIncAClicked() {
        updateColorA(A_STEP);
    }

    private void onDecBClicked() {
        updateColorB(-A_STEP);
    }

    private void onIncBClicked() {
        updateColorB(A_STEP);
    }

    private void updateColorL(int step) {
        modifiedColor.setL((int) (Math.max(Math.min(modifiedColor.getL() + step, 100), -100)));
        updateModifiedSquare();
    }

    private void updateColorA(int step) {
        modifiedColor.setA((int) (Math.max(Math.min(modifiedColor.getA() + step, 128), -128)));
        updateModifiedSquare();
    }

    private void updateColorB(int step) {
        modifiedColor.setB((int) (Math.max(Math.min(modifiedColor.getB() + step, 128), -128)));
        updateModifiedSquare();
    }

    private void updateColorLPercent(int value) {
        modifiedColor.setL(Math.max(Math.min(value, 100), -100));
        updateModifiedSquare();
    }

    private void updateColorAPercent(int value) {
        modifiedColor.setA(Math.max(Math.min((value - 50) * 128 / 50, 128), -128));
        updateModifiedSquare();
    }

    private void updateColorBPercent(int value) {
        modifiedColor.setB(Math.max(Math.min((value - 50) * 128 / 50, 128), -128));
        updateModifiedSquare();
    }

    private void updateModifiedSquare() {
        if (colorModifiedListener != null) {
            colorModifiedListener.onModified(modifiedColor);
        }
    }
}
