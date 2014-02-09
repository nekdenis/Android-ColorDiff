package com.github.nekdenis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;

public class LHCController extends ColorController {

    public static final int A_STEP = 10;
    public static final int B_STEP = 2;
    public static final int C_STEP = 1;

    private Button buttonDecA;
    private Button buttonIncA;
    private Button buttonDecB;
    private Button buttonIncB;
    private Button buttonDecC;
    private Button buttonIncC;

    private ColorObj modifiedColor;



    public LHCController(Context context) {
        super(context);
        initView();
    }

    public LHCController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LHCController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    public void updateCurrentColor(ColorObj modifiedColor) {
        this.modifiedColor = modifiedColor;
    }

    private void initView() {
        View view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.lhc_controller_view, this);

        buttonDecA = (Button) view.findViewById(R.id.color_matcher_button_dec_a);
        buttonIncA = (Button) view.findViewById(R.id.color_matcher_button_inc_a);
        buttonDecB = (Button) view.findViewById(R.id.color_matcher_button_dec_b);
        buttonIncB = (Button) view.findViewById(R.id.color_matcher_button_inc_b);
        buttonDecC = (Button) view.findViewById(R.id.color_matcher_button_dec_c);
        buttonIncC = (Button) view.findViewById(R.id.color_matcher_button_inc_c);
        initListeners();
    }

    private void initListeners() {

        buttonDecA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecAClicked();
            }
        });

        buttonIncA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncAClicked();
            }
        });

        buttonDecB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecBClicked();
            }
        });

        buttonIncB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncBClicked();
            }
        });

        buttonDecC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecCClicked();
            }
        });

        buttonIncC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncCClicked();
            }
        });
    }

    private void onDecAClicked() {
        updateColorChroma(-A_STEP);
    }

    private void onIncAClicked() {
        updateColorChroma(A_STEP);
    }

    private void onDecBClicked() {
        updateColorChroma(-B_STEP);
    }

    private void onIncBClicked() {
        updateColorChroma(B_STEP);
    }

    private void onDecCClicked() {
        updateColorChroma(-C_STEP);
    }

    private void onIncCClicked() {
        updateColorChroma(C_STEP);
    }

    private void updateColorChroma(int step) {
        double[] lch = modifiedColor.getLCH();
        lch[1] = lch[1] + step;
        modifiedColor.setLCH(lch);
        updateModifiedSquare();
    }

    private void updateModifiedSquare() {
        if (colorModifiedListener != null) {
            colorModifiedListener.onModified(modifiedColor);
        }
    }
}
