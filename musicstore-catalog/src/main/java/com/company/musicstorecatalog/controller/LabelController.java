package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    LabelRepository labelRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable Long id) {
        Optional<Label> returnVal = labelRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label addLabel(@RequestBody Label label) {
        return labelRepository.save(label);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody Label label) {
        labelRepository.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelRepository.deleteById(id);
    }
}
