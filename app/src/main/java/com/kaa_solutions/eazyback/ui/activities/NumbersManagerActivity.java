package com.kaa_solutions.eazyback.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.models.Contact;
import com.kaa_solutions.eazyback.ui.adapters.NumbersAdapter;

import java.util.ArrayList;

import static com.kaa_solutions.eazyback.core.SharedHelper.AMOUNT_PHONES_NUMBER;

public final class NumbersManagerActivity extends GenericActivity {

    private ListView listView;
    private ArrayList<Contact> arrayOfUsers;
    private NumbersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBackButton();
        getSupportActionBar().setTitle(R.string.title_activity_numbers);
        setContentView(R.layout.activity_number);
        buildFam();
        inflateListView();
        setListOnLongPressListener();

    }

    private void setListOnLongPressListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(NumbersManagerActivity.this);
                builderSingle.setIcon(R.drawable.ic_launcher);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(NumbersManagerActivity.this, android.R.layout.select_dialog_item);
                arrayAdapter.add("Edit");
                arrayAdapter.add("Delete");

                builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(NumbersManagerActivity.this, "Edit function doesn't work yet", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {
                            Contact contact = (Contact) parent.getItemAtPosition(position);
                            getPhonesDAO().deleteContact(contact);
                            Toast.makeText(NumbersManagerActivity.this, "Number has benn deleted", Toast.LENGTH_SHORT).show();
                            inflateListView();
                        }
                    }
                });
                builderSingle.show();
                return true;
            }
        });
    }

    private void buildFam() {
        findViewById(R.id.add_new_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getPhonesDAO().readAllContacts().size() < AMOUNT_PHONES_NUMBER) {
                    startActivity(new Intent(getApplicationContext(), AddNewNumber.class));
                } else {
                    Toast.makeText(NumbersManagerActivity.this, R.string.listOfNumbersIsFull, Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.add_number_from_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPhonesDAO().readAllContacts().size() < AMOUNT_PHONES_NUMBER) {
                    startActivity(new Intent(getApplicationContext(), PhoneBookActivity.class));
                } else {
                    Toast.makeText(NumbersManagerActivity.this, R.string.listOfNumbersIsFull, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inflateListView() {
        listView = (ListView) findViewById(R.id.numbers);
        arrayOfUsers = getPhonesDAO().readAllContacts();

        if (arrayOfUsers != null) {
            adapter = new NumbersAdapter(this, arrayOfUsers);
            listView.setAdapter(adapter);
        } else {
            listView.setAdapter(null);
        }
    }
}


