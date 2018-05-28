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

          /*  case (R.id.creerProduit):
                Intent fenetreCreerProduit = new Intent(this, CreationProduitActivity.class);
                startActivity(fenetreCreerProduit);
                break; */
            case (R.id.buttonNoConnexion):
                Intent fenetreVoirProduits = new Intent(this, MenuDeconnecteActivity.class);
                startActivity(fenetreVoirProduits);
                SharedPreferences session = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = session.edit();
                edit.clear();
                edit.commit();
                break;
            case(R.id.buttonConnexion):
                //on exécute la requête post
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this);
               // String ip = "192.168.43.224:8000";
                String ip = "act1louafisara.cnadal.fr";
                String url = "http://"+ip+"/Api/Connexion";
                // Request a string response from the provided URL.
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                TextView res = findViewById(R.id.textViewRes);
                                if(response.equals("identifiant ou mot de passe invalide !")){
                                    res.setText(response);
                                } else {
                                    try {
                                      //  res.setText("Test 02");
                                       // res.setText(response);
                                        JSONObject reponse = null;
                                        reponse = new JSONObject(response);
                                        Intent intentSucces = new Intent(MainActivity.this, MenuActivity.class);
                                        SharedPreferences session = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                        //recup info
                                        //session.getString("id", null);
                                        SharedPreferences.Editor edit = session.edit();
                                        edit.putString("id", reponse.get("id").toString());

                                        //enregistrer session
                                        edit.commit();

                                        res.setText("Connexion réussie");

                                        startActivity(intentSucces);
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

        SharedPreferences session = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = session.edit();
        edit.clear();
        edit.commit();

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

