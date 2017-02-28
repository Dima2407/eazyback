package com.kaa_solutions.eazyback.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.kaa_solutions.eazyback.R;
import com.kaa_solutions.eazyback.core.SharedHelper;


public class CustomDialogFragment extends DialogFragment {

    final String TAG = getClass().getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CleanerData().clearApplicationData(getActivity());

                        new SharedHelper(getActivity().getApplicationContext()).clearData();
                        Intent i = getActivity().getApplicationContext().getPackageManager()
                                .getLaunchIntentForPackage(getActivity().getApplicationContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.cancel, null);


        return builder.create();
    }


}
