import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class restaurant implements Parcelable {

    int noRestaurant, nbPlacesMax, nbPlacesRestantes;
    String nomRestaurant;

    protected restaurant(Parcel in) {
        noRestaurant = in.readInt();
        nbPlacesMax = in.readInt();
        nbPlacesRestantes = in.readInt();
        nomRestaurant = in.readString();
    }

    public static final Creator<restaurant> CREATOR = new Creator<restaurant>() {
        @Override
        public restaurant createFromParcel(Parcel in) {
            return new restaurant(in);
        }

        @Override
        public restaurant[] newArray(int size) {
            return new restaurant[size];
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
