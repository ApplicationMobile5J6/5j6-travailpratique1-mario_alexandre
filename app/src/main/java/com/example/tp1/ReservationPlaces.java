package com.example.tp1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ReservationPlaces extends AppCompatActivity {
    Button btn_openDatePicker, btn_confirmReservation;
    EditText et_selectedDate;
    TextView et_nomResto, tv_selectedPlaces;
    SeekBar seekBar_nbPlaces;

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
        Restaurant selectedRestaurant = intent.getParcelableExtra("leResto");
        et_nomResto = findViewById(R.id.tv_nomResto);
        btn_openDatePicker = findViewById(R.id.btn_openDatePicker);
        btn_confirmReservation = findViewById(R.id.btn_confirmReservation);
        seekBar_nbPlaces = findViewById(R.id.seekBar_nbPlaces);
        tv_selectedPlaces = findViewById(R.id.tv_selectedPlaces);
        btn_openDatePicker.setOnClickListener(v -> showDatePickerDialog());
        seekBar_nbPlaces.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update TextView with selected number of places
                tv_selectedPlaces.setText("Selected Places: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                }, year, month, day);

        datePickerDialog.show();
    }
}