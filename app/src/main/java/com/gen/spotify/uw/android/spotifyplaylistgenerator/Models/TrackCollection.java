package com.gen.spotify.uw.android.spotifyplaylistgenerator.Models;
import android.os.Parcelable;
import android.os.Parcel;

public class TrackCollection implements Parcelable{
    private String href;
    private int total;

    public TrackCollection(String href, int total){
        this.href = href;
        this.total = total;
    }
    public TrackCollection(TrackCollection t){
        this.href = t.href;
        this.total = t.total;
    }
    public TrackCollection(Parcel in) {
        href = in.readString();
        total = in.readInt();
    }
    public TrackCollection(){
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(href);
        out.writeInt(total);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public String getHref() {
        return href;
    }
    public int getTotal() {
        return total;
    }
    public void setHref(String val) {
        href = val;
    }
    public void setTotal(int val){
        total = val;
    }
    public static final Parcelable.Creator<TrackCollection> CREATOR
            = new Parcelable.Creator<TrackCollection>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public TrackCollection createFromParcel(Parcel in) {
            return new TrackCollection(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public TrackCollection[] newArray(int size) {
            return new TrackCollection[size];
        }
    };
}