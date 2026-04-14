package com.example.edustay.Hostel_module.Hostel_Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.edustay.Comman.Urls;
import com.example.edustay.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Home_hostel_Fragment extends Fragment {

    // TextViews for Rooms
    TextView tvRoomTotal, tvRoomFull, tvRoomAvail;
    // TextViews for Beds
    TextView tvBedTotal, tvBedOccupied, tvBedVacant;
    // TextViews for Students/Class Status
    TextView tvStudentTotal, tvStudentOngoing, tvStudentFree;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_hostel_, container, false);

        // 1. Initialize all TextViews (IDs should match your XML)
        tvRoomTotal = view.findViewById(R.id.tvRoomTotal);
        tvRoomFull = view.findViewById(R.id.tvRoomFull);
        tvRoomAvail = view.findViewById(R.id.tvRoomAvail);

        tvBedTotal = view.findViewById(R.id.tvBedTotal);
        tvBedOccupied = view.findViewById(R.id.tvBedOccupied);
        tvBedVacant = view.findViewById(R.id.tvBedVacant);

        tvStudentTotal = view.findViewById(R.id.tvStudentTotal);
        tvStudentOngoing = view.findViewById(R.id.tvStudentOngoing);
        tvStudentFree = view.findViewById(R.id.tvStudentFree);

        // 2. Fetch Data from API
        getDashboardData();

        return view;
    }

    private void getDashboardData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.urlGetHostelStats,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        // Set Room Stats
                        if (jsonObject.has("Room")) {
                            JSONObject room = jsonObject.getJSONObject("Room");
                            tvRoomTotal.setText(room.getString("total"));
                            tvRoomFull.setText(room.getString("occupied"));
                            tvRoomAvail.setText(room.getString("available"));
                        }

                        // Set Bed Stats
                        if (jsonObject.has("Beds")) {
                            JSONObject beds = jsonObject.getJSONObject("Beds");
                            tvBedTotal.setText(beds.getString("total"));
                            tvBedOccupied.setText(beds.getString("occupied"));
                            tvBedVacant.setText(beds.getString("available"));
                        }

                        // Set Student Stats
                        if (jsonObject.has("Students")) {
                            JSONObject students = jsonObject.getJSONObject("Students");
                            tvStudentTotal.setText(students.getString("total"));
                            tvStudentOngoing.setText(students.getString("occupied"));
                            tvStudentFree.setText(students.getString("available"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getActivity(), "Network Error! Please check server.", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}