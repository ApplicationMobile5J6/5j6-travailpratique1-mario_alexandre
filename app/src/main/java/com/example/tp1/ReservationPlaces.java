package com.example.tp1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReservationPlaces extends AppCompatActivity {
    Button btn_openDatePicker, btn_confirmReservation;
    EditText et_selectedDate, et_nom, et_phone;
    TextView et_nomResto, tv_selectedPlaces, tv_endTime;
    SeekBar seekBar_nbPlaces;
    Spinner spinner_reservationTime;
    Restaurant selectedRestaurant;
    ArrayList<Reservation> reservationsList = new ArrayList<>();
    int selectedSeats = 0;
    int noReservation = 1;
    String resDate,resStart, resEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_places);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        selectedRestaurant = intent.getParcelableExtra("leResto");
        et_nomResto = findViewById(R.id.tv_nomResto);
        btn_openDatePicker = findViewById(R.id.btn_openDatePicker);
        btn_confirmReservation = findViewById(R.id.btn_confirmReservation);
        seekBar_nbPlaces = findViewById(R.id.seekBar_nbPlaces);
        tv_selectedPlaces = findViewById(R.id.tv_selectedPlaces);
        spinner_reservationTime = findViewById(R.id.spinner_reservation_time);
        tv_endTime = findViewById(R.id.tv_endTime);
        et_nom = findViewById(R.id.et_nom);
        et_phone = findViewById(R.id.et_phone);
        btn_openDatePicker.setOnClickListener(v -> showDatePickerDialog());
        seekBar_nbPlaces.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_selectedPlaces.setText("Selected Places: " + progress);
                selectedSeats = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn_confirmReservation.setOnClickListener(view -> makeReservation());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.time_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reservationTime.setAdapter(adapter);

        spinner_reservationTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTime = parent.getItemAtPosition(position).toString();
                Toast.makeText(ReservationPlaces.this, "Selected Time: " + selectedTime, Toast.LENGTH_SHORT).show();
                updateEndTime(selectedTime);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void updateEndTime(String startTime) {
        resStart = startTime;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(startTime));

            // Ajoute 1h et 29 minutes
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.add(Calendar.MINUTE, 29);

            // Affiche le temps de fin
            String endTime = sdf.format(calendar.getTime());
            resEnd = endTime;
            tv_endTime.setText("End Time: " + endTime);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error calculating end time", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ReservationPlaces.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    et_selectedDate.setText(date);
                    resDate = date;
                }, year, month, day);

        datePickerDialog.show();
    }

    private void makeReservation() {
        String name = et_nom.getText().toString();
        String phone = et_phone.getText().toString();

        // Valide que les champs sont remplis
        if (name.isEmpty() || phone.isEmpty() || !isValidPhoneNumber(phone)) {
            Toast.makeText(this, "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check si il y a assez d'espaces
        if (selectedSeats > selectedRestaurant.nbPlacesRestantes) {
            Toast.makeText(this, "Il n'y a pas assez de places pour cette réservation", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si valide, cree une Reservation
        Reservation newReservation = new Reservation(noReservation, resDate, selectedSeats, resStart, resEnd, name, phone);

        // L'ajoute a la liste de reservations
        reservationsList.add(newReservation);

        // Met a jour le nombre de places qui restent
        selectedRestaurant.nbPlacesRestantes -= selectedSeats;

        // Logcat
        Log.d("Reservation", "Réservation Numéro: " + newReservation.getNoReservation());
        Log.d("Reservation", "Nombre de places: " + newReservation.getNbPlaces());
        Log.d("Reservation", "Restaurant: " + selectedRestaurant.nomRestaurant);

        // Message de confirmation
        Toast.makeText(this, "Réservation sauvegardée", Toast.LENGTH_SHORT).show();

        // Vides les champs
        clearInputFields();

        hideKeyboard();
    }

    // Valide le numero de tel
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
    }

    // Vide les champs
    private void clearInputFields() {
        et_nom.setText("");
        et_phone.setText("");
        seekBar_nbPlaces.setProgress(0); // Reset seek bar
        tv_selectedPlaces.setText("Places: 0");
    }

    // Hide the keyboard after reservation
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}