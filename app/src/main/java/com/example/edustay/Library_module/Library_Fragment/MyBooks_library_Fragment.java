package com.example.edustay.Library_module.Library_Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.edustay.AdapterClass.BookAdapter;
import com.example.edustay.Comman.Urls;
import com.example.edustay.Model.Book;
import com.example.edustay.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyBooks_library_Fragment extends Fragment {

    RecyclerView rv;
    BookAdapter adapter;
    List<Book> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books_library_, container, false);

        rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        // Get the name saved during LoginActivity
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentUserName = sp.getString("name", "");

        if (!currentUserName.isEmpty()) {
            fetchBooks(currentUserName);
        } else {
            Toast.makeText(getContext(), "User session expired. Please login again.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void fetchBooks(String name) {
        String url = Urls.urlGetMyBookName + name.replace(" ", "%20");

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                // Check if the response is the "empty" message
                if (response.contains("\"status\":\"empty\"")) {
                    list.clear();
                    if (adapter != null) adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "You have not borrowed any books yet.", Toast.LENGTH_LONG).show();
                } else {
                    // If books are found, parse the JSONArray
                    JSONArray array = new JSONArray(response);
                    list.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        list.add(new Book(
                                obj.getString("title"),
                                obj.getString("author"),
                                obj.getString("image")
                        ));
                    }
                    adapter = new BookAdapter(list, getContext());
                    rv.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(), "Server error or no internet connection.", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(getContext()).add(request);
    }
}