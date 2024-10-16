package com.example.tp1;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
    AdapterReservation adapteur;
    ArrayList<Reservation> reservationsList;
    ArrayList<Reservation> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_reserve);

        tv_nomResto = findViewById(R.id.tv_nomResto);
        lv_reservations = findViewById(R.id.lv_reservations);
        btn_datePicker = findViewById(R.id.btn_datePicker);


        reservationsList = new ArrayList<>();
        reservationsList = getIntent().getParcelableArrayListExtra("listeReservations");
        Restaurant selectedResto = getIntent().getParcelableExtra("leResto");

        assert selectedResto != null;
        tv_nomResto.setText(selectedResto.nomRestaurant);


        // Configuration du datePicker
        btn_datePicker.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AffichageReserve.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                        filterReservations(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // L'adapteur du ListView
        adapteur = new AdapterReservation(this, reservationsList);
        lv_reservations.setAdapter(adapteur);

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