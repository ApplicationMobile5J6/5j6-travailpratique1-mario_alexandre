package com.example.tp1;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;

public class Reservation {

    int noReservation;
    public int getNoReservation() {
        return noReservation;
    }

    public void setNoReservation(int noReservation) {
        this.noReservation = noReservation;
    }

    int nbPlaces;
    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    DatePicker dateReservation;
    public DatePicker getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(DatePicker dateReservation) {
        this.dateReservation = dateReservation;
    }

    TimePicker blocReservationDebut;
    public TimePicker getBlocReservationDebut() {
        return blocReservationDebut;
    }

    public void setBlocReservationDebut(TimePicker blocReservationDebut) {
        this.blocReservationDebut = blocReservationDebut;
    }

    Time blocReservationFin;
    public Time getBlocReservationFin() {
        return blocReservationFin;
    }

    public void setBlocReservationFin(Time blocReservationFin) {
        this.blocReservationFin = blocReservationFin;
    }

    String nomPersonne;
    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    String telPersonne;
    public String getTelPersonne() {
        return telPersonne;
    }

    public void setTelPersonne(String telPersonne) {
        this.telPersonne = telPersonne;
    }

    public Reservation(int pNoReserv, DatePicker pDateReserv, int pNbPlaces, TimePicker pBlocDebut, String pNomPers, String pTelPers){
        noReservation = pNoReserv;
        dateReservation = pDateReserv;
        nbPlaces = pNbPlaces;
        blocReservationDebut = pBlocDebut;
        // voir comment ajouter 1:29
        //blocReservationFin = pBlocDebut.setHour(5);
        nomPersonne = pNomPers;
        telPersonne = pTelPers;
    }
}
