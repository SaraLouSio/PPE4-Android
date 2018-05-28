package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListeCommandesActivity extends AppCompatActivity {
    String[] lesIdCommandes = {};
    String[] lesDatesCommandes = {};
    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_commandes);
        String[] items = new String[]{};

        SharedPreferences session = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user  = session.getString("id", null);
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://act1louafisara.cnadal.fr/apiGetCommandes/"+id_user;
        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lst = (ListView) findViewById(R.id.listViewCommandes);
                        String[] lesCommandes = {};

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            lesCommandes = new String[jsonArray.length()];
                            lesIdCommandes = new String[jsonArray.length()];
                            lesDatesCommandes = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                String id = item.getString("comId");
                                String date = item.getString("comDate").substring(0,10);
                                lesCommandes[i] = id + " : "+date ;
                                lesIdCommandes[i] = id;
                                lesDatesCommandes[i] = date;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Remplissage de la listview
                        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(ListeCommandesActivity.this, android.R.layout.simple_list_item_1, lesCommandes);
                        lst.setAdapter(arrayadapter);


                        //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(ListeCommandesActivity.this, CommandeActivity.class);

                                //String value = lst.getAdapter().getItem(position).toString();

                                String idCommande = lesIdCommandes[position];
                                String dateCommande = lesDatesCommandes[position];

                                intent.putExtra("idCommande", idCommande);
                                intent.putExtra("dateCommande", dateCommande);

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
