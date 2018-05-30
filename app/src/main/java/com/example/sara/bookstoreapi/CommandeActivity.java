package com.example.sara.bookstoreapi;

import android.content.Intent;
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

public class CommandeActivity extends AppCompatActivity {
    String[] lesIdProduits = {};
    ListView lst;
    String idCommande;
    String dateCommande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        String[] items = new String[]{};
        Bundle extras  = getIntent().getExtras();
        idCommande = extras.getString("idCommande");
        dateCommande = extras.getString("dateCommande");
        TextView textDate = findViewById(R.id.textViewContenu);
        textDate.setText("Commandé le " + dateCommande);
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://act1louafisara.cnadal.fr/apiGetCommande/"+idCommande;
        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lst = (ListView) findViewById(R.id.listViewContenu);
                        String[] lesCommandes = {};

                        Double total = 0.0;

                        JSONArray jsonArray = null;
                        try {
                            TextView viewTotal = findViewById(R.id.textViewTotalContenu);
                            Log.d("response",response);
                            jsonArray = new JSONArray(response);
                            lesCommandes = new String[jsonArray.length()];
                            lesIdProduits = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                JSONObject produit = item.getJSONObject("proId");
                                String nom = produit.getString("proNom");
                                String id = produit.getString("proId");
                                String prix = item.getString("contenuPrix");
                                String quantite = item.getString("contenuQuantite");

                                lesCommandes[i] = nom + ", "+ prix +"€, quantité : "+ quantite + " Total = " + Double.parseDouble(prix)*Double.parseDouble(quantite) ;

                                lesIdProduits[i] = id;

                                total += Double.parseDouble(prix)*Double.parseDouble(quantite);
                                viewTotal.setText("Total : "+String.valueOf(total)+" €");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (String commande : lesCommandes) {
                            Log.d("array",commande);

                        }

                        //Remplissage de la listview
                        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(CommandeActivity.this, android.R.layout.simple_list_item_1, lesCommandes);
                        lst.setAdapter(arrayadapter);


                        //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(CommandeActivity.this, ProduitActivity.class);

                                //String value = lst.getAdapter().getItem(position).toString();

                                String idProduit = lesIdProduits[position];

                                intent.putExtra("idProduit", idProduit);
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
}
