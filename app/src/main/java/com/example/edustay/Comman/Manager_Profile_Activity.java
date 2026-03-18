package com.example.edustay.Comman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edustay.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Manager_Profile_Activity extends AppCompatActivity {
    EditText etName ,etMobileno , etEmailid ;
    Button btnUpdate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);

        etName = findViewById(R.id.etManageName);
        etMobileno = findViewById(R.id.etManageMobileno);
        etEmailid = findViewById(R.id.etManageEmail);
        btnUpdate = findViewById(R.id.btnManageManage);




        etName.setText(getIntent().getStringExtra("name"));
        etMobileno.setText(getIntent().getStringExtra("mobileno"));
        etEmailid.setText(getIntent().getStringExtra("emailid"));




        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    private void updateProfile() {

        //over the network data manage
        AsyncHttpClient client =new AsyncHttpClient();
        // data collect or put in AsyncHttpClient
        RequestParams params =new RequestParams();

        params.put("name",etName.getText().toString());
        params.put("mobileno",etMobileno.getText().toString());
        params.put("emailid",etEmailid.getText().toString());


        client.post(Urls.urlUpdateProfile,params,new JsonHttpResponseHandler()

                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            String status = response.getString("success");
                            if (status.equals("1"))
                            {
                                Intent intent =new Intent(Manager_Profile_Activity.this,Profile_Fragment.class);
                                Toast.makeText(Manager_Profile_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else
                            {
                                Toast.makeText(Manager_Profile_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(Manager_Profile_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();

                    }
                }



        );
    }
}