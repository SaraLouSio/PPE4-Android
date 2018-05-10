package com.example.sara.bookstoreapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProduitActivity extends AppCompatActivity {
    TextView textProduit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        textProduit = findViewById(R.id.textViewNomProduit);

        Bundle extras  = getIntent().getExtras();
        String idProduit;

        if (extras != null) {
            idProduit = extras.getString("idProduit");


            RequestQueue queue2 = Volley.newRequestQueue(this);
            String url2 = "http://192.168.43.224:8000/apiGet/produit/"+idProduit;
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject item = null;
                            try {
                                item = new JSONObject(response);
                               // jsonArray = new JSONArray(response);
                               // Log.d("test", response);

                                // JSONObject item = jsonArray.getJSONObject(0);
                                String id = item.getString("proId");
                                String nom = item.getString("proNom");

                              //  Log.d("nom", nom);
                                textProduit.setText(nom);

                                 //   String prix = item.getString("proPrix");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            // Add the request to the RequestQueue.
            queue2.add(stringRequest);

            // and get whatever type user account id is
        }
    }
}
