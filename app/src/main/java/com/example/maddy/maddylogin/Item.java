package com.example.maddy.maddylogin;

<<<<<<< HEAD
import android.os.Parcel;
import android.os.Parcelable;

=======
>>>>>>> origin/master
/**
 * Created by andrey on 6/21/17.
 */

<<<<<<< HEAD
public class Item implements Parcelable {
=======
public class Item {
>>>>>>> origin/master
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
<<<<<<< HEAD

    @Override
    public int describeContents() {
        return 0;
    }

    private Item(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
=======
>>>>>>> origin/master
}
