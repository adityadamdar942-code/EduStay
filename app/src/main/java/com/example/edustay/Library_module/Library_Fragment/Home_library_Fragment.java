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

        imageSlider = view.findViewById(R.id.isHomeFragmentImageSlider);
        svsearchBooks = view.findViewById(R.id.et_library_homeFrg_SearchBooks);
        tvNoBooksAvailable = view.findViewById(R.id.tv_library_homeFrg_BookNotAvailable);
        lvBooks = view.findViewById(R.id.gvLibrary_homeFrag_Books);

        getAllBooksPOJOClasses = new ArrayList<>();
        getAllBooksAdapterClass = new GetAllBooksAdapterClass(getAllBooksPOJOClasses, requireActivity());
        lvBooks.setAdapter(getAllBooksAdapterClass);

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

        getAllBooksAdapterClass = new GetAllBooksAdapterClass(tempSearchBook, requireActivity());
        lvBooks.setAdapter(getAllBooksAdapterClass);

        if (tempSearchBook.isEmpty()) {
            tvNoBooksAvailable.setVisibility(View.VISIBLE);
            tvNoBooksAvailable.setText("No Books Found");
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
        RequestParams params = new RequestParams();

        client.put(Urls.urlGetAllBooks, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    getAllBooksPOJOClasses.clear();

                    JSONArray jsonArray = response.getJSONArray("getAllBooks");

                    if (jsonArray.length() == 0) {
                        tvNoBooksAvailable.setVisibility(View.VISIBLE);
                        lvBooks.setVisibility(View.GONE);
                        return;
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String strID = jsonObject.optString("id", "");
                        String strbookName = jsonObject.optString("bookName", "");
                        String strbookAuthor = jsonObject.optString("bookAuthor", "");
                        String strbookImage = jsonObject.optString("bookImage", "").trim();

                        Log.e("BOOK_JSON", "bookImage = " + strbookImage);

                        getAllBooksPOJOClasses.add(
                                new GetAllBooksPOJOClass(strID, strbookName, strbookAuthor, strbookImage)
                        );
                    }

                    getAllBooksAdapterClass = new GetAllBooksAdapterClass(getAllBooksPOJOClasses, requireActivity());
                    lvBooks.setAdapter(getAllBooksAdapterClass);

                    tvNoBooksAvailable.setVisibility(View.GONE);
                    lvBooks.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (isAdded() && getContext() != null) {
                        Toast.makeText(getContext(), "Data parsing error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                tvNoBooksAvailable.setVisibility(View.VISIBLE);
                lvBooks.setVisibility(View.GONE);

                if (isAdded() && getContext() != null) {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }

                Log.e("BOOK_ERROR", "API failed", throwable);
            }
        });
    }
}

