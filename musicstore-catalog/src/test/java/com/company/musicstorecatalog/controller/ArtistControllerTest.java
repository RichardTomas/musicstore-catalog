package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
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

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistRepository artistRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private String inputArtistJson;
    private String outputArtistJson;
    private Artist outputArtistModel;

    @Before
    public void setUp() throws Exception {
        Artist inputArtistModel = new Artist();
        inputArtistModel.setName("Johnny B. Good");
        inputArtistModel.setInstagram("@JohnnyBGood");
        inputArtistModel.setTwitter("@JohnnyBGood");

        inputArtistJson = mapper.writeValueAsString(inputArtistModel);

        outputArtistModel = new Artist();
        outputArtistModel.setName("Johnny B. Good");
        outputArtistModel.setInstagram("@JohnnyBGood");
        outputArtistModel.setTwitter("@JohnnyBGood");
        outputArtistModel.setArtistId(1);

        outputArtistJson = mapper.writeValueAsString(outputArtistModel);

        doReturn(outputArtistModel).when(artistRepository).save(inputArtistModel);
    }

    @Test
    public void shouldAddArtist() throws Exception {
        mockMvc.perform(post("/artist")
                        .content(inputArtistJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistJson));
    }

    @Test
    public void shouldUpdateArtist() throws Exception {
        Artist outputArtistModel2 = new Artist();
        outputArtistModel2.setName("Johnny B. Bad");
        outputArtistModel2.setInstagram("@JohnnyBBad");
        outputArtistModel2.setTwitter("@JohnnyBBad");
        outputArtistModel2.setArtistId(1);

        String outputArtistJson2 = mapper.writeValueAsString(outputArtistModel2);

        mockMvc.perform(put("/artist")
                        .content(outputArtistJson2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAlbum() throws Exception {
        mockMvc.perform(delete("/artist/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetArtistById() throws Exception {
        doReturn(Optional.of(outputArtistModel)).when(artistRepository).findById(Long.valueOf(1));
        mockMvc.perform(get("/artist/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputArtistJson));
    }

    @Test
    public void shouldGetAllArtists() throws Exception {
        String outputArtists = mapper.writeValueAsString(Arrays.asList(outputArtistModel));
        doReturn(Arrays.asList(outputArtistModel)).when(artistRepository).findAll();
        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputArtists));
    }
}