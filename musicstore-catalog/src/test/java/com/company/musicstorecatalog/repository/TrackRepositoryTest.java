package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {
    @Autowired
    TrackRepository trackRepository;
    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
    }
    @Test
    public void shouldAddGetDeleteTrack() {
        Track track = new Track();
        track.setAlbumId(1);
        track.setTitle("Songs for Littles");
        track.setRunTime(300);

        track = trackRepository.save(track);

        Track track2 = trackRepository.findById(track.getTrackId()).get();

        assertEquals(track, track2);

        trackRepository.deleteById(track.getTrackId());

        Optional<Track> track3 = trackRepository.findById(track.getTrackId());

        assertEquals(false, track3.isPresent());
    }
    @Test
    public void shouldFindAllTracks() {
        Track track = new Track();
        track.setAlbumId(1);
        track.setTitle("Songs for Littles");
        track.setRunTime(300);

        Track track2 = new Track();
        track2.setAlbumId(1);
        track2.setTitle("Songs for Toddlers");
        track2.setRunTime(295);

        track = trackRepository.save(track);
        track2 = trackRepository.save(track2);
        List<Track> allTracks = new ArrayList();
        allTracks.add(track);
        allTracks.add(track2);

        List<Track> foundAllArtists = trackRepository.findAll();

        assertEquals(allTracks.size(),foundAllArtists.size());
    }
    @Test
    public void shouldUpdateTrack() {
        Track track = new Track();
        track.setAlbumId(1);
        track.setTitle("Songs for Littles");
        track.setRunTime(300);

        track = trackRepository.save(track);

        track.setAlbumId(2);
        track.setTitle("Songs for Babies");
        track.setRunTime(340);

        trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getTrackId());
        assertEquals(track1.get(), track);
    }
}