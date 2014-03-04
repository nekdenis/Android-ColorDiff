package com.github.nekdenis.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.androidplot.xy.*;
import com.github.nekdenis.R;
import com.github.nekdenis.activity.UserInfoActivity;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.dto.ResultObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragments for showing results
 */
public class ResultFragment extends Fragment {

    public static final String EXTRA_RESULT_OBJECT = "EXTRA_RESULT_OBJECT";

    private ResultObj resultObj;

    private View centerColorView;
    private View rightColorView;
    private TextView rightColorText;
    private TextView centerColorText;
    private Button shareButton;

    private XYPlot leftPlot;
    private XYPlot rightPlot;

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
        shareButton = (Button) view.findViewById(R.id.result_next_button);
        leftPlot = (XYPlot) view.findViewById(R.id.result_left_plot);
        rightPlot = (XYPlot) view.findViewById(R.id.result_right_plot);

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
        leftPlot.addSeries(series1, series1Format);
        leftPlot.setRangeLabel("");
        leftPlot.setDomainLabel("");
        leftPlot.setTitle("");
        leftPlot.setTicksPerRangeLabel(3);
        leftPlot.getGraphWidget().setDomainLabelOrientation(-45);
        leftPlot.setDomainBoundaries(-128, 128, BoundaryMode.FIXED);
        leftPlot.setRangeBoundaries(-128, 128, BoundaryMode.FIXED);
        leftPlot.redraw();

        rightPlot.addSeries(series1, series1Format);
        rightPlot.setRangeLabel("");
        rightPlot.setDomainLabel("");
        rightPlot.setTitle("");
        rightPlot.setTicksPerRangeLabel(3);
        rightPlot.getGraphWidget().setDomainLabelOrientation(-45);
        rightPlot.redraw();
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
        UserInfoActivity.startWithResultObject(getActivity(), resultObj);
    }

}
