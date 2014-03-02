package com.github.nekdenis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.androidplot.xy.*;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.dto.ResultObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragments for showing and sharing results
 */
public class ResultFragment extends Fragment {

    public static final String EXTRA_RESULT_OBJECT = "EXTRA_RESULT_OBJECT";
    public static final String EXTRA_DATASET = "dataset";
    public static final String EXTRA_RENDERER = "renderer";
    public static final String EXTRA_CURRENT_SERIES = "current_series";
    public static final String EXTRA_CURRENT_RENDERER = "current_renderer";

    private ResultObj resultObj;

    private View centerColorView;
    private View rightColorView;
    private TextView rightColorText;
    private TextView centerColorText;
    private Button shareButton;

    private XYPlot plot;

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

        centerColorText = (TextView) view.findViewById(R.id.result_center_color_text);
        rightColorText = (TextView) view.findViewById(R.id.result_right_color_text);
        centerColorView = view.findViewById(R.id.result_center_color);
        rightColorView = view.findViewById(R.id.result_right_color);
        shareButton = (Button) view.findViewById(R.id.result_share_button);
        plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);

        initPlot();
        fillView();
        initListeners();
        return view;
    }

    private void initPlot() {
        List<Double> xs = new ArrayList<Double>();
        List<Double> ys = new ArrayList<Double>();
        for(ColorObj colorObj: resultObj.getModifiedColors()){
            xs.add(colorObj.getA());
            ys.add(colorObj.getB());
        }
        //add first again for full cirle
        xs.add(xs.get(0));
        ys.add(ys.get(0));

        XYSeries series1 = new SimpleXYSeries(xs,ys,"");

        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity(),
                R.xml.line_point_formatter_with_plf1);
        plot.addSeries(series1, series1Format);
        plot.setRangeLabel("");
        plot.setDomainLabel("");
        plot.setTitle("");
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setDomainBoundaries(-128, 128, BoundaryMode.FIXED);
        plot.setRangeBoundaries(-128, 128, BoundaryMode.FIXED);
        plot.redraw();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        centerColorView.setBackgroundColor(resultObj.getOriginalColor().getRGBint());
        rightColorView.setBackgroundColor(resultObj.getModifiedColors().get(0).getRGBint());
        rightColorText.setText(resultObj.getModifiedColors().get(0).toString());
        centerColorText.setText(resultObj.getOriginalColor().toString());
    }

    private void onShare() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RESULTS");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, makeShareText());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    private String makeShareText() {
        StringBuilder sb = new StringBuilder("Original color: ");
        sb.append(resultObj.getOriginalColor().getLABString()).append("\n");
        for (ColorObj colorObj : resultObj.getModifiedColors()) {
            sb.append(colorObj.getLABString()).append("\n");
        }
        return sb.toString();
    }

}