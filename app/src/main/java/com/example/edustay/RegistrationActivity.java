package com.example.edustay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edustay.Comman.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    EditText etRegistrationEmail, etRegistrationName,etRegistrationPassword,etRegistrationMobileno;
    CheckBox cbRegistrationShowHidePassword;
    Button btnRegistrationRegister;
    TextView tvRegistrationOldUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        etRegistrationName = findViewById(R.id.etRegiName);
        etRegistrationEmail = findViewById(R.id.etRegiEmail);
        etRegistrationMobileno = findViewById(R.id.etRegiMobileno);
        etRegistrationPassword = findViewById(R.id.etRegiPassword);
        tvRegistrationOldUser = findViewById(R.id.tvRegiOldUser);
        cbRegistrationShowHidePassword = findViewById(R.id.cbRegiShowHidePassword);
        btnRegistrationRegister = findViewById(R.id.btnRegiregistraion);
        cbRegistrationShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etRegistrationPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etRegistrationPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRegistrationRegister.setOnClickListener(v -> {
            String name = etRegistrationName.getText().toString();
            String email = etRegistrationEmail.getText().toString();
            String mobileno = etRegistrationMobileno.getText().toString();
            String password = etRegistrationPassword.getText().toString();


            if (name.isEmpty()) {
                etRegistrationName.setError("Please enter your  Name");
            }
            else if (mobileno.isEmpty())
            {
                etRegistrationMobileno.setError("Enter Mobile No.");
            }
            else if (mobileno.length() != 10)
            {
                etRegistrationMobileno.setError("Mobile No must contain exactly 10 digits");
            }
            else if (email.isEmpty())
            {
                etRegistrationEmail.setError("Please enter your email");
            }
            else if (password.isEmpty())
            {
                etRegistrationPassword.setError("Please enter your password");
            }
            else if (!isValidPassword(password))
            {
                etRegistrationPassword.setError("Password must include:\n" +
                        "• At least 8 characters\n" +
                        "• Uppercase & lowercase letters\n" +
                        "• Number\n" +
                        "• Special character");
            }
            else
            {
                
                registerUser();

            }
        });
        tvRegistrationOldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        //classname objectname = new Constructorname();
        AsyncHttpClient client = new AsyncHttpClient(); //manage the network
        RequestParams params =new RequestParams(); // collect and pass data


        params.put("name",etRegistrationName.getText().toString());
        params.put("mobileno",etRegistrationMobileno.getText().toString());
        params.put("emailid",etRegistrationEmail.getText().toString());
        params.put("password",etRegistrationPassword.getText().toString());


        client.post(Urls.urlRegisterUser,params,new JsonHttpResponseHandler() {


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);


                        try {
                            String status = response.getString("success");

                            if (status.equals("1"))
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Registration Fail", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(RegistrationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }


        );

    }


    // Password strength checker
    private boolean isValidPassword(String password) {
        String passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }





}