package com.example.sara.bookstoreapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreationProduitActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private RequestQueue requestQueue;

    private static CreationProduitActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_produit);
        mInstance = this;
    }

    public static synchronized CreationProduitActivity getInstance() {
        return mInstance;
    }

    /**
     * Create a getRequestQueue() method to return the instance of
     * RequestQueue.This kind of implementation ensures that
     * the variable is instatiated only once and the same
     * instance is used throughout the application
     **/
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    /**
     * public method to add the Request to the the single
     * instance of RequestQueue created above.Setting a tag to every
     * request helps in grouping them. Tags act as identifier
     * for requests and can be used while cancelling them
     **/
    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);

    }

    /**
     * Cancel all the requests matching with the given tag
     **/

    public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.creerProduit:
                //URL of the request we are sending

                String url = "http://192.168.43.224:8000/setproduit";

/**
 JsonObjectRequest takes in five paramaters
 Request Type - This specifies the type of the request eg: GET,POST
 URL          - This String param specifies the Request URL
 JSONObject   - This parameter takes in the POST parameters.Sending this parameters
 makes this a POST request
 Listener     -This parameter takes in a implementation of Response.Listener()
 interface which is invoked if the request is successful
 Listener     -This parameter takes in a implementation of Error.Listener()
 interface which is invoked if any error is encountered while processing
 the request

 **/

                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("proNom", "TestApi");
                    postparams.put("proPrix", "10");
                    postparams.put("proImage", "http://manutentionquebec.com/wp-content/themes/manutentionquebe/images/aucune-image.jpg");
                    postparams.put("proResume", "TestApi");
                    postparams.put("proStock", "10");
                    postparams.put("catId", "1");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                //Success Callback
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                TextView textResult = (TextView) findViewById(R.id.textResult);
                                textResult.setText("Erreur, " + error.getMessage());

                                //Failure Callback

                            }
                        });

// Adding the request to the queue along with a unique string tag
                CreationProduitActivity.getInstance().addToRequestQueue(jsonObjReq, "postRequest");

                break;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
