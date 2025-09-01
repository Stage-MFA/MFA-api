package com.school.security.controllers.api;

import com.school.security.dtos.requests.MaterialReqDto;
import com.school.security.entities.Material;
import com.school.security.services.contracts.MaterialService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/material")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "https://mfamaintenance.netlify.app/",
            "http://192.168.1.133:3000/"
        })
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<Material> getAllMaterial() {
        return this.materialService.findAll();
    }

    @PutMapping
    public Material register(@RequestBody MaterialReqDto data) {
        return this.materialService.createOrUpdate(data);
    }

    @DeleteMapping("/{id}")
    public Material delete(@PathVariable Long id) {
        return this.materialService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Material findById(@PathVariable Long id) {
        return this.materialService.findById(id);
    }
}
