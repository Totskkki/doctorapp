package com.example.myapplication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchAdapter;
import com.example.myapplication.viewmodel.Announcement;
import com.example.myapplication.viewmodel.DoctorSchedule;

import com.example.myapplication.viewmodel.PatientInfo;
import com.example.myapplication.viewmodel.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import api.ApiService;
import api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView,resultsRecyclerView;
    private SearchAdapter adapter;
    private List<Announcement> announcements = new ArrayList<>();
    private List<DoctorSchedule> doctorSchedules = new ArrayList<>();
    private List<PatientInfo> patients = new ArrayList<>();

    private ImageButton closeButtonsearch;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchEditText = findViewById(R.id.searchEditText);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton closeButton = findViewById(R.id.closeButtonsearch);

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                backButton.setVisibility(View.VISIBLE);
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    closeButton.setVisibility(View.VISIBLE);
                } else {
                    closeButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        backButton.setOnClickListener(v -> onBackPressed());
        closeButton.setOnClickListener(v -> searchEditText.setText(""));


        searchEditText = findViewById(R.id.searchEditText);
        closeButtonsearch = findViewById(R.id.closeButtonsearch);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);

        searchAdapter = new SearchAdapter(this, new ArrayList<>());
        resultsRecyclerView.setAdapter(searchAdapter);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call the API whenever the search input changes
                searchForData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }
    private void searchForData(String searchTerm) {
        // Create Retrofit instance and API interface
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SearchResponse> call = apiService.fetchAllData(searchTerm);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update RecyclerView with new data
                    List<Object> items = new ArrayList<>();
                    items.addAll(response.body().getAnnouncements());
                    items.addAll(response.body().getPatients());
                    searchAdapter.updateData(items);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(SearchResultsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }

        });
    }



    private void filterResults(String query) {
        // Implement filtering logic here if needed
        Log.d("FILTER", "Filter with query: " + query);
    }
}
