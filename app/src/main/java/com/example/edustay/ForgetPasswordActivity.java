package com.example.edustay;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edustay.Comman.Urls;
import com.loopj.android.http.*;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText etForgetUserName , etForgetNewPassword , etForgetConfirmPassword;
    Button btnForgetPass ;
    CheckBox cbForgetShowHidePassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etForgetUserName = findViewById(R.id.etForgetUserName);
        etForgetNewPassword = findViewById(R.id.etForgetNewPassword);
        etForgetConfirmPassword = findViewById(R.id.etForgetConfirmPassword);
        btnForgetPass = findViewById(R.id.btnForgetPass);
        cbForgetShowHidePassword = findViewById(R.id.cbForgetShowHidePassword);

        // Show / Hide Password
        cbForgetShowHidePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etForgetNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etForgetConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etForgetNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etForgetConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btnForgetPass.setOnClickListener(v -> validateAndSend());
    }

    private void validateAndSend() {

        String username = etForgetUserName.getText().toString().trim();
        String newPass = etForgetNewPassword.getText().toString().trim();
        String confirmPass = etForgetConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            etForgetUserName.setError("Enter Username");
            return;
        }

        if (username.length() < 8) {
            etForgetUserName.setError("Minimum 8 characters");
            return;
        }

        if (newPass.isEmpty()) {
            etForgetNewPassword.setError("Enter New Password");
            return;
        }

        if (!newPass.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) {
            etForgetNewPassword.setError("Password must contain:\nUppercase, Lowercase, Number, Special char");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            etForgetConfirmPassword.setError("Password not match");
            return;
        }

        forgetPassword(username, newPass);
    }

    private void forgetPassword(String username, String password) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name", username);
        params.put("password", password);

        client.post(Urls.urlForgetPassword, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    String status = response.getString("success");

                    if (status.equals("1")) {
                        Toast.makeText(ForgetPasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ForgetPasswordActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}