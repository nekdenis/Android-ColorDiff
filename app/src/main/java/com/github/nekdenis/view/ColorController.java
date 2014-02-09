package com.github.nekdenis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.github.nekdenis.dto.ColorObj;

public abstract class ColorController extends FrameLayout {

    protected OnColorModifiedListener colorModifiedListener;

    public ColorController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ColorController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorController(Context context) {
        super(context);
    }

    /**
     * set {@link OnColorModifiedListener}
     * for this controller
     * @param colorModifiedListener
     */
    public void setColorModifiedListener(OnColorModifiedListener colorModifiedListener) {
        this.colorModifiedListener = colorModifiedListener;
    }

    /**
     * set color that will be modified by this controller
     *
     */
    public abstract void updateCurrentColor(ColorObj modifiedColor);

    /**
     * This interface must be implemented by those views who controls
     * color
     */

    public interface OnColorModifiedListener {
        public void onModified(ColorObj color);
    }
}
