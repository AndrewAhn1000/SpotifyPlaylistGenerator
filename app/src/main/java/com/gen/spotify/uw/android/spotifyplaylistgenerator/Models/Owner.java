package com.gen.spotify.uw.android.spotifyplaylistgenerator.Models;
import android.os.Parcelable;
import android.os.Parcel;

public class Owner implements Parcelable{
    private String display_name;
    private String href;
    private String id;
    private String type;
    private String uri;

    public Owner(String display_name, String href, String id, String type, String uri){
        this.display_name = display_name;
        this.href=href;
        this.id=id;
        this.type=type;
        this.uri=uri;
    }
    public Owner(Owner o){
        this.display_name = o.display_name;
        this.href = o.href;
        this.id = o.id;
        this.type = o.type;
        this.uri = o.uri;
    }
    public Owner(Parcel in){
        display_name = in.readString();
        href = in.readString();
        id = in.readString();
        type = in.readString();
        uri = in.readString();
    }
    public Owner(){}
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(display_name);
        out.writeString(href);
        out.writeString(id);
        out.writeString(type);
        out.writeString(uri);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public String getDisplayName(){
        return display_name;
    }
    public String getHref(){
        return href;
    }
    public String getOwnerId(){
        return id;
    }
    public String getOwnerType(){
        return type;
    }
    public String getUri(){
        return uri;
    }
    public void setDisplayName(String val){
        display_name = val;
    }
    public void setHref(String val){
        if(val != null) {
            href = val;
        }
    }
    public void setOwnerId(String val){
        id = val;
    }
    public void setOwnerType(String val){
        type = val;
    }
    public void setUri(String val){
        uri = val;
    }
    public static final Parcelable.Creator<Owner> CREATOR
            = new Parcelable.Creator<Owner>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
