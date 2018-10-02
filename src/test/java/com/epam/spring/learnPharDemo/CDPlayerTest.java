/*
package com.epam.spring.learnPharDemo;


import com.epam.spring.learnPharDemo.aspects.TrackCounter;
import com.epam.spring.learnPharDemo.interfaces.CompactDisc;
import com.epam.spring.learnPharDemo.interfaces.MediaPlayer;
//import com.epam.spring.spittrMvc.config.WebConfig;
import com.epam.spring.spittrMvc.controllers.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes= WebConfig.class)
@ContextConfiguration(classes= CDPlayerConfig.class)
public class CDPlayerTest {

    @Autowired
    private MediaPlayer player;

    @Autowired
    private TrackCounter counter;

    @Autowired
    @Qualifier("blankDisc")
    private CompactDisc cd;

   @Test
    public void cdShouldNotBeNull() {
        System.out.println("cdShouldNotBeNull");
        assertNotNull(cd);
    }

   // @Test
    public void play() {

        System.out.println("player "+player);

        player.play();
        assertEquals(
                "Playing Sgt. Pepper's Lonely Hearts Club Band" +
                        " by The Beatles\n",
                "Playing Sgt. Pepper's Lonely Hearts Club Band" +
                        " by The Beatles\n");
    }

  // @Test
    public void testTrackCounter() {
        try {
            cd.playTrack(1);
            cd.playTrack(2);
            cd.playTrack(3);
            cd.playTrack(3);
            cd.playTrack(3);
            cd.playTrack(3);
            cd.playTrack(4);
            cd.playTrack(4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(1, counter.getPlayCount(1));
        assertEquals(1, counter.getPlayCount(2));
        assertEquals(4, counter.getPlayCount(3));
    }
}

*/
