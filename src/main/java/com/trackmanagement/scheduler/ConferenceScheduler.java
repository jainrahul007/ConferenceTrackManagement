package com.trackmanagement.scheduler;

import com.trackmanagement.model.*;
import com.trackmanagement.processor.InputReader;
import com.trackmanagement.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConferenceScheduler {

    private InputReader inputReader;


    private static Logger logger = Logger.getLogger();

    private List<Talk> talks = new ArrayList<>();
    private List<Track> trackList = new ArrayList<>();

    public ConferenceScheduler()
    {
        this.inputReader= new InputReader();

    }

    private void printTracks(List<Track> tracks)
    {
        int trackCount = 1;
        for (Track track : tracks)
        {
            System.out.println("Track " + trackCount++ + ":");
            track.printSchedule();
        }
    }

    public void schedule(String fileName)
    {
        scheduleByFirstFitDecreasing(fileName);
        printTracks(trackList);
    }




    private void scheduleByFirstFitDecreasing(String fileName)
    {
        talks= inputReader.getInputListofTalk(fileName);
        talks.sort((o1, o2) -> o2.getTalkTimeinMins() - o1.getTalkTimeinMins());

        Talk longestTalk = talks.get(0);
        int longestTalkDuration = longestTalk.getTalkTimeinMins();

        Talk shortestTalk = talks.get(talks.size() - 1);
        int shortestTalkDuration = shortestTalk.getTalkTimeinMins();

        if (longestTalkDuration > Math.max(SessionConfiguration.MORNING_SESSION_DURATION, SessionConfiguration.AFTERNOON_SESSION_DURATION)
                || shortestTalkDuration < 0)
        {
            logger.error("Error: Some task are beyond the permissible range.");
            System.exit(1);
        }

        for (Talk talk : talks)
        {
            boolean isTalkOccupied = false;
            for (Track track : trackList)
            {
                if (track.allocateTalk(talk))
                {
                    isTalkOccupied = true;
                    break;
                }
            }
            if (!isTalkOccupied)
            {
                Track track = new Track();
                track.allocateTalk(talk);
                trackList.add(track);
            }
        }
        for (Track track : trackList)
        {
            track.addNetworking();
        }
    }


}