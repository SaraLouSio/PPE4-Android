package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ModifPanierActivity extends AppCompatActivity {
    String idProduit;
    String idPanier;
    String qtePanier;
    String nomPanier;
    TextView viewNom;
    TextView viewQte;
    Integer quantite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_panier);

        viewNom = findViewById(R.id.textViewNomProduitModif);
        viewQte = findViewById(R.id.textViewQuantitePanier);

        Bundle extras  = getIntent().getExtras();
        idProduit = extras.getString("idProduit");
        idPanier = extras.getString("idPanier");
        qtePanier = extras.getString("qtePanier");
        nomPanier = extras.getString("nomPanier");
        quantite = Integer.parseInt(qtePanier);
        viewQte.setText(String.valueOf(quantite));
        viewNom.setText(nomPanier);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonSuppPanier):
                if (idPanier != null) {
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url2 = "http://act1louafisara.cnadal.fr/apiSupprPanier/"+idPanier ;
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Intent intentSucces = new Intent(ModifPanierActivity.this, PanierActivity.class);
                                    startActivity(intentSucces);
                                    finish();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(stringRequest);
                } break;
            case (R.id.buttonPanierPlus):
                if (idPanier != null) {
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url2 = "http://act1louafisara.cnadal.fr/apiPlusPanier/"+idPanier ;
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(stringRequest);
                    quantite ++;
                    viewQte.setText(String.valueOf(quantite));


                } break;
            case (R.id.buttonPanierMoins):
                if (idPanier != null) {
                    if(quantite>1) {
                        RequestQueue queue = Volley.newRequestQueue(this);
                        String url2 = "http://act1louafisara.cnadal.fr/apiMoinsPanier/" + idPanier;
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        queue.add(stringRequest);
                        quantite--;
                        viewQte.setText(String.valueOf(quantite));
                    }
                    else{
                        RequestQueue queue = Volley.newRequestQueue(this);
                        String url2 = "http://act1louafisara.cnadal.fr/apiSupprPanier/"+idPanier ;
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Intent intentSucces = new Intent(ModifPanierActivity.this, PanierActivity.class);
                                        startActivity(intentSucces);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        queue.add(stringRequest);
                    }


                } break;
            case (R.id.buttonPanierPageProduit):
                Intent intentSucces = new Intent(ModifPanierActivity.this, ProduitActivity.class);
                intentSucces.putExtra("idProduit", idProduit);

                startActivity(intentSucces);
                break;
        }
    }
}
