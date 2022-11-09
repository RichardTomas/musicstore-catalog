package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrackRepository trackRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private String inputTrackJson;

    private String outputTrackJson;

    private Track outputTrackModel;

    @Before
    public void setUp() throws Exception {
        Track inputTrackModel = new Track();
        inputTrackModel.setAlbumId(5);
        inputTrackModel.setTitle("Castaway");
        inputTrackModel.setRunTime(300);

        inputTrackJson = mapper.writeValueAsString(inputTrackModel);

        outputTrackModel = new Track();
        outputTrackModel.setAlbumId(5);
        outputTrackModel.setTitle("Castaway");
        outputTrackModel.setRunTime(300);
        outputTrackModel.setTrackId(1);

        outputTrackJson = mapper.writeValueAsString(outputTrackModel);

        doReturn(outputTrackModel).when(trackRepository).save(inputTrackModel);
    }

    @Test
    public void shouldAddTrack() throws Exception {
        mockMvc.perform(post("/track")
                        .content(inputTrackJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackJson));
    }

    @Test
    public void shouldUpdateTrack() throws Exception {
        Track outputTrackModel2 = new Track();
        outputTrackModel2.setAlbumId(5);
        outputTrackModel2.setTitle("Aurora Sunrise");
        outputTrackModel2.setRunTime(310);
        outputTrackModel2.setTrackId(1);

        String outputTrackJson2 = mapper.writeValueAsString(outputTrackModel2);

        mockMvc.perform(put("/track")
                        .content(outputTrackJson2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        mockMvc.perform(delete("/track/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetTrackById() throws Exception {
        doReturn(Optional.of(outputTrackModel)).when(trackRepository).findById(Long.valueOf(1));
        mockMvc.perform(get("/track/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTrackJson));
    }

    @Test
    public void shouldGetAllAlbums() throws Exception {
        String outputTracks = mapper.writeValueAsString(Arrays.asList(outputTrackModel));
        doReturn(Arrays.asList(outputTrackModel)).when(trackRepository).findAll();
        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTracks));
    }
}