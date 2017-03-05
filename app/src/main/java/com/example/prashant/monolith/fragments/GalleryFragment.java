package com.example.prashant.monolith.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.google.gson.Gson;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    public GalleryAdapter adapter;
    public GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        ArrayList<String> imageList = new ArrayList<>();
        adapter = new GalleryAdapter(getContext(), imageList);
        gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setNumColumns(2);
        gridView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        new ImageLoadTask().execute();
        super.onStart();
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, ArrayList<String>> {

        private final String LOG_TAG = ImageLoadTask.class.getSimpleName();

        String response = null;

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            GalleryAdapter adapter = new GalleryAdapter(getActivity(), result);
            gridView.setAdapter(adapter);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                Log.d(LOG_TAG, "Starting fetch");
                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Add the String message converter
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                // Make the HTTP GET request, marshaling the response to a String

                Gson gson = new Gson();

                String url = "https://api.nasa.gov/planetary/apod?date=2017-03-04&hd=True&api_key=DEMO_KEY";

                response = restTemplate.getForObject(url, String.class);

                Log.i("response is {}", gson.toJson(response));
                Log.d(LOG_TAG, "Finished fetch");

                try {
                    JSONObject object = new JSONObject(response);

                    String date  = object.getString("date");
                    Log.d(LOG_TAG, "date is : " + date);

                    String detail = object.getString("explanation");
                    Log.d(LOG_TAG, "explanation is : " + detail);

                    String image_path = object.getString("url");
                    Log.d(LOG_TAG, "image url is : " + image_path);

                    String title = object.getString("title");
                    Log.d(LOG_TAG, "title is : " + title);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            }
            return null;
        }
    }
}
