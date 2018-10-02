package com.epam.spring.learnPharDemo;

import com.epam.spring.learnPharDemo.interfaces.CompactDisc;
import com.epam.spring.learnPharDemo.interfaces.MediaPlayer;

public class CDPlayer implements MediaPlayer {


    private CompactDisc cd;

    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }
    public void play() {
        System.out.println(" cd in player "+cd);
        cd.play();
    }
}