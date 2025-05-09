package com.example.lab4_20191802;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class WeatherSearchFragment extends Fragment {

    private EditText etLocation;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private List<Location> locationList;
    private final String API_URL = "http://api.weatherapi.com/v1/search.json?key=ec24b1c6dd8a4d528c1205500250305&q=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_search, container, false);

        etLocation = view.findViewById(R.id.etLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locationList = new ArrayList<>();
        locationAdapter = new LocationAdapter(locationList);
        recyclerView.setAdapter(locationAdapter);

        btnSearch.setOnClickListener(v -> {
            String location = etLocation.getText().toString().trim();
            if (!location.isEmpty()) {
                fetchLocations(location);
            } else {
                Toast.makeText(getContext(), "Ingrese una locación para buscar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void fetchLocations(String location) {
        Log.d("FETCH_LOCATIONS", "Consultando API para: " + location);
        String url = API_URL + location;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API_RESPONSE", response.toString());
                        locationList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Location loc = new Location(
                                        obj.getString("id"),
                                        obj.getString("name"),
                                        obj.getString("region"),
                                        obj.getString("country"),
                                        obj.getString("lat"),
                                        obj.getString("lon")
                                );
                                locationList.add(loc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        locationAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}