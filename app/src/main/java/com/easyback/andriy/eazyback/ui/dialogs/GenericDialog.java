package com.easyback.andriy.eazyback.ui.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.widget.Toast;

import com.easyback.andriy.eazyback.core.callbacks.DialogsCallbacks;

public abstract class GenericDialog extends DialogFragment {

    protected DialogsCallbacks mDialogsCallbacks;

    public void setDialogsCallbacks(DialogsCallbacks pDialogsCallbacks) {
        mDialogsCallbacks = pDialogsCallbacks;
    }

    public void showShortToast(int pResId) {
        showShortToast(getString(pResId));
    }

    public void showShortToast(String pStringBody) {
        genericShortToast(pStringBody, Toast.LENGTH_SHORT);
    }

    public void showLongToast(int pResId) {
        showLongToast(getString(pResId));
    }

    public void showLongToast(String pStringBody) {
        genericShortToast(pStringBody, Toast.LENGTH_LONG);
    }

    private void genericShortToast(String pStringBody, int pType) {
        Toast.makeText(getActivity(), pStringBody, pType).show();
    }
}
