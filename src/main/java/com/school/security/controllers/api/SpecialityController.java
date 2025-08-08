package com.school.security.controllers.api;

import com.school.security.dtos.requests.SpecialityReqDto;
import com.school.security.dtos.responses.SpecialityResDto;
import com.school.security.services.contracts.SpecialityService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/speciality")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.133:3000/"})
public class SpecialityController {
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public List<SpecialityResDto> findAllSpeciality() {
        return this.specialityService.findAll();
    }

    @GetMapping("{id}")
    public SpecialityResDto findById(@PathVariable Long id) {
        return this.specialityService.findById(id);
    }

    @PutMapping
    public SpecialityResDto saveOrUpdate(@RequestBody SpecialityReqDto toSave) {
        return this.specialityService.createOrUpdate(toSave);
    }

    @DeleteMapping("{id}")
    public SpecialityResDto deleteById(@PathVariable Long id) {
        return this.specialityService.deleteById(id);
    }

    @PutMapping("{id}")
    public SpecialityResDto update(@RequestBody SpecialityReqDto toSave, @PathVariable Long id) {
        return this.specialityService.save(toSave, id);
    }
}
