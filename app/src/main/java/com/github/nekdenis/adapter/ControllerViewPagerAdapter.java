package com.github.nekdenis.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.github.nekdenis.dto.ColorObj;
import com.github.nekdenis.view.ColorController;
import com.github.nekdenis.view.LABController;
import com.github.nekdenis.view.LHCController;

public class ControllerViewPagerAdapter extends PagerAdapter {

    public static final int VIEWS_COUNT = 2;

    private Context context;
    private ColorController.OnColorModifiedListener colorModifiedListener;
    private ColorObj modifiedColor;

    /**
     * Adapter with different color controller views
     * @param context
     * @param colorModifiedListener
     * @param colorObj
     */
    public ControllerViewPagerAdapter(Context context, ColorController.OnColorModifiedListener colorModifiedListener, ColorObj colorObj) {
        this.context = context;
        this.colorModifiedListener = colorModifiedListener;
        this.modifiedColor = colorObj;
    }

    @Override
    public int getCount() {
        return VIEWS_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View result = null;
        switch (position) {
            case 1:
                LABController labController = new LABController(context);
                labController.setColorModifiedListener(colorModifiedListener);
                labController.updateCurrentColor(modifiedColor);
                result = labController;
                break;
            case 0:
                LHCController lchController = new LHCController(context);
                lchController.setColorModifiedListener(colorModifiedListener);
                lchController.updateCurrentColor(modifiedColor);
                result = lchController;
                break;
        }
        ((ViewPager) collection).addView(result, 0);
        return result;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

}
