package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
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
public class ArtistRepositoryTest {
    @Autowired
    ArtistRepository artistRepository;
    @Before
    public void setUp() throws Exception {
        artistRepository.deleteAll();
    }
    @Test
    public void shouldAddGetDeleteArtist() {
        Artist artist = new Artist();
        artist.setName("New Artist");
        artist.setInstagram("@NewArtist");
        artist.setTwitter("@NewArtist");

        artist = artistRepository.save(artist);

        Artist artist2 = artistRepository.findById(artist.getArtistId()).get();

        assertEquals(artist, artist2);

        artistRepository.deleteById(artist.getArtistId());

        Optional<Artist> artist3 = artistRepository.findById(artist.getArtistId());

        assertEquals(false, artist3.isPresent());
    }
    @Test
    public void shouldFindAllArtists() {
        Artist artist = new Artist();
        artist.setName("New Artist");
        artist.setInstagram("@NewArtist");
        artist.setTwitter("@NewArtist");

        Artist artist2 = new Artist();
        artist2.setName("Another Artist");
        artist2.setInstagram("@AnotherArtist");
        artist2.setTwitter("@AnotherArtist");

        artist = artistRepository.save(artist);
        artist2 = artistRepository.save(artist2);
        List<Artist> allArtists = new ArrayList();
        allArtists.add(artist);
        allArtists.add(artist2);

        List<Artist> foundAllArtists = artistRepository.findAll();

        assertEquals(allArtists.size(),foundAllArtists.size());
    }
    @Test
    public void shouldUpdateArtist() {
        Artist artist = new Artist();
        artist.setName("New Artist");
        artist.setInstagram("@NewArtist");
        artist.setTwitter("@NewArtist");

        artist = artistRepository.save(artist);

        artist.setName("Another Artist");
        artist.setInstagram("@AnotherArtist");
        artist.setTwitter("@AnotherArtist");

        artistRepository.save(artist);

        Optional<Artist> artist1 = artistRepository.findById(artist.getArtistId());
        assertEquals(artist1.get(), artist);
    }
}