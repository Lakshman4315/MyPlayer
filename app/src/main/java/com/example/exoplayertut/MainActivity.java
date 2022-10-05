package com.example.exoplayertut;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<data> mUrlArray;
    private RecyclerView mRecyclerView;
    private recyclerViewAdapter mAdapter;
    private RequestQueue mRe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUrlArray = new ArrayList<>();
        mAdapter = new recyclerViewAdapter(this, mUrlArray);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);

        mRe = Volley.newRequestQueue(this);
        getVideoUrl();

    }

    private void getVideoUrl(){
        String videoUrl="https://pixabay.com/api/videos/?key="+getString(R.string.APIKey)+"&q=yellow+flowers";
        //                            Log.e("response", response.getJSONObject("hits").get("videos").toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, videoUrl, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            mUrlArray.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String url = jsonArray.getJSONObject(i).getJSONObject("videos").getJSONObject("large").getString("url");
                                mUrlArray.add(new data(url));
                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.d("response", "error")
        );
        mRe.add(jsonObjectRequest);
    }

}