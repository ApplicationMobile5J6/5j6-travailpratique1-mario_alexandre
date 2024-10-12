package com.example.tp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterReservation extends BaseAdapter {
    private Context dContexte;
    private List<Reservation> reserveListe = new ArrayList<>();
    private TextView tvNomPersonne, tvNbPlaces, tvHeures;
    private ImageView imageView;
    public AdapterReservation(Context context, ArrayList<Reservation> reservations) {
        this.reserveListe = reservations;
        this.dContexte = context;
    }

    @Override
    public int getCount() {
        return reserveListe.size();
    }

    @Override
    public Reservation getItem(int position) {
        return reserveListe.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(dContexte).inflate(R.layout.list_view_elements, parent, false);
        // Obtiens la reservation
        Reservation reservation = getItem(position);

        //Donnees du ListView
        imageView = convertView.findViewById(R.id.iv_Reservation);
        tvNomPersonne = convertView.findViewById(R.id.tv_nomClient);
        tvNbPlaces = convertView.findViewById(R.id.tv_nbPlacesReserve);
        tvHeures = convertView.findViewById(R.id.tv_heureReserve);

        imageView.setImageResource(R.drawable.image_reservation);
        tvNomPersonne.setText(reservation.getNomPersonne());
        tvNbPlaces.setText("Places: " + reservation.nbPlaces);
        tvHeures.setText(reservation.getBlocReservationDebut() + " - " + reservation.getBlocReservationFin());

        // Return the completed view to render on screen
        return convertView;
    }
}
