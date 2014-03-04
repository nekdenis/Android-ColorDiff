package com.github.nekdenis.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.fragment.ColorPalleteFragment;
import com.github.nekdenis.util.ColorConverter;

public class MainActivity extends ActionBarActivity implements ColorPalleteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ColorPalleteFragment.newInstance())
                    .commit();
        }
        //testCoverter();
    }

    private void testCoverter() {
        ColorObj a = new ColorObj("", 30, 68, -112);
        Log.d("CONVERTER_TEST FIRST ALG with D50", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(30, 68, -112, ColorConverter.D50)));
        Log.d("CONVERTER_TEST FIRST ALG with D55", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(30, 68, -112, ColorConverter.D55)));
        Log.d("CONVERTER_TEST FIRST ALG with D65", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(30, 68, -112, ColorConverter.D65)));
        Log.d("CONVERTER_TEST FIRST ALG with D75", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(30, 68, -112, ColorConverter.D75)));
        Log.d("CONVERTER_TEST SECOND ALG", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGBII(30, 68, -112)));
        Log.d("CONVERTER_TEST THIRD ALG", "LAB = 30, 68, -112 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGBIII(30, 68, -112)));

        Log.d("CONVERTER_TEST FIRST ALG with D50", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(50, 0, 0, ColorConverter.D50)));
        Log.d("CONVERTER_TEST FIRST ALG with D55", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(50, 0, 0, ColorConverter.D55)));
        Log.d("CONVERTER_TEST FIRST ALG with D65", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(50, 0, 0, ColorConverter.D65)));
        Log.d("CONVERTER_TEST FIRST ALG with D75", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGB(50, 0, 0, ColorConverter.D75)));
        Log.d("CONVERTER_TEST SECOND ALG", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGBII(50, 0, 0)));
        Log.d("CONVERTER_TEST THIRD ALG", "LAB = 50, 0, 0 RGB =" + RGBArrayAsString(ColorConverter.LABtoRGBIII(50, 0, 0)));
    }

    private String RGBArrayAsString(int[] rgb) {

        rgb[0] = (rgb[0] << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        rgb[1] = (rgb[1] << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        rgb[2] = rgb[2] & 0x000000FF; //Mask out anything not blue.
        int a = 0xFF000000 | rgb[0] | rgb[1] | rgb[2]; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
        return Integer.toHexString(a);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ColorConverterActivty.startActivity(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelected(ColorObj colorObj) {
        ColorMatcherActivity.startWithColor(MainActivity.this, colorObj);
    }
}
