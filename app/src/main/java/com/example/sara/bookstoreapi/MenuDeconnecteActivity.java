package com.example.sara.bookstoreapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuDeconnecteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deconnecte);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonGoProduits2):
                Intent fenetreVoirProduits = new Intent(this, ListeProduitsActivity.class);
                startActivity(fenetreVoirProduits);
                break;
            case (R.id.buttonGoMap2):
                Intent fenetreVoirMap = new Intent(this, MapsActivity.class);
                startActivity(fenetreVoirMap);
                break;
        }
    }

}
