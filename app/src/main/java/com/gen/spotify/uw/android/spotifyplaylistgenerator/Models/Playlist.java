package com.gen.spotify.uw.android.spotifyplaylistgenerator.Models;
import android.os.Parcelable;
import android.os.Parcel;

public class Playlist implements Parcelable{
    private String id;
    private String name;
    private Owner owner;
    private boolean isCollaborative;
    private String href;
    private String uri;
    private String type;
    private boolean isPublic;
    private String snapshotId;
    private TrackCollection tracks;

    public Playlist(String id, String name, Owner owner, boolean isCollaborative,
                    String href, String uri, String type, boolean isPublic, String snapshotId,
                    TrackCollection tracks){
        this.id = id;
        this.name = name;
        this.owner = new Owner(owner);
        this.isCollaborative = isCollaborative;
        this.href = href;
        this.uri = uri;
        this.type = type;
        this.isPublic = isPublic;
        this.snapshotId = snapshotId;
        this.tracks = new TrackCollection(tracks);
    }

    public Playlist(Playlist p){
        this.id = p.id;
        this.name = p.name;
        this.owner = new Owner(p.owner);
        this.isCollaborative = p.isCollaborative;
        this.href = p.href;
        this.uri = p.uri;
        this.type = p.type;
        this.isPublic = p.isPublic;
        this.snapshotId = p.snapshotId;
        this.tracks = new TrackCollection(p.tracks);
    }

    public Playlist(){}

    public Playlist(Parcel in){
        id = in.readString();
        name = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
        isCollaborative = in.readByte() != 0;
        href = in.readString();
        uri = in.readString();
        type = in.readString();
        isPublic = in.readByte() != 0;
        snapshotId = in.readString();
        tracks = in.readParcelable(TrackCollection.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeParcelable(owner, flags);
        out.writeByte((byte) (isCollaborative ? 1 : 0));
        out.writeString(href);
        out.writeString(uri);
        out.writeString(type);
        out.writeByte((byte) (isPublic ? 1 : 0));
        out.writeString(snapshotId);
        out.writeParcelable(tracks, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getID(){
        return id;
    }

    public String getPlaylistName(){
        return name;
    }

    public Owner getOwner(){
        return owner;
    }

    public boolean getIsCollaborative(){
        return isCollaborative;
    }

    public String getHref(){
        return href;
    }

    public String getUri(){
        return uri;
    }

    public String getSpotifyType(){
        return type;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public TrackCollection getTracks(){
        return tracks;
    }

    public void setID(String val){
        id = val;
    }

    public void setPlaylistName(String val){
        name = val;
    }

    public void setOwner(Owner val){
        owner = new Owner(val);
    }

    public void setCollaborative(boolean val){
        isCollaborative = val;
    }

    public void setHref(String val){
        href = val;
    }

    public void setUri(String val){
        uri = val;
    }

    public void setSpotifyType(String val){
        type = val;
    }

    public void setIsPublic(boolean val){
        isPublic = val;
    }

    public void setSnapshotId(String val){
        snapshotId = val;
    }

    public void setTracks(TrackCollection val){
        tracks = new TrackCollection(val);
    }

    public static final Parcelable.Creator<Playlist> CREATOR
            = new Parcelable.Creator<Playlist>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };
}