package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button reserverTable, afficherReserve;
    TextView validateur_places;
    Spinner spinnerRestaurants;

    List<Restaurant> restaurantList;
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
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Chez Alex", 30, 30));
        restaurantList.add(new Restaurant(2, "Chez Mario", 16, 16));
        spinnerRestaurants = findViewById(R.id.sp_restaurants);
        reserverTable = findViewById(R.id.btn_reserver);
        afficherReserve = findViewById(R.id.btn_afficherReserv);
        validateur_places = findViewById(R.id.tv_placesDisp);

        List<String> restaurantNames = new ArrayList<>();
        for (Restaurant resto : restaurantList) {
            restaurantNames.add(resto.nomRestaurant);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, restaurantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurants.setAdapter(adapter);


        reserverTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pageReserve = new Intent(MainActivity.this, ReservationPlaces.class);
                int selectedPosition = spinnerRestaurants.getSelectedItemPosition();
                Restaurant unObjetResto = restaurantList.get(selectedPosition);
                pageReserve.putExtra("leResto",unObjetResto);
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