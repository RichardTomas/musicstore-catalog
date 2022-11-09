package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Label;
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
public class LabelRepositoryTest {
    @Autowired
    LabelRepository labelRepository;
    @Before
    public void setUp() throws Exception{
        labelRepository.deleteAll();
    }
    @Test
    public void shouldAddGetDeleteLabel() {
        Label label = new Label();
        label.setName("Trilogy Music");
        label.setWebsite("www.trilogymusic.com");

        label = labelRepository.save(label);

        Label label2 = labelRepository.findById(label.getLabelId()).get();

        assertEquals(label, label2);

        labelRepository.deleteById(label.getLabelId());

        Optional<Label> label3 = labelRepository.findById(label.getLabelId());

        assertEquals(false, label3.isPresent());
    }
    @Test
    public void shouldFindAllLabels() {
        Label label = new Label();
        label.setName("Trilogy Music");
        label.setWebsite("www.trilogymusic.com");

        Label label2 = new Label();
        label2.setName("2U Records");
        label2.setWebsite("www.2urecords.com");

        label = labelRepository.save(label);
        label2 = labelRepository.save(label2);
        List<Label> allLabels = new ArrayList();
        allLabels.add(label);
        allLabels.add(label2);

        List<Label> foundAllLabels = labelRepository.findAll();

        assertEquals(allLabels.size(),foundAllLabels.size());
    }
    @Test
    public void shouldUpdateLabel() {
        Label label = new Label();
        label.setName("Trilogy Music");
        label.setWebsite("www.trilogymusic.com");

        label = labelRepository.save(label);

        label.setName("2U Music");
        label.setWebsite("www.2uymusic.com");

        labelRepository.save(label);

        Optional<Label> label1 = labelRepository.findById(label.getLabelId());
        assertEquals(label1.get(), label);
    }

}