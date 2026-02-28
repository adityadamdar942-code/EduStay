package com.example.edustay.Comman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.edustay.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Profile_Fragment extends Fragment {
    TextView tvProfileName , tvProfileEmail;
    ImageView ivProfileImage;
    CardView cvProfileHelp ,cvProfileTheme ,cvProfileAboutUs ,cvProfilePassword ,cvProfileManageProf;

    SharedPreferences preferences;
    SharedPreferences.Editor editor ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

       tvProfileName =view.findViewById(R.id.tvProfileName);
       tvProfileEmail =view.findViewById(R.id.tvProfileEmail);
       ivProfileImage =view.findViewById(R.id.ivProfileImage);
       cvProfileManageProf =view.findViewById(R.id.cvProfileManageProf);
       cvProfilePassword =view.findViewById(R.id.cvProfilePassword);
       cvProfileAboutUs =view.findViewById(R.id.cvProfileAboutUs);
       cvProfileTheme =view.findViewById(R.id.cvProfileTheme);
       cvProfileHelp =view.findViewById(R.id.cvProfileHelp);

       getMyProfile();





       //intent
       cvProfileManageProf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), Manager_Profile_Activity.class);
               startActivity(intent);
           }
       });

       cvProfilePassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), Password_Security_Activity.class);
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
       cvProfileTheme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), Theme_Activity.class);
               startActivity(intent);
           }
       });
       cvProfileHelp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getContext(), Help_center_Activity.class);
               startActivity(intent);
           }
       });



       return view ;
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


                                //display a  name and email
                                tvProfileName.setText(strName);
                                tvProfileEmail.setText(strEmailid);


                                //profile pic
                                Glide.with(getContext())
                                        .load("http://172.30.87.239:80/EduStayAPI/images/"+strimage)
                                        .skipMemoryCache(true)
                                        .error(R.drawable.image_not_found)
                                        .into(ivProfileImage);



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