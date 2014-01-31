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
    private Button buttonDecA;
    private Button buttonIncA;
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
        buttonDecA = (Button) view.findViewById(R.id.color_matcher_button_dec_a);
        buttonIncA = (Button) view.findViewById(R.id.color_matcher_button_inc_a);
        finishButton = (Button) view.findViewById(R.id.color_matcher_finish_button);

        initListeners();
        updateOriginalSquare();
        updateModifiedSquare();

        return view;
    }

    private void initListeners() {

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

        seekBarL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateColorLight(i);
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
                updateColorA(i);
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
                updateColorB(i);
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

    private void onDecAClicked() {
        updateColor(A_STEP);
    }

    private void onIncAClicked() {
        updateColor(-A_STEP);
    }


    private void updateColor(int step) {
        modifiedColor.setA(modifiedColor.getA()+step);
        updateModifiedSquare();
    }

    private void updateColorLight(int value) {
        modifiedColor.setL(value);
        updateModifiedSquare();
    }

    private void updateColorA(int value) {
        modifiedColor.setA(value);
        updateModifiedSquare();
    }

    private void updateColorB(int value) {
        modifiedColor.setB(value);
        updateModifiedSquare();
    }

    private void updateOriginalSquare() {
        leftSquare.setBackgroundColor(originalColor.getRGBint());
    }

    private void updateModifiedSquare() {
        rightSquare.setBackgroundColor(modifiedColor.getRGBint());
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
