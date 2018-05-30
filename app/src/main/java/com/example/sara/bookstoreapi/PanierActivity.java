package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class PanierActivity extends AppCompatActivity {
    String[] lesIdProduits = {};
    String[] lesIdPanier = {};
    String[] lesQtePanier = {};
    String[] lesNomsPanier = {};
    ListView lst;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        String[] items = new String[]{};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.

        SharedPreferences session = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user  = session.getString("id", null);

        //on exécute la requête get
        // Instantiate the RequestQueue.
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://act1louafisara.cnadal.fr/apiGetPanier/"+id_user;
        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        lst = (ListView) findViewById(R.id.listViewPanier);
                        String[] lesProduits = {};
                        Double total = 0.0;
                        JSONArray jsonArray = null;
                        try {
                            TextView viewTotal = findViewById(R.id.textViewTotalPanier);
                            jsonArray = new JSONArray(response);
                            if(jsonArray.length()==0){
                                viewTotal.setText("Panier vide");
                                View b = findViewById(R.id.buttonValidPanier);
                                b.setVisibility(View.GONE);
                            }

                            lesProduits = new String[jsonArray.length()];
                            lesIdProduits = new String[jsonArray.length()];
                            lesIdPanier = new String[jsonArray.length()];
                            lesQtePanier = new String[jsonArray.length()];
                            lesNomsPanier = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                JSONObject produit = item.getJSONObject("proId");
                                String nom = produit.getString("proNom");
                                String id = produit.getString("proId");
                                String quantite = item.getString("panQuantite");
                                String prix = produit.getString("proPrix");
                                String panId = item.getString("panId");
                                lesProduits[i] = nom + ", "+ prix +"€, quantité : "+ quantite + " Total = " + Double.parseDouble(prix)*Double.parseDouble(quantite) ;
                                lesIdProduits[i] = id;
                                lesIdPanier[i] = panId;
                                lesQtePanier[i] = quantite;
                                lesNomsPanier[i] = nom;

                                total += Double.parseDouble(prix)*Double.parseDouble(quantite);
                                viewTotal.setText("Total : "+String.valueOf(total)+" €");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Remplissage de la listview
                        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(PanierActivity.this, android.R.layout.simple_list_item_1, lesProduits);
                        lst.setAdapter(arrayadapter);


                        //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(PanierActivity.this, ModifPanierActivity.class);

                                //String value = lst.getAdapter().getItem(position).toString();

                                String idProduit = lesIdProduits[position];
                                String idPanier = lesIdPanier[position];
                                String qtePanier = lesQtePanier[position];
                                String nomPanier = lesNomsPanier[position];


                                intent.putExtra("idProduit", idProduit);
                                intent.putExtra("idPanier", idPanier);
                                intent.putExtra("qtePanier", qtePanier);
                                intent.putExtra("nomPanier", nomPanier);

                                // intent.putExtra("id", id);


                                startActivity(intent);
//                                    	TextView tv = (TextView) view;
//                                    	Toast.makeText(MainActivity.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
                            }

                        });
                    }

                    ;
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue2.add(stringRequest2);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonValidPanier):
                SharedPreferences session = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String id_user = session.getString("id", null);
                if (id_user != null) {
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url2 = "http://act1louafisara.cnadal.fr/apiValiderPanier/"+id_user ;
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    onRestart();
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
    public void onRestart(){
        super.onRestart();
        //Refresh your stuff here
        finish();
        startActivity(getIntent());
    }
}
