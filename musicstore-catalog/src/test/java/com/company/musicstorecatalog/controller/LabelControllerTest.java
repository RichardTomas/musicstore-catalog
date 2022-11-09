package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LabelRepository labelRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private String inputLabelJson;
    private String outputLabelJson;
    private Label outputLabelModel;

    @Before
    public void setUp() throws Exception {
        Label inputLabelModel = new Label();
        inputLabelModel.setName("Black Label");
        inputLabelModel.setWebsite("www.blacklabel.com");

        inputLabelJson = mapper.writeValueAsString(inputLabelModel);

        outputLabelModel = new Label();
        outputLabelModel.setName("Black Label");
        outputLabelModel.setWebsite("www.blacklabel.com");
        outputLabelModel.setLabelId(1);

        outputLabelJson = mapper.writeValueAsString(outputLabelModel);

        doReturn(outputLabelModel).when(labelRepository).save(inputLabelModel);
    }

    @Test
    public void shouldAddLabel() throws Exception {
        mockMvc.perform(post("/label")
                        .content(inputLabelJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelJson));
    }

    @Test
    public void shouldUpdateLabel() throws Exception {
        Label outputLabelModel2 = new Label();
        outputLabelModel2.setName("Blue Label");
        outputLabelModel2.setWebsite("www.bluelabel.com");
        outputLabelModel2.setLabelId(1);

        String outputLabelJson2 = mapper.writeValueAsString(outputLabelModel2);

        mockMvc.perform(put("/label")
                        .content(outputLabelJson2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabel() throws Exception {
        mockMvc.perform(delete("/label/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetLabelById() throws Exception {
        doReturn(Optional.of(outputLabelModel)).when(labelRepository).findById(Long.valueOf(1));
        mockMvc.perform(get("/label/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputLabelJson));
    }

    @Test
    public void shouldGetAllLabels() throws Exception {
        String outputLabels = mapper.writeValueAsString(Arrays.asList(outputLabelModel));
        doReturn(Arrays.asList(outputLabelModel)).when(labelRepository).findAll();
        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputLabels));
    }
}