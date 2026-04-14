package com.example.edustay.Hostel_module.Hostel_Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.edustay.AdapterClass.RoomAdapter;
import com.example.edustay.Comman.Urls;
import com.example.edustay.Model.RoomModel;
import com.example.edustay.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

 public class RoomInfo_hostel_Fragment extends Fragment {

    RecyclerView recyclerView;
    RoomAdapter adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_info_hostel_, container, false);

        recyclerView = view.findViewById(R.id.rvRoomInfo);
        progressBar = view.findViewById(R.id.progressBar); // Don't forget this!

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        loadRoomData();
        return view;
    }

    private void loadRoomData() {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sp = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String studentId = sp.getString("id", "");


        String url = Urls.urlGetRoomInfo + studentId;

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            progressBar.setVisibility(View.GONE);
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>(){}.getType();
                List<RoomModel> roomList = gson.fromJson(response, listType);

                if (roomList != null) {
                    adapter = new RoomAdapter(roomList, getContext(), studentId); // Pass studentId to adapter
                    recyclerView.setAdapter(adapter);
                }
            } catch (Exception e) {
                Log.e("ROOM_ERROR", "Error: " + e.getMessage());
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
        });

        Volley.newRequestQueue(requireContext()).add(request);
    }
}
