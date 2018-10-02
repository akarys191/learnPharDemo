package com.epam.spring.learnPharDemo.aspects;

import org.aspectj.lang.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class TrackCounter {
    private Map<Integer, Integer> trackCounts =
            new HashMap<Integer, Integer>();
    @Pointcut(
            "execution(** com.epam.spring.learnPharDemo.otherProjects.interfaces.CompactDisc.playTrack(int)) " +
                    "&& args(trackNumber)")
    public void trackPlayed(int trackNumber) {}
    @Before("trackPlayed(trackNumber)")
    public void countTrack(int trackNumber) {
        System.out.println(" Starting aspect!......................countTrack..............");
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount + 1);
        System.out.println(" Ending aspect!........with count "+trackCounts.size()+","+(currentCount+1)+"............................");
    }

    /*
     @Around("trackPlayed(trackNumber)")
     public void countTrack(ProceedingJoinPoint jp,int trackNumber) {
        System.out.println(" Starting aspect!......................countTrack..............");
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount + 1);
        try {
            jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(" Ending aspect!........with count "+trackCounts.size()+","+(currentCount+1)+"............................");
    }*/


    @AfterThrowing("trackPlayed(trackNumber)")
    public void finishTrack(int trackNumber) {
        System.out.println(" Starting aspect!.................finishTrack...................");
        System.out.println(" Finished track "+trackNumber);

    }
    public int getPlayCount(int trackNumber) {
        return trackCounts.containsKey(trackNumber)
                ? trackCounts.get(trackNumber) : 0;
    }
}