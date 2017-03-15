package com.kaa_solutions.eazyback.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;

import java.util.ArrayList;

public class DelayBackAdapter extends ArrayAdapter<Contact> {
    public DelayBackAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_delay_back, parent, false);
        }

        TextView txtViewName = (TextView) convertView.findViewById(R.id.adapter_delay_back_name);
        if (contact.getName() != null) {
            txtViewName.setText(contact.getName());
        } else {
            txtViewName.setText("Unknown");
        }

        TextView txtViewPhone = (TextView) convertView.findViewById(R.id.adapter_delay_back_phone_number);
        if (contact.getPhone() != null) {
            txtViewPhone.setText(contact.getPhone());
        }

        return convertView;
    }
}