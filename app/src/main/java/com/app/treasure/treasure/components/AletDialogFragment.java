package com.app.treasure.treasure.components;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.app.treasure.treasure.R;

/**
 * Created by ruifeng on 2016/7/13.
 */
public class AletDialogFragment extends DialogFragment {

    private static final String KYE_TITLE="title";
    private static final String KYE_MESSAGE="message";


    public static AletDialogFragment newInstance(int ResTitle,String msg){
        AletDialogFragment dialogFragment = new AletDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KYE_TITLE, ResTitle);
        bundle.putString(KYE_MESSAGE, msg);
        dialogFragment.setArguments(bundle);
        return  dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title=getArguments().getInt(KYE_TITLE);
        String message=getArguments().getString(KYE_MESSAGE);
        return  new AlertDialog.Builder(getActivity(),getTheme())
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
