package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlbumRepository albumRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private String inputAlbumJson;

    private String outputAlbumJson;

    private Album outputAlbumModel;

    @Before
    public void setUp() throws Exception {
        Album inputAlbumModel = new Album();
        inputAlbumModel.setTitle("New Album");
        inputAlbumModel.setArtistId(2);
        inputAlbumModel.setReleaseDate(LocalDate.of(2022, 1, 7));
        inputAlbumModel.setLabelId(2);
        inputAlbumModel.setListPrice(new BigDecimal("9.75"));

        inputAlbumJson = mapper.writeValueAsString(inputAlbumModel);

        outputAlbumModel = new Album();
        outputAlbumModel.setTitle("New Album");
        outputAlbumModel.setArtistId(2);
        outputAlbumModel.setReleaseDate(LocalDate.of(2022, 1, 7));
        outputAlbumModel.setLabelId(2);
        outputAlbumModel.setListPrice(new BigDecimal("9.75"));
        outputAlbumModel.setAlbumId(1);

        outputAlbumJson = mapper.writeValueAsString(outputAlbumModel);

        doReturn(outputAlbumModel).when(albumRepository).save(inputAlbumModel);
    }

    @Test
    public void shouldAddAlbum() throws Exception {
        mockMvc.perform(post("/album")
                        .content(inputAlbumJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumJson));
    }

    @Test
    public void shouldUpdateAlbum() throws Exception {
        Album outputAlbumModel2 = new Album();
        outputAlbumModel2.setTitle("Another Album");
        outputAlbumModel2.setArtistId(3);
        outputAlbumModel2.setReleaseDate(LocalDate.of(2022, 9, 14));
        outputAlbumModel2.setLabelId(5);
        outputAlbumModel2.setListPrice(new BigDecimal("8.50"));
        outputAlbumModel2.setAlbumId(1);

        String outputAlbumJson2 = mapper.writeValueAsString(outputAlbumModel2);

        mockMvc.perform(put("/album")
                        .content(outputAlbumJson2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAlbum() throws Exception {
        mockMvc.perform(delete("/album/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAlbumById() throws Exception {
        doReturn(Optional.of(outputAlbumModel)).when(albumRepository).findById(Long.valueOf(1));
        mockMvc.perform(get("/album/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputAlbumJson));
    }

    @Test
    public void shouldGetAllAlbums() throws Exception {
        String outputAlbums = mapper.writeValueAsString(Arrays.asList(outputAlbumModel));
        doReturn(Arrays.asList(outputAlbumModel)).when(albumRepository).findAll();
        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputAlbums));
    }
}