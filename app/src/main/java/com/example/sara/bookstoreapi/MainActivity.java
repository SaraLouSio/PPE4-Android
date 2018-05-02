package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    final String MyPREFERENCES = "MyPrefs";
    SharedPreferences session;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //première consommation du webservice, un bouton va obtenir une information et la placer dans un textview
/*            case R.id.buttonApi:

// un champ textview est créé sur l'activity
                final TextView mTextView = (TextView) findViewById(R.id.textDonnee);
                // Instantiate the RequestQueue. La requête sera gérée par Volley
                RequestQueue queue = Volley.newRequestQueue(this);
                ///*//*****  Attention, il faut ici préciser l'url de votre serveur
                ////    	Le terminal mobile et le serveur doivent être dans
                ////    	le même réseau pour communiquer  *********************
                String url = "http://192.168.43.224:8000/apiGet/produits/all";
                // Request a string response from the provided URL.
                // Méthode GET !!!!
                // On met en place un listener (écouteur), onResponse sera exécuté lorque la réponse sera là.

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                mTextView.setText(response);
                            }
                        }, new Response.ErrorListener() {        // CAS d’ERREUR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("Erreur, " + error.getMessage());
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                break;
            //seconde consommation du webservice, un bouton va obtenir une information et la placer dans une listview
            case R.id.buttonApiListe:
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
                                        lesProduits[i] = nom + " - " + prix + "€";
                                        System.out.println("nom" + nom + "/");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Remplissage de la listview
                                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lesProduits);
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

                break;*/
          /*  case (R.id.creerProduit):
                Intent fenetreCreerProduit = new Intent(this, CreationProduitActivity.class);
                startActivity(fenetreCreerProduit);
                break; */
            case (R.id.buttonNoConnexion):
                Intent fenetreVoirProduits = new Intent(this, ListeProduitsActivity.class);
                startActivity(fenetreVoirProduits);
                break;
            case(R.id.buttonConnexion):
                //on exécute la requête post
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this);
                String ip = "192.168.43.224:8000";
                String url = "http://"+ip+"/Api/Connexion";
                // Request a string response from the provided URL.
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                if(response.equals("identifiant ou mot de passe invalide !")){
                                    TextView res = findViewById(R.id.textViewRes);
                                    res.setText(response);
                                } else {
                                    try {
                                        JSONObject reponse = null;
                                        reponse = new JSONObject(response);
                                        //Intent intentSucces = new Intent(connexion, MenuConnecter.class);
                                        SharedPreferences session = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                        //recup info
                                        //session.getString("id", null);
                                        SharedPreferences.Editor edit = session.edit();
                                        edit.putString("pseudo", reponse.get("pseudo").toString());
                                        edit.putString("id", reponse.get("id").toString());

                                        //enregistrer session
                                        edit.commit();

                                        //startActivity(intentSucces);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        TextView id = findViewById(R.id.editTextUser);
                        TextView mdp = findViewById(R.id.editTextPassword);
                        String username = id.getText().toString();
                        String password = mdp.getText().toString();
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("username", username );
                        params.put("password", password);

                        return params;
                    }
                };
                queue.add(postRequest);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}

