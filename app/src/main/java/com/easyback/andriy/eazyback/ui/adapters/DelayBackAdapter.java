package com.easyback.andriy.eazyback.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.easyback.andriy.eazyback.R;

import java.util.ArrayList;
import java.util.Set;

public class DelayBackAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mLayoutInflater;

    public DelayBackAdapter(Context context, Set<String> objects) {
        super(context, 0, new ArrayList<String>(objects));
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.adapter_delay_back, parent, false);
            holder = new Holder();

            holder.phone = (TextView) convertView.findViewById(R.id.adapter_delay_back_phone_number);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.phone.setText(getItem(position));

        return convertView;
    }

    private static final class Holder {
        TextView phone;
    }

}
