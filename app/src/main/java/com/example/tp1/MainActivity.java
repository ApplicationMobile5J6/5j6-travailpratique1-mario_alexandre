package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
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
    String modifTexteNbPlaces;
    Intent affichageListe;
    ArrayList<Restaurant> restaurantList;
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
        affichageListe = getIntent();

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
                int selectedPosition = spinnerRestaurants.getSelectedItemPosition();
                Restaurant unObjetResto = restaurantList.get(selectedPosition);
                ArrayList<Reservation> reservationsListe;
               if  (unObjetResto.noRestaurant ==1) {
                    reservationsListe = affichageListe.getParcelableArrayListExtra("listeReservations1");
                   pageAfficher.putExtra("listeReservations", reservationsListe);
               } else {
                   reservationsListe = affichageListe.getParcelableArrayListExtra("listeReservations2");
                   pageAfficher.putExtra("listeReservations", reservationsListe);
               }
                pageAfficher.putExtra("leResto",unObjetResto);
                startActivity(pageAfficher);
                finish();
            }
        });

        spinnerRestaurants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int afficherNbPlaces = spinnerRestaurants.getSelectedItemPosition();
                Restaurant nbResto = restaurantList.get(afficherNbPlaces);

                modifTexteNbPlaces = getResources().getString(R.string.ui1_numPlaces, nbResto.nbPlacesRestantes);
                validateur_places.setText(modifTexteNbPlaces);

                if (nbResto.nbPlacesRestantes < 5){
                    validateur_places.setTextColor(ContextCompat.getColor(validateur_places.getContext(), R.color.rouge));
                }
                else {
                    validateur_places.setTextColor(ContextCompat.getColor(validateur_places.getContext(), R.color.white));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}