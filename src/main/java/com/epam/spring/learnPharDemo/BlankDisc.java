package com.epam.spring.learnPharDemo;

import com.epam.spring.learnPharDemo.interfaces.CompactDisc;

import java.util.List;

public class BlankDisc implements CompactDisc {
    private String title;
    private String artist;
    private List<String> tracks;
    public BlankDisc(){}
    public BlankDisc(String title, String artist){
        this.artist=artist;
        this.title=title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
    public void playTrack(int trackNumber) throws  Exception {

        if(trackNumber==4)
            throw new Exception("I don't like that music!");

        System.out.println("Playing " + title + " by " + artist+" track: "+tracks.get(trackNumber));
    }
}
