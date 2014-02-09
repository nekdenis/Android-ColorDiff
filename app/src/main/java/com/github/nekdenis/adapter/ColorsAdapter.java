package com.github.nekdenis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.github.nekdenis.R;
import com.github.nekdenis.dto.ColorObj;

import java.util.ArrayList;

public class ColorsAdapter extends ArrayAdapter<ColorObj> {
    private LayoutInflater inflater;

    public ColorsAdapter(Context context, ArrayList<ColorObj> trips) {
        super(context, R.layout.color_item, trips);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.color_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.populate(getItem(position));

        return convertView;
    }

    class Holder {
        View background;

        public Holder(View v) {
            background =  v.findViewById(R.id.color_item_bg);
        }

        public void populate(ColorObj colorObj) {
            background.setBackgroundColor(colorObj.getRGBint());
        }

    }
}
