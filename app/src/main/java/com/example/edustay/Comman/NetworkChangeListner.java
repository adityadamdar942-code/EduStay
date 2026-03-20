package com.example.edustay.Comman;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.example.edustay.R;

import androidx.appcompat.widget.AppCompatButton;

public class NetworkChangeListner extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkDetails.isConnectedtoInternet(context)) {
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.check_internet_connection, null);
            ad.setView(view);

            AlertDialog alertDialog = ad.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            AppCompatButton btnTryAgain = view.findViewById(R.id.btnTryAgain);
            btnTryAgain.setOnClickListener(v -> {
                alertDialog.dismiss();

                if (!NetworkDetails.isConnectedtoInternet(context)) {
                    onReceive(context, intent);
                } else {
                    Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, "Your Internet is Connected", Toast.LENGTH_SHORT).show();
        }
    }
}
