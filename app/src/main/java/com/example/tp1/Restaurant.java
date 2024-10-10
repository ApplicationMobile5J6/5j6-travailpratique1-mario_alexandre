package com.example.tp1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restaurant implements Parcelable {

    int noRestaurant, nbPlacesMax, nbPlacesRestantes;
    String nomRestaurant;

    public Restaurant(int noRestaurant, String nomRestaurant, int nbPlacesMax, int nbPlacesRestantes) {
        this.noRestaurant = noRestaurant;
        this.nomRestaurant = nomRestaurant;
        this.nbPlacesMax = nbPlacesMax;
        this.nbPlacesRestantes = nbPlacesRestantes;
    }
    protected Restaurant(Parcel in) {
        noRestaurant = in.readInt();
        nbPlacesMax = in.readInt();
        nbPlacesRestantes = in.readInt();
        nomRestaurant = in.readString();
    }


    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(noRestaurant);
        parcel.writeInt(nbPlacesMax);
        parcel.writeInt(nbPlacesRestantes);
        parcel.writeString(nomRestaurant);
    }
}
