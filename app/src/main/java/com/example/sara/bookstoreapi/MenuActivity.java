package com.example.sara.bookstoreapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        switch (v.getId()) {

          /*  case (R.id.creerProduit):
                Intent fenetreCreerProduit = new Intent(this, CreationProduitActivity.class);
                startActivity(fenetreCreerProduit);
                break; */
            case (R.id.buttonGoProduits):
                Intent fenetreVoirProduits = new Intent(this, ListeProduitsActivity.class);
                startActivity(fenetreVoirProduits);
                break;
            case (R.id.buttonGoPanier):
                Intent fenetreVoirPanier = new Intent(this, PanierActivity.class);
                startActivity(fenetreVoirPanier);
                break;
            case (R.id.buttonGoMap):
                Intent fenetreVoirMap = new Intent(this, MapsActivity.class);
                startActivity(fenetreVoirMap);
                break;
            case (R.id.buttonGoCommandes):
                Intent fenetreVoirCommandes = new Intent(this, ListeCommandesActivity.class);
                startActivity(fenetreVoirCommandes);
                break;
        }
    }
}
