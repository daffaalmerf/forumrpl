package com.project.daffaalmerf.uaspm;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    LoadingDialog(Activity mActivity){
        activity = mActivity;
    }

    void startDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_dialog_loading, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    void dismissDialog(){

        alertDialog.dismiss();

    }

}
