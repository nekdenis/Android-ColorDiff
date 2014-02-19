package com.github.nekdenis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;

/**
 * Controller that changes color in LAB coordinates.
 * Changes depends on angle and radius between original color
 * and modified in AB coordinates
 * User can change the radius
 */
public class LARController extends ColorController {

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
    private ColorObj originalColor;
    private double angle;
    private int step;

    public LARController(Context context) {
        super(context);
        initView();
    }

    public LARController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LARController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    public void updateCurrentColor(ColorObj modifiedColor) {
        this.modifiedColor = modifiedColor;
    }

    public void setOriginalColorAndAngle(ColorObj originalColor, double angle) {
        this.originalColor = originalColor;
        this.angle = angle;
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
        updateColorRadius(-A_STEP);
    }

    private void onIncAClicked() {
        updateColorRadius(A_STEP);
    }

    private void onDecBClicked() {
        updateColorRadius(-B_STEP);
    }

    private void onIncBClicked() {
        updateColorRadius(B_STEP);
    }

    private void onDecCClicked() {
        updateColorRadius(-C_STEP);
    }

    private void onIncCClicked() {
        updateColorRadius(C_STEP);
    }

    private void updateColorRadius(int stepValue) {
        step += stepValue;
        int[] ab = convertAngleAndRadiusToAB(step);
        modifiedColor.setA(ab[0]);
        modifiedColor.setB(ab[1]);
        updateModifiedSquare();
    }

    private void updateModifiedSquare() {
        if (colorModifiedListener != null) {
            colorModifiedListener.onModified(modifiedColor);
        }
    }

    private int[] convertAngleAndRadiusToAB(int radius) {
        int a = (int) (originalColor.getA() + radius * Math.cos(angle));
        int b = (int) (originalColor.getB() + radius * Math.sin(angle));
        int[] result = {a, b};
        return result;
    }

    /**
     * set the @param angle between original and modified
     * colors in AB coordinates
     *
     */
    public void updateAngle(double angle) {
        this.angle = angle;
        updateColorRadius(0);
        updateModifiedSquare();
    }
}
