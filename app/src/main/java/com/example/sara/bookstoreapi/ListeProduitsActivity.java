package com.example.sara.bookstoreapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListeProduitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);


        //on exécute la requête get
        // Instantiate the RequestQueue.
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://192.168.43.224:8000/apiGet/produits/all";
        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ListView lst;
                        lst = (ListView) findViewById(R.id.listViewProduits);
                        //parcours des Totos retournés
                        String[] lesProduits = {};
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            lesProduits = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                String id = item.getString("proId");
                                String nom = item.getString("proNom");
                                String prix = item.getString("proPrix");
                                lesProduits[i] = nom + " - " + prix + "€"; //attention erreur ici à corriger.
                                System.out.println("nom" + nom + "/");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Remplissage de la listview
                        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(ListeProduitsActivity.this, android.R.layout.simple_list_item_1, lesProduits);
                        lst.setAdapter(arrayadapter);

                        //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
