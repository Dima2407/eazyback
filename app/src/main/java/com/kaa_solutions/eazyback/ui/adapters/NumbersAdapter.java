package com.kaa_solutions.eazyback.ui.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;

import java.util.ArrayList;

public class NumbersAdapter extends ArrayAdapter<Contact> {

    public NumbersAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        Log.d(getClass().getSimpleName(), "getViewAdapter: " + contact.toString());
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_numbers, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.adapter_numbers_name);
        name.setText(contact.getName());
        TextView phone = (TextView) convertView.findViewById(R.id.adapter_numbers_phone);
        phone.setText(contact.getPhone());
        return convertView;
    }
}
