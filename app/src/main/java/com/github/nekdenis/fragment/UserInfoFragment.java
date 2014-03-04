package com.github.nekdenis.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.dto.ResultObj;


/**
 * Fragment for setting user information and sending results
 */
public class UserInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public static final String EXTRA_RESULT_OBJECT = "EXTRA_RESULT_OBJECT";

    private ResultObj resultObj;

    private EditText nameEditText;
    private EditText distanceEditText;
    private EditText modelEditText;
    private EditText brightnessEditText;
    private Button finishButton;

    public static UserInfoFragment newInstance(ResultObj resultObj) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESULT_OBJECT, resultObj);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserInfoFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resultObj = (ResultObj) getArguments().getSerializable(EXTRA_RESULT_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        nameEditText = (EditText) view.findViewById(R.id.user_info_name);
        distanceEditText = (EditText) view.findViewById(R.id.user_info_distance);
        modelEditText = (EditText) view.findViewById(R.id.user_info_model);
        brightnessEditText = (EditText) view.findViewById(R.id.user_info_brightness);
        finishButton = (Button) view.findViewById(R.id.user_info_finish_button);

        return view;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillviews();
        initListeners();
    }

    private void fillviews() {
        modelEditText.setText(getDeviceName());
        brightnessEditText.setText(getScreenBrightness());
    }

    private void initListeners() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinishClick();
            }
        });
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public String getScreenBrightness() {
        try {
            int curBrightnessValue = android.provider.Settings.System.getInt(getActivity().getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            return String.valueOf(curBrightnessValue);
        } catch (Settings.SettingNotFoundException e) {
            return "0";
        }
    }

    private void onFinishClick() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RESULTS");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, makeShareText());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    private String makeShareText() {
        StringBuilder sb = new StringBuilder();

        sb.append("User name: ");
        sb.append(nameEditText.getText().toString()).append("\n");

        sb.append("Phone model: ");
        sb.append(modelEditText.getText().toString()).append("\n");

        sb.append("Brightness level: ");
        sb.append(brightnessEditText.getText().toString()).append("\n");

        sb.append("Face distance:");
        sb.append(distanceEditText.getText().toString()).append("\n");

        sb.append("Original color: ");
        sb.append(resultObj.getOriginalColor().getLABString()).append("\n");

        for (ColorObj colorObj : resultObj.getModifiedColors()) {
            sb.append(colorObj.getLABString()).append("\n");
        }
        return sb.toString();
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
        public void onFragmentInteraction();
    }

}
