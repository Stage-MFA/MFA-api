package com.school.security.controllers.api;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;
import com.school.security.services.contracts.RequestInterventionService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.133:3000/"})
public class InterventionRequestController {

    private final RequestInterventionService requestInterventionService;

    public InterventionRequestController(RequestInterventionService requestInterventionService) {
        this.requestInterventionService = requestInterventionService;
    }

    @GetMapping
    public List<RequestInterventionResDto> findAll() {
        return this.requestInterventionService.findAll();
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
}
