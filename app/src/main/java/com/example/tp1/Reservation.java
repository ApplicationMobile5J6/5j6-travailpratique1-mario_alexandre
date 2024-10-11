package com.example.tp1;

import android.os.Parcelable;
import android.os.Parcel;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

public class Reservation implements Parcelable {

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

    String dateReservation;
    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    String blocReservationDebut;
    public String getBlocReservationDebut() {
        return blocReservationDebut;
    }

    String blocReservationFin;
    public String getBlocReservationFin() {
        return blocReservationFin;
    }

    public void setBlocReservationDebut(String blocReservationDebut) {
        this.blocReservationDebut = blocReservationDebut;
    }
    public void setBlocReservationFin(String blocReservationFin) {
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

    public Reservation(int pNoReserv, String pDateReserv, int pNbPlaces, String pBlocDebut, String pBlocFin, String pNomPers, String pTelPers){
        noReservation = pNoReserv;
        dateReservation = pDateReserv;
        nbPlaces = pNbPlaces;
        blocReservationDebut = pBlocDebut;
        blocReservationFin = pBlocFin;
        nomPersonne = pNomPers;
        telPersonne = pTelPers;
    }

    protected Reservation(Parcel in) {
        noReservation = in.readInt();
        nbPlaces = in.readInt();
        dateReservation = in.readString();
        blocReservationDebut = in.readString();
        blocReservationFin = in.readString();
        nomPersonne = in.readString();
        telPersonne = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noReservation);
        dest.writeInt(nbPlaces);
        dest.writeString(dateReservation);
        dest.writeString(blocReservationDebut);
        dest.writeString(blocReservationFin);
        dest.writeString(nomPersonne);
        dest.writeString(telPersonne);
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @NonNull
    @Override
    public String toString() {
        return "Reservation{" +
                "noReservation=" + noReservation +
                ", nbPlaces=" + nbPlaces +
                ", dateReservation='" + dateReservation + '\'' +
                ", blocReservationDebut='" + blocReservationDebut + '\'' +
                ", blocReservationFin='" + blocReservationFin + '\'' +
                ", nomPersonne='" + nomPersonne + '\'' +
                ", telPersonne='" + telPersonne + '\'' +
                '}';
    }
}
