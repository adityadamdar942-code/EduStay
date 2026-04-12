package com.example.edustay.Library_module.Library_Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.edustay.AdapterClass.GetAllBooksAdapterClass;
import com.example.edustay.Comman.Urls;
import com.example.edustay.POJOClass.GetAllBooksPOJOClass;
import com.example.edustay.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Home_library_Fragment extends Fragment {

    SearchView svsearchBooks;
    TextView tvNoBooksAvailable;
    GridView lvBooks;
    ImageSlider imageSlider;

    List<GetAllBooksPOJOClass> getAllBooksPOJOClasses;
    GetAllBooksAdapterClass getAllBooksAdapterClass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_library_, container, false);

        // Initialize UI Components
        imageSlider = view.findViewById(R.id.isHomeFragmentImageSlider);
        svsearchBooks = view.findViewById(R.id.et_library_homeFrg_SearchBooks);
        tvNoBooksAvailable = view.findViewById(R.id.tv_library_homeFrg_BookNotAvailable);
        lvBooks = view.findViewById(R.id.gvLibrary_homeFrag_Books);

        getAllBooksPOJOClasses = new ArrayList<>();

        // Search Logic
        svsearchBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBooks(newText);
                return true;
            }
        });

        loadImageSlider();
        getAllBooks();

        return view;
    }

    private void searchBooks(String query) {
        List<GetAllBooksPOJOClass> tempSearchBook = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            tempSearchBook.addAll(getAllBooksPOJOClasses);
        } else {
            String searchText = query.toLowerCase().trim();
            for (GetAllBooksPOJOClass obj : getAllBooksPOJOClasses) {
                if (obj.getBookName().toLowerCase().contains(searchText)
                        || obj.getBookAuthor().toLowerCase().contains(searchText)) {
                    tempSearchBook.add(obj);
                }
            }
        }

        // Update Adapter with filtered list
        getAllBooksAdapterClass = new GetAllBooksAdapterClass(tempSearchBook, getActivity());
        lvBooks.setAdapter(getAllBooksAdapterClass);

        if (tempSearchBook.isEmpty()) {
            tvNoBooksAvailable.setVisibility(View.VISIBLE);
            lvBooks.setVisibility(View.GONE);
        } else {
            tvNoBooksAvailable.setVisibility(View.GONE);
            lvBooks.setVisibility(View.VISIBLE);
        }
    }

    private void loadImageSlider() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.basicsof_civil_engineering_bookimage_slider, "Basics of Civil Engineering", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.data_engineering_bookimage_slider, "Data Engineering", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.software_engineering_image_slider, "Software Engineering", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    private void getAllBooks() {
        AsyncHttpClient client = new AsyncHttpClient();

        // Use GET instead of PUT for fetching data usually
        client.get(Urls.urlGetAllBooks, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    getAllBooksPOJOClasses.clear();
                    JSONArray jsonArray = response.getJSONArray("getAllBooks");

                    if (jsonArray.length() == 0) {
                        tvNoBooksAvailable.setVisibility(View.VISIBLE);
                        lvBooks.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            getAllBooksPOJOClasses.add(new GetAllBooksPOJOClass(
                                    jsonObject.optString("id"),
                                    jsonObject.optString("bookName"),
                                    jsonObject.optString("bookAuthor"),
                                    jsonObject.optString("bookImage").trim()
                            ));
                        }

                        // Set adapter after data is loaded
                        getAllBooksAdapterClass = new GetAllBooksAdapterClass(getAllBooksPOJOClasses, getActivity());
                        lvBooks.setAdapter(getAllBooksAdapterClass);

                        tvNoBooksAvailable.setVisibility(View.GONE);
                        lvBooks.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Log.e("JSON_ERR", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Server Connectivity Issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}