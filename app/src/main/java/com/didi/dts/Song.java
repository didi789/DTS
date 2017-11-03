package com.didi.dts;

/**
 * Created by Didi-PC on 01/09/2017.
 */

public class Song {

    private String strName;
    private String strAuthor;
    private String strYoutubeURL;
    private int strLocalAudio;

    public Song(String Name, String Author, String YoutubeURL, int LocalAudio) {
        this.strName = Name;
        this.strAuthor = Author;
        this.strYoutubeURL = YoutubeURL;
        this.strLocalAudio = LocalAudio;
    }

    public String getName() {
        return (this.strName);
    }

    public void setName(String strSongName) {
        this.strName = strSongName;
    }

    public String getAuthor() {
        return (this.strAuthor);
    }

    public void setAuthor(String strAuthor) {
        this.strAuthor = strAuthor;
    }

    public String getYoutubeURL() {
        return (this.strYoutubeURL);
    }

    public void setYoutubeURL(String strYoutubeURL) {
        this.strYoutubeURL = strYoutubeURL;
    }

    public int getLocalAudio() {
        return (this.strLocalAudio);
    }

    public void setLocalAudio(int strLocalAudio) {
        this.strLocalAudio = strLocalAudio;
    }
}
