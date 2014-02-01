package com.github.nekdenis.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;


/**
 * Fragment that contains color matching UI
 */
public class ColorMatcherFragment extends Fragment {
    private static final String EXTRA_COLOR_OBJECT = "EXTRA_COLOR_OBJECT";
    public static final int A_STEP = 10;

    private OnFragmentInteractionListener mListener;

    private ColorObj originalColor;
    private ColorObj modifiedColor;

    private View leftSquare;
    private View rightSquare;
    private SeekBar seekBarL;
    private SeekBar seekBarA;
    private SeekBar seekBarB;
    private Button buttonDecL;
    private Button buttonIncL;
    private Button buttonDecA;
    private Button buttonIncA;
    private Button buttonDecB;
    private Button buttonIncB;
    private TextView textOriginalRGB;
    private TextView textModifiedRGB;
    private TextView textOriginalLAB;
    private TextView textModifiedLAB;
    private Button finishButton;

    /**
     * @param colorObj color object for color test
     * @return
     */
    public static ColorMatcherFragment newInstance(ColorObj colorObj) {
        ColorMatcherFragment fragment = new ColorMatcherFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_COLOR_OBJECT, colorObj);
        fragment.setArguments(args);
        return fragment;
    }

    public ColorMatcherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            originalColor = (ColorObj) getArguments().getSerializable(EXTRA_COLOR_OBJECT);
            modifiedColor = new ColorObj("0", originalColor);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_matcher, container, false);

        leftSquare = view.findViewById(R.id.color_matcher_left_square);
        rightSquare = view.findViewById(R.id.color_matcher_right_square);
        seekBarL = (SeekBar) view.findViewById(R.id.color_matcher_light_controller);
        seekBarA = (SeekBar) view.findViewById(R.id.color_matcher_a_controller);
        seekBarB = (SeekBar) view.findViewById(R.id.color_matcher_b_controller);
        buttonDecL = (Button) view.findViewById(R.id.color_matcher_button_dec_l);
        buttonIncL = (Button) view.findViewById(R.id.color_matcher_button_inc_l);
        buttonDecA = (Button) view.findViewById(R.id.color_matcher_button_dec_a);
        buttonIncA = (Button) view.findViewById(R.id.color_matcher_button_inc_a);
        buttonDecB = (Button) view.findViewById(R.id.color_matcher_button_dec_b);
        buttonIncB = (Button) view.findViewById(R.id.color_matcher_button_inc_b);
        finishButton = (Button) view.findViewById(R.id.color_matcher_finish_button);
        textModifiedLAB = (TextView) view.findViewById(R.id.color_matcher_right_lab);
        textModifiedRGB = (TextView) view.findViewById(R.id.color_matcher_right_rgb);
        textOriginalRGB = (TextView) view.findViewById(R.id.color_matcher_left_rgb);
        textOriginalLAB = (TextView) view.findViewById(R.id.color_matcher_left_lab);

        updateSeekBars();
        initListeners();
        updateOriginalSquare();
        updateModifiedSquare();

        return view;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        modifiedColor.setL(modifiedColor.getL() + step);
        updateModifiedSquare();
    }

    private void updateColorA(int step) {
        modifiedColor.setA(modifiedColor.getA() + step);
        updateModifiedSquare();
    }

    private void updateColorB(int step) {
        modifiedColor.setB(modifiedColor.getB() + step);
        updateModifiedSquare();
    }

    private void updateColorLPercent(int value) {
        modifiedColor.setL(value);
        updateModifiedSquare();
    }

    private void updateColorAPercent(int value) {
        modifiedColor.setA((value-50)*128/50);
        updateModifiedSquare();
    }

    private void updateColorBPercent(int value) {
        modifiedColor.setB((value-50)*128/50);
        updateModifiedSquare();
    }

    private void updateOriginalSquare() {
        leftSquare.setBackgroundColor(originalColor.getRGBint());
        textOriginalLAB.setText(originalColor.toString());
        textOriginalRGB.setText("" + originalColor.getRGBint());
    }

    private void updateModifiedSquare() {
        rightSquare.setBackgroundColor(modifiedColor.getRGBint());
        textModifiedLAB.setText(modifiedColor.toString());
        textModifiedRGB.setText(Integer.toHexString(modifiedColor.getRGBint()));
    }

    private void updateSeekBars() {
        seekBarL.setProgress(modifiedColor.getL());
        seekBarA.setProgress((modifiedColor.getA()+128)*100/256);
        seekBarB.setProgress((modifiedColor.getB()+128)*100/256);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
