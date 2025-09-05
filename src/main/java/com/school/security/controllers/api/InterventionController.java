package com.school.security.controllers.api;

import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.dtos.responses.InterventionStatisticsResDto;
import com.school.security.services.contracts.InterventionService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interventions")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "http://192.168.1.133:3000/",
            "https://mfamaintenance.netlify.app/"
        })
public class InterventionController {

    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping
    public List<InterventionResDto> findAll() {
        return this.interventionService.findAll();
    }

    @GetMapping("/{id}")
    public InterventionResDto findById(@PathVariable Long id) {
        return this.interventionService.findById(id);
    }

    @PostMapping
    public InterventionResDto save(@RequestBody InterventionReqDto toSave) {
        return this.interventionService.createOrUpdate(toSave);
    }

    @PutMapping("/{id}")
    public InterventionResDto update(
            @RequestBody InterventionReqDto toUpdate, @PathVariable Long id) {
        return this.interventionService.update(toUpdate, id);
    }

    @DeleteMapping("/{id}")
    public InterventionResDto delete(@PathVariable Long id) {
        return this.interventionService.deleteById(id);
    }

    @GetMapping("/invitation")
    public Long getInterventionCount(@RequestParam Long id) {
        return this.interventionService.getCountIntervention(id);
    }

    @GetMapping("/statistics")
    public InterventionStatisticsResDto getInterventionStatistics() {
        return this.interventionService.getInterventionStatistics();
    }

    @GetMapping("/variation-by-years")
    public Map<String, Object> getVariation(@RequestParam int year) {
        return this.interventionService.getVariationParAnnee(year);
    }

    @GetMapping("/years-possibles")
    public List<Integer> getYears() {
        return this.interventionService.findAllYearsWithRequest();
    }
}
