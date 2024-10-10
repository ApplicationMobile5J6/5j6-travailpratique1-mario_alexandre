package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    Button reserverTable, afficherReserve;
    TextView validateur_places;
    Spinner spinnerRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerRestaurants = findViewById(R.id.sp_restaurants);
        reserverTable = findViewById(R.id.btn_reserver);
        afficherReserve = findViewById(R.id.btn_afficherReserv);
        validateur_places = findViewById(R.id.tv_placesDisp);


        reserverTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageReserve = new Intent(MainActivity.this, ReservationPlaces.class);
                //retient les infos du resto choisi et l'amene vers l'autre page
                //pageReserve.putExtra("leResto",unObjetResto);
                startActivity(pageReserve);
            }
        });

        afficherReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageAfficher = new Intent(MainActivity.this, AffichageReserve.class);
                startActivity(pageAfficher);
            }
        });

        // Verifie si les places disponibles sont inferieures ou egales a 4
        // Pour l'instant, on change juste la couleur pour la verification
        if (true){
            validateur_places.setTextColor(ContextCompat.getColor(validateur_places.getContext(), R.color.rouge));
        }

    }
}