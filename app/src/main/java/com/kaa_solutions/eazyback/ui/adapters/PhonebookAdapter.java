package com.kaa_solutions.eazyback.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;

import java.util.ArrayList;

public class PhonebookAdapter extends ArrayAdapter<Contact> implements Filterable {
    public PhonebookAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        Log.d(getClass().getSimpleName(), "getViewAdapter: " + contact.toString());
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_phonebook, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.adapter_phonebook_contact_name);
        name.setText(contact.getName());
        TextView phone = (TextView) convertView.findViewById(R.id.adapter_phonebook_contact_phone);
        phone.setText(contact.getPhone());
        return convertView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        // return a filter that filters data based on a constraint

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }


    private class ContactsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
