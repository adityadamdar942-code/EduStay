package com.example.edustay.Hostel_module.Hostel_Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edustay.R;


public class Complaints_hostel_Fragment extends Fragment  {

    EditText etEmailSubject, etEmailMessage;
    Button btnComplaintComplaint;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_complaints_hostel_,container, false);

        // Init views
        etEmailSubject = view.findViewById(R.id.etComplaintSubject);
        etEmailMessage = view.findViewById(R.id.etComplaintEmailMessage);
        btnComplaintComplaint = view.findViewById(R.id.btnComplaintComplaint);

        btnComplaintComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject = etEmailSubject.getText().toString();
                String message = etEmailMessage.getText().toString();

                // Common email (fixed)
                String recipientEmail = "adityadamdar942@gmail.com";

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + recipientEmail));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(
                            intent,
                            "Choose Email App"
                    ));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(
                            getContext(),
                            "No Email App Installed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        return view;
    }
}
