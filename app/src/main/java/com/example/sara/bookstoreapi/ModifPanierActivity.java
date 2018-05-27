package com.example.sara.bookstoreapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModifPanierActivity extends AppCompatActivity {
    String idProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_panier);

        Bundle extras  = getIntent().getExtras();
        idProduit = extras.getString("idProduit");
    }
}
