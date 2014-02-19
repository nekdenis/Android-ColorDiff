package com.github.nekdenis.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.github.nekdenis.R;
import com.github.nekdenis.adapter.ColorsAdapter;
import com.github.nekdenis.dto.ColorObj;

import java.util.ArrayList;


/**
 * A fragment representing a list of colors to choose.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
@SuppressWarnings("JavadocReference")
public class ColorPalleteFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    private ArrayList<ColorObj> colors;

    public static ColorPalleteFragment newInstance() {
        ColorPalleteFragment fragment = new ColorPalleteFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ColorPalleteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fillColors();
        mAdapter = new ColorsAdapter(getActivity(), colors);
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onColorSelected(colors.get(position));
        }
    }

    private void fillColors() {
        colors = new ArrayList<ColorObj>();
        colors.add(new ColorObj("", 25,47,89));
        colors.add(new ColorObj("", 100,42,56));
        colors.add(new ColorObj("", 45,47,49));
        colors.add(new ColorObj("", 56,-75,-90));
        colors.add(new ColorObj("", 69,51,36));
        colors.add(new ColorObj("", 56,19,-23));
        colors.add(new ColorObj("", 31,51,-100));
        colors.add(new ColorObj("", 72,36,79));
        colors.add(new ColorObj("", 49,88,-28));
        colors.add(new ColorObj("", 72,-21,68));

    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onColorSelected(ColorObj colorObj);
    }

}
