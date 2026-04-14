package com.example.edustay.Hostel_module.Hostel_Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_info_hostel_, container, false);

        recyclerView = view.findViewById(R.id.rvRoomInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ScrollView च्या आत स्मूथ स्क्रोलिंगसाठी
        recyclerView.setNestedScrollingEnabled(false);

        loadRoomData();
        return view;
    }

    private void loadRoomData() {
        // SharedPreferences मधून लॉगिन आयडी मिळवा
        SharedPreferences sp = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String studentId = sp.getString("id", "1");

        // तुमचा IP पत्ता तपासा (ipconfig ने मिळणारा)
        String url = Urls.urlGetRoomInfo + studentId;

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RoomModel>>(){}.getType();
                List<RoomModel> roomList = gson.fromJson(response, listType);

                adapter = new RoomAdapter(roomList, getContext());
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getContext(), "Server Error!", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(requireContext()).add(request);
    }
}