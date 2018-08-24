package com.mohred.studentnetwork.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mohred.studentnetwork.R;

/**
 * Created by Redan on 12/16/2016.
 */

public class DialogFragmentError extends DialogFragment implements View.OnClickListener
{
    private String message=null;
    private ImageButton imageButton;

    public static DialogFragmentError newInstance(String message)
    {
        DialogFragmentError f = new DialogFragmentError();
        f.setMessage(message);

        return f;
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_error_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_error_height);
        getDialog().getWindow().setLayout(width, height);
    }


    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog  dialog = new Dialog(getActivity(), R.style.GrowFromPointTheme );
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_error, null);

        if(message!=null)
        {
            TextView messageView = (TextView) view.findViewById(R.id.the_text_message);
            messageView.setText(message);
        }

        Drawable exitDrawable = getActivity().getResources().getDrawable(R.drawable.ic_action_delete_sign_48px);

        exitDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.light_blue),
                PorterDuff.Mode.MULTIPLY));

        imageButton = (ImageButton) view.findViewById(R.id.button_exit);
        imageButton.setImageDrawable(exitDrawable);
        imageButton.setOnClickListener(this);

        Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    @Override
    public void	onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
    }

    @Override
    public void	onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
    }

    @Override
    public void	onStart()
    {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ok_button:
                this.dismiss();
                break;

            case R.id.button_exit:
                this.dismiss();
                break;
        }
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
