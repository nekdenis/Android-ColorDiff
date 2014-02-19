package com.github.nekdenis.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.github.nekdenis.R;
import com.github.nekdenis.activity.ResultActivity;
import com.github.nekdenis.adapter.ControllerViewPagerAdapter;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.dto.ResultObj;
import com.github.nekdenis.view.ColorController;


/**
 * Fragment that contains color matching UI
 */
public class ColorMatcherFragment extends Fragment {
    private static final String EXTRA_COLOR_OBJECT = "EXTRA_COLOR_OBJECT";
    public static final int STEPS_COUNT = 9;

    private OnFragmentInteractionListener mListener;

    private ColorObj originalColor;
    private ColorObj modifiedColor;
    private ResultObj resultObj;

    private View leftSquare;
    private View rightSquare;

    private ViewPager controllerViewPager;
    private ControllerViewPagerAdapter controllerViewPagerAdapter;
    private TextView textOriginalRGB;
    private TextView textModifiedRGB;
    private TextView textOriginalLAB;
    private TextView textModifiedLAB;
    private TextView textOriginalLCH;
    private TextView textModifiedLCH;
    private Button finishButton;

    private int step = 0;
    private double angle;

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

        controllerViewPager = (ViewPager) view.findViewById(R.id.color_matcher_controllers_pager);
        leftSquare = view.findViewById(R.id.color_matcher_left_square);
        rightSquare = view.findViewById(R.id.color_matcher_right_square);

        finishButton = (Button) view.findViewById(R.id.color_matcher_finish_button);
        textModifiedLAB = (TextView) view.findViewById(R.id.color_matcher_right_lab);
        textModifiedLCH = (TextView) view.findViewById(R.id.color_matcher_right_lch);
        textModifiedRGB = (TextView) view.findViewById(R.id.color_matcher_right_rgb);
        textOriginalRGB = (TextView) view.findViewById(R.id.color_matcher_left_rgb);
        textOriginalLAB = (TextView) view.findViewById(R.id.color_matcher_left_lab);
        textOriginalLCH = (TextView) view.findViewById(R.id.color_matcher_left_lch);

        initViewPager();
        initListeners();
        updateOriginalSquare();
        updateModifiedSquare();

        Toast.makeText(getActivity(), getResources().getString(R.string.matcher_lowest_toast), Toast.LENGTH_SHORT);

        return view;
    }

    private void initViewPager() {
        ColorController.OnColorModifiedListener colorModifiedListener = new ColorController.OnColorModifiedListener() {
            @Override
            public void onModified(ColorObj color) {
                ColorMatcherFragment.this.modifiedColor = color;
                updateModifiedSquare();
            }
        };
        controllerViewPagerAdapter = new ControllerViewPagerAdapter(getActivity(), colorModifiedListener, modifiedColor, originalColor, angle);
        controllerViewPager.setAdapter(controllerViewPagerAdapter);
    }

    private void initListeners() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinishClick();
            }
        });
    }

    private void onFinishClick() {
        if (step == 0) {
            resultObj = new ResultObj();
            resultObj.setOriginalColor(originalColor);
        }
        if (step == STEPS_COUNT - 2) {
            finishButton.setText(R.string.button_finish);
        }
        if (step == STEPS_COUNT - 1) {
            ResultActivity.startWithResult(getActivity(), resultObj);
        }
        step++;
        resultObj.addModifiedColor(new ColorObj("", modifiedColor));
        modifiedColor = new ColorObj("", originalColor);
        updateAngle();
        updateModifiedSquare();
    }

    private void updateAngle() {
        angle = Math.toRadians(360/STEPS_COUNT*step%360);
        controllerViewPagerAdapter.updateAngle(angle);
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

    private void updateOriginalSquare() {
        leftSquare.setBackgroundColor(originalColor.getRGBint());
        textOriginalLAB.setText(originalColor.toString());
        textOriginalRGB.setText(Integer.toHexString(originalColor.getRGBint()));
        textOriginalLCH.setText(originalColor.getLCHString());
    }

    private void updateModifiedSquare() {
        rightSquare.setBackgroundColor(modifiedColor.getRGBint());
        textModifiedLAB.setText(modifiedColor.toString());
        textModifiedRGB.setText(Integer.toHexString(modifiedColor.getRGBint()));
        textModifiedLCH.setText(modifiedColor.getLCHString());
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
