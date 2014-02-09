package com.github.nekdenis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ResultObj;

public class ResultFragment extends Fragment {

    public static final String EXTRA_RESULT_OBJECT = "EXTRA_RESULT_OBJECT";

    private ResultObj resultObj;

    private View leftColorView;
    private View centerColorView;
    private View rightColorView;
    private TextView rightColorText;
    private TextView centerColorText;
    private TextView leftColorText;
    private Button shareButton;


    public static ResultFragment newInstance(ResultObj resultObj) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESULT_OBJECT, resultObj);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
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
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        leftColorText = (TextView) view.findViewById(R.id.result_left_color_text);
        centerColorText = (TextView) view.findViewById(R.id.result_center_color_text);
        rightColorText = (TextView) view.findViewById(R.id.result_right_color_text);
        leftColorView = view.findViewById(R.id.result_left_color);
        centerColorView = view.findViewById(R.id.result_center_color);
        rightColorView = view.findViewById(R.id.result_right_color);
        shareButton = (Button) view.findViewById(R.id.result_share_button);

        fillView();
        initListeners();

        return view;
    }

    private void initListeners() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShare();
            }
        });
    }

    private void fillView() {
        leftColorView.setBackgroundColor(resultObj.getLeftColor().getRGBint());
        centerColorView.setBackgroundColor(resultObj.getOriginalColor().getRGBint());
        rightColorView.setBackgroundColor(resultObj.getRightColor().getRGBint());
        rightColorText.setText(resultObj.getRightColor().toString());
        centerColorText.setText(resultObj.getOriginalColor().toString());
        leftColorText.setText(resultObj.getLeftColor().toString());
    }

    private void onShare() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RESULTS");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, makeShareText());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    private String makeShareText() {
        return String.format("Original color: %1$s \nLeft color: %2$s \nRight color: %3$s",
                resultObj.getOriginalColor().getLABString(), resultObj.getLeftColor().getLABString(), resultObj.getRightColor().getLABString());
    }

}
