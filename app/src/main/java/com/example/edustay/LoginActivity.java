package com.example.edustay;

import android.app.ProgressDialog;
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

public class LoginActivity extends AppCompatActivity {


    EditText etLoginUsername,etLoginPassword;
    CheckBox cbLoginShowHidePassword;
    Button btnLoginLogo;
    TextView tvLoginNewUser;
    ProgressDialog progressDialog;

    SharedPreferences preferences; //store temp data//database
    SharedPreferences.Editor editor;//put or edit data of SharedPrefernces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();
        if (preferences.getBoolean("islogin",false))
        {
            Intent intent = new Intent(LoginActivity.this,ModuleActivity.class);
            startActivity(intent);
        }
        etLoginUsername = findViewById(R.id.etLoginUserName);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        cbLoginShowHidePassword = findViewById(R.id.cbLoginShowHidePassword);
        btnLoginLogo = findViewById(R.id.btnLoginLogin);
        tvLoginNewUser = findViewById(R.id.tvLoginNewUser);

        cbLoginShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLoginLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLoginUsername.getText().toString().isEmpty()) {
                    etLoginUsername.setError("Please Enter Your username");
                } else if (etLoginUsername.getText().toString().length() < 8) {
                    etLoginUsername.setError("name must be greater than 8 character ");
                }else if (etLoginPassword.getText().toString().isEmpty()){
                    etLoginPassword.setError("Please Enter Your Password");
                } else if (etLoginPassword.getText().toString().toUpperCase().toLowerCase().length()<8.) {
                    etLoginPassword.setError("Password must be Consider" +
                            "\n1: minimum 8 characters\n" +
                            "2: uppercase\n" +
                            "3: Lowercase\n" +
                            "4: Special Character\n" +
                            "5: Integers");

                } else
                {
                   progressDialog = new ProgressDialog(LoginActivity.this);
                   progressDialog.setTitle("Login");
                   progressDialog.setMessage("please wait");
                   progressDialog.show();
                   loginUser();

                }
            }
        });
        tvLoginNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser() {

        //Over the  Network data manage

        AsyncHttpClient client =new AsyncHttpClient(); // data gheun jail tumchya web service prynt
        RequestParams params = new RequestParams(); // data collect and pass AsyncHttpClient

        params.put("name",etLoginUsername.getText().toString()); //aditya
        params.put("password",etLoginPassword.getText().toString());//aditya123


        client.post(Urls.urlLoginUser,params,new JsonHttpResponseHandler()
              {
                  //200 = Result ok                 Header []  = authorization JSONObject = response receive
                  //404 = not found
                  // 500 = server error
                  @Override
                  public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                      super.onSuccess(statusCode, headers, response);

                      try {
                          String status = response.getString("success");

                          if (status.equals("1"))
                          {
                              progressDialog.dismiss();
                              Toast.makeText(LoginActivity.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(LoginActivity.this, ModuleActivity.class);
                              editor.putString("name",etLoginUsername.getText().toString()).commit();
                              editor.putBoolean("islogin",true).commit();
                              startActivity(intent);
                              finish();
                          }
                          else
                          {
                              progressDialog.dismiss();
                              Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                          }
                      } catch (JSONException e) {
                          throw new RuntimeException(e);
                      }


                  }

                  @Override
                  public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                      super.onFailure(statusCode, headers, throwable, errorResponse);
                      progressDialog.dismiss();
                      Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                  }
              }


        );

    }

}