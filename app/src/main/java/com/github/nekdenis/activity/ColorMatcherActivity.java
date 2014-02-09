package com.github.nekdenis.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.fragment.ColorMatcherFragment;

public class ColorMatcherActivity extends FragmentActivity implements ColorMatcherFragment.OnFragmentInteractionListener {

    public static final String EXTRA_COLOR = "EXTRA_COLOR";

    /**
     * Starts activity with color matcher
     */
    public static void StartWithColor(Context context, ColorObj colorObj) {
        Intent i = new Intent(context, ColorMatcherActivity.class);
        i.putExtra(EXTRA_COLOR,colorObj);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matcher);
        ColorObj colorObj = (ColorObj) getIntent().getSerializableExtra(EXTRA_COLOR);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ColorMatcherFragment.newInstance(colorObj))
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
