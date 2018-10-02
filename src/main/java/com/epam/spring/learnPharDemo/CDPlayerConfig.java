package com.epam.spring.learnPharDemo;

import com.epam.spring.learnPharDemo.aspects.TrackCounter;
import com.epam.spring.learnPharDemo.interfaces.CompactDisc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class CDPlayerConfig {
    @Autowired
    Environment env;

    @Bean
    @Qualifier("blankDisc")
    public BlankDisc disc() {
        BlankDisc blankDisc = new BlankDisc(
                env.getProperty("disc.title"),
                env.getProperty("disc.artist"));

        List<String> tracks = new ArrayList<String>();
        tracks.add("Sgt. Pepper's Lonely Hearts Club Band");
        tracks.add("With a Little Help from My Friends");
        tracks.add("Lucy in the Sky with Diamonds");
        tracks.add("Getting Better");
        tracks.add("Fixing a Hole");
        blankDisc.setTracks(tracks);
        return blankDisc;
    }

    @Bean
    @Qualifier("sgtPeppers")
    public CompactDisc sgtPeppers() {
        return new SgtPeppers();
    }

    @Bean
    public CDPlayer cdPlayer(@Qualifier("blankDisc") CompactDisc compactDisc) {
        return new CDPlayer(compactDisc);
    }

    @Bean
    public TrackCounter trackCounter() {
        return new TrackCounter();
    }
}
