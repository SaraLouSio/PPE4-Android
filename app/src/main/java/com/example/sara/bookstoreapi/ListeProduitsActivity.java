package com.example.sara.bookstoreapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ListeProduitsActivity extends AppCompatActivity {

    String[] lesIdProduits = {};
    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner_categorie);
        //create a list of items for the spinner.
        String[] items = new String[]{};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        //on exécute la requête get
        // Instantiate the RequestQueue.
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://act1louafisara.cnadal.fr/apiGet/produits/all";
        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lst = (ListView) findViewById(R.id.listViewProduits);
                        //parcours des Totos retournés
                        String[] lesProduits = {};

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            lesProduits = new String[jsonArray.length()];
                            lesIdProduits = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                String id = item.getString("proId");
                                String nom = item.getString("proNom");
                                String prix = item.getString("proPrix");
                                lesProduits[i] = nom ;
                                lesIdProduits[i] = id;
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
                                Intent intent = new Intent(ListeProduitsActivity.this, ProduitActivity.class);

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
