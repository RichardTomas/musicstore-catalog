package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {
    @Autowired
    AlbumRepository albumRepository;
    @Before
    public void setUp() throws Exception{
        albumRepository.deleteAll();
    }
    @Test
    public void shouldAddGetDeleteAlbum() {

        Album album =  new Album();
        album.setTitle("New Album");
        album.setArtistId(1);
        album.setReleaseDate(LocalDate.of(2022, 10, 22));
        album.setLabelId(1);
        album.setListPrice(new BigDecimal("10.88"));

        album = albumRepository.save(album);

        Album album2 = albumRepository.findById(album.getAlbumId()).get();

        assertEquals(album, album2);

        albumRepository.deleteById(album.getAlbumId());

        Optional<Album> album3 = albumRepository.findById(album.getAlbumId());

        assertEquals(false, album3.isPresent());
    }
    @Test
    public void shouldFindAllAlbums() {
        Album album =  new Album();
        album.setTitle("New Album");
        album.setArtistId(1);
        album.setReleaseDate(LocalDate.of(2022, 10, 22));
        album.setLabelId(1);
        album.setListPrice(new BigDecimal("10.88"));

        Album album2 =  new Album();
        album2.setTitle("Another Album");
        album2.setArtistId(2);
        album2.setReleaseDate(LocalDate.of(2021, 8, 13));
        album2.setLabelId(2);
        album2.setListPrice(new BigDecimal("10.55"));

        album = albumRepository.save(album);
        album2 = albumRepository.save(album2);
        List<Album> allAlbums = new ArrayList();
        allAlbums.add(album);
        allAlbums.add(album2);

        List<Album> foundAllAlbums = albumRepository.findAll();

        assertEquals(allAlbums.size(),foundAllAlbums.size());
    }
    @Test
    public void shouldUpdateAlbum() {
        Album album =  new Album();
        album.setTitle("New Album");
        album.setArtistId(1);
        album.setReleaseDate(LocalDate.of(2022, 10, 22));
        album.setLabelId(1);
        album.setListPrice(new BigDecimal("10.88"));

        album = albumRepository.save(album);

        album.setTitle("Another Album");
        album.setReleaseDate(LocalDate.of(2022, 10, 23));
        album.setListPrice(new BigDecimal("10.89"));

        albumRepository.save(album);

        Optional<Album> album1 = albumRepository.findById(album.getAlbumId());
        assertEquals(album1.get(), album);
    }
}
