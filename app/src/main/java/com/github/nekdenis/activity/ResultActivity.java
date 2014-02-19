package com.github.nekdenis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ResultObj;
import com.github.nekdenis.fragment.ResultFragment;

/**
 * Activity for showing and sharing results
 */
public class ResultActivity extends FragmentActivity {

    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    private ResultObj resultObj;

    public static void startWithResult(Context context, ResultObj resultObj) {
        Intent i = new Intent(context, ResultActivity.class);
        i.putExtra(EXTRA_RESULT, resultObj);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matcher);
        resultObj = (ResultObj) getIntent().getSerializableExtra(EXTRA_RESULT);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ResultFragment.newInstance(resultObj))
                .commit();
    }

}
