package com.example.edustay.Comman;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.edustay.ForgetPasswordActivity;
import com.example.edustay.LoginActivity;
import com.example.edustay.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Profile_Fragment extends Fragment {
    TextView tvProfileName , tvProfileEmail , tvProfileMobileno;
    ImageView ivProfileImage;
    CardView cvProfileHelp  ,cvProfileAboutUs ,cvProfilePassword ,cvProfileManageProf , cvProfileLogout ,cvProfileDeleteAccount;

    SharedPreferences preferences;
    SharedPreferences.Editor editor ;

    //Logout
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

       tvProfileMobileno =view.findViewById(R.id.tvProfileMobileno);
       tvProfileName =view.findViewById(R.id.tvProfileName);
       tvProfileEmail =view.findViewById(R.id.tvProfileEmail);
       ivProfileImage =view.findViewById(R.id.ivProfileImage);
       cvProfileManageProf =view.findViewById(R.id.cvProfileManageProf);
       cvProfilePassword =view.findViewById(R.id.cvProfilePassword);
       cvProfileAboutUs =view.findViewById(R.id.cvProfileAboutUs);
       cvProfileHelp =view.findViewById(R.id.cvProfileHelp);
       cvProfileLogout = view.findViewById(R.id.cvProfileLogout);
       cvProfileDeleteAccount =view.findViewById(R.id.cvProfileDeleteAccount);


       getMyProfile();





       //intent


       cvProfilePassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), ForgetPasswordActivity.class);
               startActivity(intent);
           }
       });
       cvProfileAboutUs.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), About_Us_Activity.class);
               startActivity(intent);
           }
       });
       cvProfileDeleteAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteAccount();
           }
       });

        cvProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle("Logout");
                ad.setMessage("Are you sure you want to logout?");

                ad.setPositiveButton("No", (dialog, which) -> dialog.cancel());

                ad.setNegativeButton("Yes", (dialog, which) -> {

                    googleSignInClient.signOut();

                    editor.putBoolean("islogin", false);
                    editor.apply();

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    getActivity().finish();
                });

                ad.create().show();
            }

        });



       return view ;
    }

    private void deleteAccount() {

        // client server communication


        AsyncHttpClient client =new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",preferences.getString("name","" ));

        client.post(Urls.urlDeleteAccount,params,new JsonHttpResponseHandler()
                {

                    // status code
                    // 200 = Result ok
                    // 404 =not found
                    //500 = server issue

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            String status = response.getString("success");

                            if (status.equals("1"))
                            {
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                editor.putBoolean("islogin", false);
                                editor.apply();

                                startActivity(intent);
                            }else {

                                Toast.makeText(getContext(), "Account Not Deleted ", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);


                        Toast.makeText(getContext(), "Server not found ", Toast.LENGTH_SHORT).show();
                    }
                }




        );
    }

    private void getMyProfile() {

        //Manage Network and send the data over all the network
        AsyncHttpClient client = new AsyncHttpClient();
        // collect data or put
        RequestParams params =new RequestParams();

        params.put("name",preferences.getString("name",""));
        client.post(Urls.urlMyDetails,params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("getMyDetails");
                            //int ,condition ,incre or decre
                            for (int i = 0; i < jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String strId = jsonObject.getString("id");
                                String strimage =jsonObject.getString("image");
                                String strName =jsonObject.getString("name");
                                String strMobileno = jsonObject.getString("mobileno");
                                String strEmailid = jsonObject.getString("emailid");
                                String strPassword =jsonObject.getString("password");


                                //display a  name and email, mobile no
                                tvProfileMobileno.setText(strMobileno);
                                tvProfileName.setText(strName);
                                tvProfileEmail.setText(strEmailid);


                                //profile pic
                                Glide.with(getContext())
                                        .load(Urls.urlProfilePic+strimage)
                                        .skipMemoryCache(true)
                                        .error(R.drawable.image_not_found)
                                        .into(ivProfileImage);



                                cvProfileManageProf.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent =new Intent(getContext(), Manager_Profile_Activity.class);
                                        intent.putExtra("name",tvProfileName.getText().toString());
                                        intent.putExtra("mobileno",tvProfileMobileno.getText().toString());
                                        intent.putExtra("emailid",tvProfileEmail.getText().toString());
                                        startActivity(intent);
                                    }
                                });



                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Server error
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getContext(), "Server Issue", Toast.LENGTH_SHORT).show();

                    }
                }


        );

    }


}