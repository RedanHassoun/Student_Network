package com.mohred.studentnetwork.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.interfaces.DialogFragmentListener;

/**
 * Created by Redan on 12/31/2016.
 */

public class DialogFragmentInsertURL extends DialogFragment
{
    private DialogFragmentListener parent;

    public static final DialogFragmentInsertURL newInstance(DialogFragmentListener parent)
    {
        DialogFragmentInsertURL f = new DialogFragmentInsertURL();
        f.setParent(parent);

        return f;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder createProjectAlert = new AlertDialog.Builder(getActivity());

        createProjectAlert.setTitle(getString(R.string.link));

        LayoutInflater inflater = getActivity().getLayoutInflater();

        createProjectAlert.setView(inflater.inflate(R.layout.dialog_fragment_insert_link, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getParent().onDialogPositiveClick(DialogFragmentInsertURL.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getParent().onDialogNegativeClick(DialogFragmentInsertURL.this);
                    }
                });

        return createProjectAlert.create();
    }


    public DialogFragmentListener getParent()
    {
        return parent;
    }

    public void setParent(DialogFragmentListener parent)
    {
        this.parent = parent;
    }
}
