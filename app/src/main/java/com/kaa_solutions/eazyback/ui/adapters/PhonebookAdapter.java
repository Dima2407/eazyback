package com.kaa_solutions.eazyback.ui.adapters;

import android.content.Context;
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
import java.util.List;

public class PhonebookAdapter extends ArrayAdapter<Contact> implements Filterable {

    private List<Contact> originalData = null;
    private List<Contact> filteredData = null;
    private ItemFilter mFilter = new ItemFilter();

    public PhonebookAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        originalData = contacts;
        filteredData = contacts;
    }


    public int getCount() {
        return filteredData.size();
    }

    public Contact getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return mFilter;
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

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Contact> list = originalData;

            int count = list.size();
            final ArrayList<Contact> nlist = new ArrayList<>(count);

            for (Contact contact : originalData) {
                if (contact.getName().toLowerCase().contains(filterString)) {
                    nlist.add(contact);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }

    }

}
