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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReservationPlaces extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Button btn_openDatePicker, btn_confirmReservation;
    EditText et_nom, et_phone;
    TextView et_nomResto, tv_selectedPlaces, tv_endTime, tv_nbPlaces, tv_dateReserve;
    SeekBar seekBar_nbPlaces;
    Spinner spinner_reservationTime;
    Restaurant selectedRestaurant;
    Reservation newReservation;
    ArrayList<Reservation> reservationsList = new ArrayList<>();
    int selectedSeats = 0;
    int noReservation = 1;
    String modifTexte;
    String resStart, resEnd;
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
        tv_nbPlaces = findViewById(R.id.tv_nbPlacesRestantes);
        tv_dateReserve = findViewById(R.id.et_selectedDate);
        et_nom = findViewById(R.id.et_nom);
        et_phone = findViewById(R.id.et_phone);

        btn_openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfficheDate dateAfficher = new AfficheDate();
                dateAfficher.show(getSupportFragmentManager(), "Choix Date");
            }

        });


        // Debut de la page
        modifTexte = getResources().getString(R.string.ui2_placesReservs, selectedSeats);
        tv_selectedPlaces.setText(modifTexte);

        modifTexte = getResources().getString(R.string.ui2_afficherResto, selectedRestaurant.nomRestaurant);
        et_nomResto.setText(modifTexte);

        modifTexte = getResources().getString(R.string.ui1_numPlaces, selectedRestaurant.nbPlacesRestantes);
        tv_nbPlaces.setText(modifTexte);

        seekBar_nbPlaces.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                modifTexte = getResources().getString(R.string.ui2_placesReservs, progress);
                tv_selectedPlaces.setText(modifTexte);
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
                modifTexte = getResources().getString(R.string.ui2_heure, selectedTime);
                Toast.makeText(ReservationPlaces.this, modifTexte, Toast.LENGTH_SHORT).show();
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
            modifTexte = getResources().getString(R.string.ui2_finReserve, endTime);
            tv_endTime.setText(modifTexte);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.ui2_erreurTemps, Toast.LENGTH_SHORT).show();
        }
    }

    private void makeReservation() {
        String name = et_nom.getText().toString();
        String phone = et_phone.getText().toString();

        // Valide que les champs sont remplis
        if (name.isEmpty() || phone.isEmpty() || !isValidPhoneNumber(phone)) {
            Toast.makeText(this, R.string.ui2_erreurInfos, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check si il y a assez d'espaces
        else if (selectedSeats > selectedRestaurant.nbPlacesRestantes) {
            Toast.makeText(this, R.string.ui2_pasPlaces, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // Si valide, cree une Reservation
            newReservation = new Reservation(noReservation, tv_dateReserve.getText().toString(), selectedSeats, resStart, resEnd, name, phone);
            noReservation++;
            // L'ajoute a la liste de reservations
            reservationsList.add(newReservation);

            // Met a jour le nombre de places qui restent
            selectedRestaurant.nbPlacesRestantes -= selectedSeats;
            modifTexte = getResources().getString(R.string.ui1_numPlaces, selectedRestaurant.nbPlacesRestantes);
            tv_nbPlaces.setText(modifTexte);

            // Logcat de la RESERVATION
            Log.d("Reservation", "Réservation Numéro: " + newReservation.getNoReservation());
            Log.d("Reservation", "Nombre de places: " + newReservation.getNbPlaces());
            Log.d("Reservation", "Date: " + newReservation.dateReservation);
            Log.d("Reservation", "Heure debut: " + newReservation.blocReservationDebut);

            // Message de confirmation
            Toast.makeText(this, R.string.ui2_reserveReussi, Toast.LENGTH_SHORT).show();

            // Vides les champs
            clearInputFields();

            hideKeyboard();

            Intent pageMain= new Intent(ReservationPlaces.this, MainActivity.class);
            if (selectedRestaurant.noRestaurant == 1) {
                pageMain.putParcelableArrayListExtra("listeReservations1", reservationsList);
                startActivity(pageMain);
            } else{
                pageMain.putParcelableArrayListExtra("listeReservations2", reservationsList);
                startActivity(pageMain);
            }
        }
    }

    // Valide le numero de tel
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{3}\\d{3}\\d{4}$");
    }

    // Vide les champs
    private void clearInputFields() {
        et_nom.setText("");
        et_phone.setText("");
        seekBar_nbPlaces.setProgress(0); // Reset seek bar
        modifTexte = getResources().getString(R.string.ui2_placesReservs, 0);
        tv_selectedPlaces.setText(modifTexte);
        tv_dateReserve.setText("");
    }

    // Hide the keyboard after reservation
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String dateChoisie = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        tv_dateReserve.setText(dateChoisie);
    }


}