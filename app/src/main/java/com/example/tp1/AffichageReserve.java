package com.example.tp1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AffichageReserve extends AppCompatActivity {

    TextView tv_nomResto;
    ListView lv_reservations;
    Button btn_datePicker;
    ArrayList<Reservation> reservationsList;
    ArrayList<Reservation> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_reserve);

        tv_nomResto = findViewById(R.id.tv_nomResto);
        lv_reservations = findViewById(R.id.lv_reservations);
        btn_datePicker = findViewById(R.id.btn_datePicker);

        Intent affichageListe = getIntent();
        reservationsList = new ArrayList<>();
        Parcelable[] reservationsArray = affichageListe.getParcelableArrayExtra("listeReservations");
        Restaurant selectedResto = affichageListe.getParcelableExtra("leResto");

        tv_nomResto.setText(selectedResto.nomRestaurant);

        // Convertit le parcelable en array
        if (reservationsArray != null) {
            for (Parcelable parcelable : reservationsArray) {
                reservationsList.add((Reservation) parcelable);
            }
        }


        // Configuration du datePicker
        btn_datePicker.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AffichageReserve.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        // Format the date
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                        filterReservations(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // L'adapteur du ListView
        lv_reservations.setAdapter(new AdapterReservation(this, reservationsList));


        lv_reservations.setOnItemClickListener((parent, view, position, id) -> {
            Reservation selectedReservation = reservationsList.get(position);
            Toast.makeText(this, "Numéro de réservation: " + selectedReservation.getNoReservation() + "\nTéléphone: " + selectedReservation.getTelPersonne(), Toast.LENGTH_LONG).show();
        });
    }

    private void filterReservations(String date) {
        filteredList = new ArrayList<>();
        for (Reservation reservation : reservationsList) {
            if (reservation.getDateReservation().equals(date)) {
                filteredList.add(reservation);
            }
        }
        Collections.sort(filteredList, (o1, o2) -> o1.getBlocReservationDebut().compareTo(o2.getBlocReservationDebut()));
        lv_reservations.setAdapter(new AdapterReservation(this, filteredList));
    }
}