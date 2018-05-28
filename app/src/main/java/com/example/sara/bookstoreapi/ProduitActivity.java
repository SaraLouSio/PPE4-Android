package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.net.URL;

public class ProduitActivity extends AppCompatActivity {
    TextView textProduit ;
    ImageView imgProduit;
    TextView textPrix ;
    TextView textStock ;
    TextView textDescription ;
    String idProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        textProduit = findViewById(R.id.textViewNomProduit);
        textPrix = findViewById(R.id.textViewPrix);
        textStock = findViewById(R.id.textViewStock);
        textDescription = findViewById(R.id.textViewDescription);

        textDescription.setMovementMethod(new ScrollingMovementMethod());

        imgProduit = findViewById(R.id.imageViewProduit);

        Bundle extras  = getIntent().getExtras();


        SharedPreferences session = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        String id_user  = session.getString("id", null);
        if(id_user==null){
            View b = findViewById(R.id.buttonPanier);
            b.setVisibility(View.GONE);
        }

        if (extras != null) {
            idProduit = extras.getString("idProduit");


            RequestQueue queue2 = Volley.newRequestQueue(this);
            String url2 = "http://act1louafisara.cnadal.fr/apiGet/produit/"+idProduit;
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
                                String imgURL = item.getString("proImage");
                                String stock = item.getString("proStock");
                                String resume = item.getString("proResume");
                                String prix = item.getString("proPrix");




                                //  Log.d("nom", nom);
                                textProduit.setText(nom);
                                textPrix.setText("Prix : "+prix+" €");
                                textDescription.setText(resume);
                                textStock.setText(stock+" en stock");
                                new DownLoadImageTask(imgProduit).execute(imgURL);


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
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonPanier):
                Log.d("button","test Ok");
                SharedPreferences session = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String id_user = session.getString("id", null);
                if (id_user != null) {
                    Log.d("id user",id_user);
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url2 = "http://act1louafisara.cnadal.fr/apiPanier/ajouter/"+id_user+"/"+idProduit ;
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        Intent intentSucces = new Intent(ProduitActivity.this, PanierActivity.class);
                                        startActivity(intentSucces);
                                    }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(stringRequest);
                } break;
        }
    }
}



