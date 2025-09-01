package com.school.security.controllers.api;

import com.school.security.dtos.requests.MaintenanceReqDto;
import com.school.security.dtos.responses.MaintenanceResDto;
import com.school.security.services.contracts.MaintenanceService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenances")
@CrossOrigin(origins = {"http://localhost:3000", "https://mfamaintenance.netlify.app/","http://192.168.1.133:3000/"})
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public List<MaintenanceResDto> findAll() {
        return this.maintenanceService.findAll();
    }

    @GetMapping("/{id}")
    public MaintenanceResDto findById(@PathVariable Long id) {
        return this.maintenanceService.findById(id);
    }

    @PostMapping
    public MaintenanceResDto save(@RequestBody MaintenanceReqDto toSave) {
        return this.maintenanceService.createOrUpdate(toSave);
    }

    @PutMapping("/{id}")
    public MaintenanceResDto update(
            @RequestBody MaintenanceReqDto toUpdate, @PathVariable Long id) {
        return this.maintenanceService.update(toUpdate, id);
    }

    @DeleteMapping("/{id}")
    public MaintenanceResDto delete(@PathVariable Long id) {
        return this.maintenanceService.deleteById(id);
    }
}
