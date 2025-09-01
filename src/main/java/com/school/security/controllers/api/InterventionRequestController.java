package com.school.security.controllers.api;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;
import com.school.security.dtos.responses.RequestStatisticsResDto;
import com.school.security.services.contracts.RequestInterventionService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "https://mfamaintenance.netlify.app/",
            "http://192.168.1.133:3000/"
        })
public class InterventionRequestController {

    private final RequestInterventionService requestInterventionService;

    public InterventionRequestController(RequestInterventionService requestInterventionService) {
        this.requestInterventionService = requestInterventionService;
    }

    @GetMapping
    public List<RequestInterventionResDto> findAll() {
        return this.requestInterventionService.findAll();
    }

    @GetMapping("/users/{id}")
    public List<RequestInterventionResDto> findAllByUserId(@PathVariable Long id) {
        return this.requestInterventionService.listRequestInterventionByUserId(id);
    }

    @GetMapping("/{id}")
    public RequestInterventionResDto findById(@PathVariable Long id) {
        return this.requestInterventionService.findById(id);
    }

    @PostMapping
    public RequestInterventionResDto save(@RequestBody RequestInterventionReqDto toSave) {
        return this.requestInterventionService.createOrUpdate(toSave);
    }

    @PutMapping("/{id}")
    public RequestInterventionResDto update(
            @RequestBody RequestInterventionReqDto toUpdate, @PathVariable Long id) {
        return this.requestInterventionService.update(toUpdate, id);
    }

    @DeleteMapping("/{id}")
    public RequestInterventionResDto delete(@PathVariable Long id) {
        return this.requestInterventionService.deleteById(id);
    }

    @GetMapping("/invitation")
    public Long getRequestCount() {
        return this.requestInterventionService.getCountRequest();
    }

    @GetMapping("/variation-by-years")
    public Map<String, Object> getVariation(@RequestParam int year) {
        return this.requestInterventionService.getVariationParAnnee(year);
    }

    @GetMapping("/years-possibles")
    public List<Integer> getYears() {
        return this.requestInterventionService.findAllYearsWithRequest();
    }

    @GetMapping("/statistics")
    public RequestStatisticsResDto getStatisticIntervention() {
        return this.requestInterventionService.getStatisticIntervention();
    }
}
