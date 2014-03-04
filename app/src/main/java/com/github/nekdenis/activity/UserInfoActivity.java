package com.github.nekdenis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ResultObj;
import com.github.nekdenis.fragment.UserInfoFragment;

public class UserInfoActivity extends FragmentActivity implements UserInfoFragment.OnFragmentInteractionListener {

    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    /**
     * Starts activity with color matcher
     */
    public static void startWithResultObject(Context context, ResultObj resultObj) {
        Intent i = new Intent(context, UserInfoActivity.class);
        i.putExtra(EXTRA_RESULT, resultObj);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matcher);
        ResultObj resultObj = (ResultObj) getIntent().getSerializableExtra(EXTRA_RESULT);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, UserInfoFragment.newInstance(resultObj))
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
