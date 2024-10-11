package com.example.tp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterReservation extends ArrayAdapter<Reservation> {

    public AdapterReservation(Context context, List<Reservation> reservations) {
        super(context, 0, reservations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtiens la reservation
        Reservation reservation = getItem(position);



        //Donnees du ListView
      /*  ImageView imageView = convertView.findViewById(R.id.image_table);
        TextView tvNomPersonne = convertView.findViewById(R.id.tv_nom_personne);
        TextView tvNbPlaces = convertView.findViewById(R.id.tv_nb_places);
        TextView tvHeures = convertView.findViewById(R.id.tv_heures);


        imageView.setImageResource(R.drawable.image_reservation.jpg);
        tvNomPersonne.setText(reservation.getNomPersonne());
        tvNbPlaces.setText("Places: " + reservation.getNbPlaces());
        tvHeures.setText("De: " + reservation.getBlocReservationDebut() + " à " + reservation.getBlocReservationFin());

        // Return the completed view to render on screen
        return convertView;
        */

    }
}
