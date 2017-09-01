package com.didi.dts;

/**
 * Created by Didi-PC on 01/09/2017.
 */

public class Song {

    private String strName;
    private String strAuthor;
    private String strYoutubeURL;
    private String strLocalAudio;

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

    public String getLocalAudio() {
        return (this.strLocalAudio);
    }

    public void setLocalAudio(String strLocalAudio) {
        this.strLocalAudio = strLocalAudio;
    }
}
